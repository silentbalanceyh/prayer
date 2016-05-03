package com.prayer.business.digraph;

import static com.prayer.util.debug.Log.info;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.constant.Constants;
import com.prayer.facade.schema.verifier.Attributes;
import com.prayer.facade.util.digraph.NodeData;
import com.prayer.util.digraph.Edges;
import com.prayer.util.digraph.Graphic;
import com.prayer.util.digraph.Node;
import com.prayer.util.io.IOKit;
import com.prayer.util.string.StringKit;

import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
final class SchemaGraphicer {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(SchemaGraphicer.class);
    // ~ Instance Fields =====================================
    /** 从Key到Data的映射 **/
    private transient ConcurrentMap<String, String> dataMap = new ConcurrentHashMap<>();

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 
     * @param folder
     * @return
     */
    public Graphic build(@NotNull @NotEmpty @NotBlank final String folder) {
        /** 1.数据Key **/
        final String[] data = this.buildNodes(folder);
        /** 2.设置FromTo，构建图 **/
        final Edges fromTo = this.buildMapping(folder);
        final Graphic graphic = this.buildGraphic(data, fromTo);
        /** 3.根据Json数据构造Graphic **/
        info(LOGGER,"( SchemaGraphicer ) Graphic -> \n" + graphic.toString());
        return graphic;
    }
    // ~ Private Methods =====================================

    private Graphic buildGraphic(final String[] data, Edges fromTo) {
        final int length = data.length;
        final Node[] nodes = new Node[length];
        for (int idx = 0; idx < length; idx++) {
            final String key = data[idx];
            final NodeData dataItem = new OrderedData(key);
            dataItem.setData(this.dataMap.get(key));
            final Node node = new Node(dataItem);
            nodes[idx] = node;
        }
        return new Graphic(nodes, fromTo);
    }

    /** 构建图中搜需要的data **/
    private String[] buildNodes(final String folder) {
        this.dataMap.clear();
        final List<String> files = IOKit.listFiles(folder);
        final List<String> data = new ArrayList<>();
        for (final String file : files) {
            final String dataFile = folder + '/' + file;
            final String key = this.extractKey(dataFile);
            if (StringKit.isNonNil(key)) {
                data.add(this.extractKey(dataFile));
            }
        }
        return data.toArray(Constants.T_STR_ARR);
    }

    private Edges buildMapping(final String folder) {
        final Edges fromToMap = new Edges();
        final List<String> files = IOKit.listFiles(folder);
        for (final String file : files) {
            final String dataFile = folder + '/' + file;
            fromToMap.addEdge(this.extractMapping(dataFile));
        }
        return fromToMap;
    }

    private Edges extractMapping(final String file) {
        final Edges mapping = new Edges();
        try {
            final JsonObject data = new JsonObject(IOKit.getContent(file));
            /** 抽取Key **/
            if (data.containsKey(Attributes.R_META)) {
                final String key = data.getJsonObject(Attributes.R_META).getString(Attributes.M_TABLE);
                if (null != key) {
                    /** 读取Field列表 **/
                    final JsonArray fields = data.getJsonArray(Attributes.R_FIELDS);
                    final int size = fields.size();
                    for (int idx = 0; idx < size; idx++) {
                        final JsonObject item = fields.getJsonObject(idx);
                        /** 读取当前Table引用 **/
                        final String ref = item.getString(Attributes.F_REF_TABLE);
                        if (null != item && StringKit.isNonNil(ref)) {
                            /**
                             * 这个操作很重要，必须跳过自身引用的情况，如果不去掉自身引用，会导致逆向图中会出现搜索树，
                             * 参考下边例子
                             **/
                            if (!key.equals(ref)) {
                                mapping.addEdge(key, ref);
                            }
                        }
                    }
                }
            }
        } catch (DecodeException ex) {
            mapping.clear();
        }
        return mapping;
    }

    private String extractKey(final String file) {
        String key = null;
        try {
            final JsonObject data = new JsonObject(IOKit.getContent(file));
            if (data.containsKey(Attributes.R_META)) {
                final JsonObject meta = data.getJsonObject(Attributes.R_META);
                key = meta.getString(Attributes.M_TABLE);
                // 填充Map
                if (null != key) {
                    this.dataMap.put(key, file);
                }
            }
        } catch (DecodeException ex) {
            key = null;
        }
        return key;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
