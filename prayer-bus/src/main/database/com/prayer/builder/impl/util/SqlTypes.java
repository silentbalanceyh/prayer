package com.prayer.builder.impl.util;

import static com.prayer.util.debug.Log.jvmError;
import static com.prayer.util.reflection.Instance.singleton;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;
import com.prayer.constant.Resources;
import com.prayer.constant.Symbol;
import com.prayer.dao.impl.std.database.MetadataDaoImpl;
import com.prayer.facade.dao.schema.MetadataDao;
import com.prayer.util.io.IOKit;
import com.prayer.util.io.PropertyKit;
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
    /** 数据库名称获取 **/
    private static final PropertyKit LOADER = new PropertyKit(Resources.DB_CFG_FILE);
    /** 数据库元数据访问 **/
    private static final MetadataDao dao = singleton(MetadataDaoImpl.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    /** 初始化数据类型映射表，直接根据Database填充 **/
    static {
        /** 1.可支持类型集合 **/
        final String typeFile = Resources.DB_TYPES_FILE + dao.getFile() + "/types" + Symbol.DOT
                + Constants.EXTENSION.JSON;
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
        final String sptFile = Resources.DB_TYPES_FILE + dao.getFile() + "/mapping" + Symbol.DOT
                + Constants.EXTENSION.JSON;
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
        final String mappingFile = Resources.DB_TYPES_FILE + dao.getFile() + "/mapping" + Symbol.DOT
                + Constants.EXTENSION.PROP;
        DB_MAPPING.clear();
        final PropertyKit loader = new PropertyKit(mappingFile);
        final Properties prop = loader.getProp();
        for (final Object key : prop.keySet()) {
            if (null != key && StringKit.isNonNil(key.toString())) {
                final String keyStr = key.toString();
                final String[] keys = keyStr.split("\\.");
                if (Constants.TWO == keys.length && StringUtil.equals(keys[0], Resources.DB_CATEGORY)
                        && StringKit.isNonNil(keys[1])) {
                    DB_MAPPING.put(keys[1], prop.getProperty(keyStr));
                }
            }
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
     * 数据库名称
     * 
     * @return
     */
    public static String database() {
        return LOADER.getString(Resources.DB_CATEGORY + ".jdbc.database.name");
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
