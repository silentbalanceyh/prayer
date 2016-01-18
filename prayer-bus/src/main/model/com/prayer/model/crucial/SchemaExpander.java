package com.prayer.model.crucial;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.SystemEnum.KeyCategory;
import com.prayer.model.meta.database.PEField;
import com.prayer.model.meta.database.PEKey;
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
public final class SchemaExpander {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    /**
     * 功能函数
     * 
     * @param keys
     * @return
     */
    public static ConcurrentMap<String, PEKey> toKeysMap(@NotNull final List<PEKey> keys) {
        final ConcurrentMap<String, PEKey> retMap = new ConcurrentHashMap<>();
        for (final PEKey key : keys) {
            if (StringKit.isNonNil(key.getName())) {
                retMap.put(key.getName(), key);
            }
        }
        return retMap;
    }

    /**
     * 功能函数
     * 
     * @param fields
     * @return
     */
    public static ConcurrentMap<String, PEField> toFieldsMap(@NotNull final List<PEField> fields) {
        final ConcurrentMap<String, PEField> retMap = new ConcurrentHashMap<>();
        for (final PEField field : fields) {
            if (StringKit.isNonNil(field.getName())) {
                retMap.put(field.getName(), field);
            }
        }
        return retMap;
    }

    /**
     * 
     * @param fields
     * @param colName
     * @return
     */
    public static PEField getColumn(@NotNull final ConcurrentMap<String, PEField> fields,
            @NotNull @NotBlank @NotEmpty final String colName) {
        PEField colField = null;
        for (final PEField field : fields.values()) {
            if (StringKit.isNonNil(field.getColumnName()) && StringUtil.equals(field.getColumnName(), colName)) {
                colField = field;
                break;
            }
        }
        return colField;
    }

    /**
     * 注意返回值，获取TreeSet的列名集合
     * 
     * @param fields
     * @return
     */
    public static Set<String> getColumns(@NotNull final ConcurrentMap<String, PEField> fields) {
        // 因为列顺序会对SQL语句生成影响，所以使用了TreeSet的自然排序
        final Set<String> columns = new TreeSet<>();
        for (final PEField field : fields.values()) {
            if (StringKit.isNonNil(field.getColumnName())) {
                columns.add(field.getColumnName());
            }
        }
        return columns;
    }

    /**
     * 获取主键的Schema
     * 
     * @param fields
     * @return
     */
    public static List<PEField> getPrimaryKeys(@NotNull final ConcurrentMap<String, PEField> fields) {
        final List<PEField> retList = new ArrayList<>();
        for (final PEField field : fields.values()) {
            if (field.isPrimaryKey()) {
                retList.add(field);
            }
        }
        return retList;
    }

    /**
     * 获取外键的原始定义
     * 
     * @param keys
     * @return
     */
    public static List<PEKey> getForeignKey(@NotNull final ConcurrentMap<String, PEKey> keys) {
        List<PEKey> foreignKeys = new ArrayList<>();
        for (final PEKey key : keys.values()) {
            if (KeyCategory.ForeignKey == key.getCategory()) {
                foreignKeys.add(key);
            }
        }
        return foreignKeys;
    }

    /**
     * 获取外键的字段定义
     * 
     * @param fields
     * @return
     */
    public static List<PEField> getForeignField(@NotNull final ConcurrentMap<String, PEField> fields) {
        final List<PEField> foreignFields = new ArrayList<>();
        for (final PEField field : fields.values()) {
            if (field.isForeignKey()) {
                foreignFields.add(field);
            }
        }
        return foreignFields;
    }

    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private SchemaExpander() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
