package com.prayer.business.impl.ordered;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;
import com.prayer.facade.schema.verifier.Attributes;
import com.prayer.facade.util.graphic.GraphicData;
import com.prayer.util.digraph.Edges;
import com.prayer.util.digraph.GraphicModel;
import com.prayer.util.digraph.VertexNode;
import com.prayer.util.io.IOKit;
import com.prayer.util.string.StringKit;

import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

/**
 * 
 * @author Lang
 *
 */

public class OrderedGraphicer {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderedGraphicer.class);
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================

    public Map<Integer, String> build(@NotNull @NotEmpty @NotBlank final String folder) {
        /** 1.数据Key **/
        final String[] data = this.buildNodes(folder);
        /** 2.设置FromTo，构建图 **/
        final Edges fromTo = this.buildMapping(folder);
        System.out.println(fromTo);
        final GraphicModel graphic = this.buildGraphic(data, fromTo);
        /** 3.设置ToFrom，构建逆图 **/
        final GraphicModel rtGraphic = this.buildGraphic(data, fromTo.revert());
        System.out.println(graphic);
        System.out.println(rtGraphic);
        return null;
    }
    // ~ Private Methods =====================================

    private GraphicModel buildGraphic(final String[] data, Edges fromTo) {
        final int length = data.length;
        final VertexNode[] nodes = new VertexNode[length];
        for (int idx = 0; idx < length; idx++) {
            final String key = data[idx];
            final GraphicData dataItem = new OrderedData(key);
            final VertexNode node = new VertexNode(dataItem);
            nodes[idx] = node;
        }
        return new GraphicModel(nodes, fromTo);
    }

    /** 构建图中搜需要的data **/
    private String[] buildNodes(final String folder) {
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
                            /** Skip **/
                            mapping.addEdge(key, ref);
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
                key = data.getJsonObject(Attributes.R_META).getString(Attributes.M_TABLE);
            }
        } catch (DecodeException ex) {
            key = null;
        }
        return key;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
