package com.prayer.builder.impl.util;

import static com.prayer.util.debug.Log.debug;
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
import com.prayer.util.io.PropertyKit;
import com.prayer.util.string.StringKit;

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
    private static final ConcurrentMap<String, String> DB_TYPES = new ConcurrentHashMap<>();
    /** 数据库名称获取 **/
    private static final PropertyKit LOADER = new PropertyKit(Resources.DB_CFG_FILE);
    /** 数据库元数据访问 **/
    private static final MetadataDao dao = singleton(MetadataDaoImpl.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    /** 初始化数据类型映射表，直接根据Database填充 **/
    static {
        final String file = Resources.DB_TYPES_FILE + dao.getFile() + Symbol.DOT + Constants.EXTENSION.PROP;
        debug(LOGGER, "[D] Type Mapping file = " + file);
        final PropertyKit loader = new PropertyKit(file);
        final Properties prop = loader.getProp();
        for (final Object key : prop.keySet()) {
            if (null != key && StringKit.isNonNil(key.toString())) {
                final String keyStr = key.toString();
                final String[] keys = keyStr.split("\\.");
                if (Constants.TWO == keys.length && StringUtil.equals(keys[0], Resources.DB_CATEGORY)
                        && StringKit.isNonNil(keys[1])) {
                    DB_TYPES.put(keys[1], prop.getProperty(keyStr));
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
        return DB_TYPES.get(key);
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
