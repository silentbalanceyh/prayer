package com.prayer.database.util.sql;

import static com.prayer.util.debug.Log.jvmError;
import static com.prayer.util.reflection.Instance.singleton;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.dao.data.DatabaseDalor;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.constant.Symbol;
import com.prayer.facade.database.dao.DatabaseDao;
import com.prayer.facade.resource.Inceptor;
import com.prayer.facade.resource.Point;
import com.prayer.resource.InceptBus;
import com.prayer.resource.Resources;
import com.prayer.util.io.IOKit;
import com.prayer.util.resource.DatumLoader;
import com.prayer.util.string.StringKit;

import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jodd.util.StringUtil;
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
public final class SqlTypes {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(SqlTypes.class);
    /** 数据库类型映射 **/
    private static final ConcurrentMap<String, String> DB_MAPPING = new ConcurrentHashMap<>();
    /** 数据库类型验证 **/
    private static final JsonArray DB_TYPE_SET = new JsonArray();
    /** 数据库可支持的映射 **/
    private static final ConcurrentMap<String, JsonArray> DB_SUPPORT_TYPES = new ConcurrentHashMap<>();
    /** 数据库修改类型的向量表 **/
    private static final ConcurrentMap<String, JsonArray> DB_REVERT_VECTORS = new ConcurrentHashMap<>();
    /** 数据库名称获取 **/
    private static final Inceptor LOADER = Resources.JDBC;
    /** 数据库元数据访问 **/
    private static final DatabaseDao dao = singleton(DatabaseDalor.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    /** 初始化数据类型映射表，直接根据Database填充 **/
    static {
        /** 1.可支持类型集合 **/
        final String mappingFolder = InceptBus.build(Point.Database.class).getString(Point.Database.Jdbc.MAPPING);
        final String typeFile = mappingFolder + dao.getFile() + "/types" + Symbol.DOT + Constants.EXTENSION.JSON;
        try {
            DB_TYPE_SET.clear();
            final String content = IOKit.getContent(typeFile);
            final JsonArray typeArray = new JsonArray(content);
            for (final Object item : typeArray) {
                if (null != item && String.class == item.getClass()) {
                    DB_TYPE_SET.add(item.toString());
                }
            }
        } catch (DecodeException ex) {
            jvmError(LOGGER, ex);
        }
        /** 2.映射表的信息 **/
        final String sptFile = mappingFolder + dao.getFile() + "/mapping" + Symbol.DOT + Constants.EXTENSION.JSON;
        try {
            DB_SUPPORT_TYPES.clear();
            final String content = IOKit.getContent(sptFile);
            final JsonObject sptTypes = new JsonObject(content);
            for (final String field : sptTypes.fieldNames()) {
                if (null != field) {
                    final JsonArray values = sptTypes.getJsonArray(field);
                    DB_SUPPORT_TYPES.put(field, values);
                }
            }
        } catch (DecodeException ex) {
            jvmError(LOGGER, ex);
        }

        /** 3.系统使用的映射表 **/
        final String mappingFile = mappingFolder + dao.getFile() + "/mapping" + Symbol.DOT + Constants.EXTENSION.PROP;
        DB_MAPPING.clear();
        final Properties prop = DatumLoader.getLoader(mappingFile);
        for (final Object key : prop.keySet()) {
            final String keyStr = key.toString();
            final String[] keys = keyStr.split("\\.");
            if (Constants.TWO == keys.length && StringUtil.equals(keys[0], Resources.Data.CATEGORY)
                    && StringKit.isNonNil(keys[1])) {
                DB_MAPPING.put(keys[1], prop.getProperty(keyStr));
            }
        }
        /** 4.系统使用的类型转换表 **/
        final String rvectorFile = mappingFolder + dao.getFile() + "/vectors" + Symbol.DOT + Constants.EXTENSION.JSON;
        try {
            DB_REVERT_VECTORS.clear();
            final String content = IOKit.getContent(rvectorFile);
            final JsonObject vtTypes = new JsonObject(content);
            for (final String field : vtTypes.fieldNames()) {
                if (null != field) {
                    final JsonArray values = vtTypes.getJsonArray(field);
                    DB_REVERT_VECTORS.put(field, values);
                }
            }
        } catch (DecodeException ex) {
            jvmError(LOGGER, ex);
        }
    }

    // ~ Static Methods ======================================
    /**
     * 从系统中读取类型信息
     * 
     * @param key
     * @return
     */
    public static String get(@NotNull @NotEmpty @NotBlank final Object key) {
        return DB_MAPPING.get(key);
    }

    /**
     * 读取所有数据库类型集
     * 
     * @return
     */
    public static JsonArray types() {
        return DB_TYPE_SET;
    }

    /**
     * 
     * @param key
     * @return
     */
    public static JsonArray supports(final String key) {
        return DB_SUPPORT_TYPES.get(key);
    }

    /**
     * 获取目标类型，得到可以转换成target的所有类型集合
     * 
     * @param target
     * @return
     */
    public static JsonArray rvectors(final String target) {
        return DB_REVERT_VECTORS.get(target);
    }

    /**
     * 数据库名称
     * 
     * @return
     */
    public static String database() {
        return LOADER.getString(Resources.Data.CATEGORY + ".jdbc.database.name");
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private SqlTypes() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
