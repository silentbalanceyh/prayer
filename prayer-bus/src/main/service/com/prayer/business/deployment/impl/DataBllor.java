package com.prayer.business.deployment.impl;

import static com.prayer.util.Converter.fromStr;
import static com.prayer.util.debug.Log.info;
import static com.prayer.util.debug.Log.jvmError;
import static com.prayer.util.reflection.Instance.singleton;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.business.digraph.OrderedBuilder;
import com.prayer.constant.SystemEnum.Category;
import com.prayer.dao.schema.SchemaDalor;
import com.prayer.facade.business.instantor.deployment.DataInstantor;
import com.prayer.facade.database.dao.schema.SchemaDao;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.util.io.IOKit;

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
public class DataBllor implements DataInstantor {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(DataBllor.class);
    /** **/
    private static final String DATA_PROC = "( {0} ) Data Loading from data file = {1} with identifier = {2}, type = {3}.";
    /** **/
    private static final String DATA_PURGE = "( {0} ) Data purging for identifier = {1}.";
    // ~ Instance Fields =====================================
    /** 底层调用 **/
    @NotNull
    private transient final SchemaDao schemaDao;
    /** **/
    @NotNull
    private transient final OrderedBuilder builder;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public DataBllor() {
        /** 实例化当前的Dao **/
        this.schemaDao = singleton(SchemaDalor.class);
        this.builder = singleton(OrderedBuilder.class);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public boolean loading(@NotNull @NotBlank @NotEmpty final String folder) throws AbstractException {
        /** 1.枚举所有Folders，构造Order **/
        final List<String> entities = IOKit.listDirectories(folder);
        final List<String> orders = this.buildLoadOrder();
        /** 2.枚举所有的文件夹 **/
        for (final String identifier : orders) {
            if (entities.contains(identifier)) {
                // TODO: 文件名本身就是identifier（约定）
                final String dataFolder = folder + "/" + identifier;
                /** 3.枚举数据目录下所有数据文件 **/
                final List<String> files = IOKit.listFiles(dataFolder);
                for (final String file : files) {
                    final String finalFile = dataFolder + "/" + file;
                    /** 4.构造JsonArray **/
                    final JsonObject dataObj = this.buildRaw(finalFile);
                    info(LOGGER, MessageFormat.format(DATA_PROC, getClass().getSimpleName(), finalFile, identifier,
                            dataObj.getString("type")));
                    /** 5.执行数据导入 **/
                    this.processData(identifier, dataObj);
                }
            }
        }
        return true;
    }

    /** **/
    @Override
    public boolean purge() throws AbstractException {
        final List<String> orders = this.buildPurgeOrder();
        for (final String identifier : orders) {
            info(LOGGER, MessageFormat.format(DATA_PURGE, getClass().getSimpleName(), identifier));
            DataExecutors.purgeData(identifier);
        }
        return true;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private void processData(final String identifier, final JsonObject dataObj) throws AbstractException {
        try {
            final Category category = fromStr(Category.class, dataObj.getString("type"));
            if (null != category) {
                final JsonArray array = dataObj.getJsonArray("data");
                if (null != array && !array.isEmpty()) {
                    if (Category.ENTITY == category) {
                        DataExecutors.processEntity(identifier, array);
                    } else {
                        DataExecutors.processRelation(identifier, array);
                    }
                }
            }
        } catch (ClassCastException ex) {
            jvmError(LOGGER, ex);
        }
    }

    /** 构造导入顺序 **/
    private List<String> buildPurgeOrder() throws AbstractException {
        final ConcurrentMap<String, String> map = this.schemaDao.get();
        final ConcurrentMap<Integer, String> orderMap = this.builder.buildPurgeOrder(map.keySet());
        final List<String> identifiers = new ArrayList<>();
        for (final Integer order : orderMap.keySet()) {
            identifiers.add(map.get(orderMap.get(order)));
        }
        /** 将不存在的identifiers移除 **/
        return identifiers;
    }

    /** 构造导入顺序 **/
    private List<String> buildLoadOrder() throws AbstractException {
        final List<String> identifiers = this.buildPurgeOrder();
        Collections.reverse(identifiers);
        return identifiers;
    }

    private JsonObject buildRaw(final String file) {
        JsonObject data = new JsonObject();
        try {
            data = new JsonObject(IOKit.getContent(file));
        } catch (DecodeException ex) {
            jvmError(LOGGER, ex);
            ex.printStackTrace();
        }
        return data;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
