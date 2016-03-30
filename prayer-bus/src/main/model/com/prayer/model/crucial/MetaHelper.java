package com.prayer.model.crucial;

import static com.prayer.util.Calculator.index;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.model.meta.database.PEField;
import com.prayer.model.type.DataType;

/**
 * 
 * @author Lang
 *
 */
public final class MetaHelper {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 读取字段类型
     * 
     * @param raw
     * @return
     */
    public static ConcurrentMap<String, DataType> extractTypes(final MetaRaw raw) throws AbstractDatabaseException {
        return extractToMap(raw.readNames(), raw.readTypes());
    }

    /**
     * 读取数据列类型
     * 
     * @param raw
     * @return
     * @throws AbstractDatabaseException
     */
    public static ConcurrentMap<String, DataType> extractColumnTypes(final MetaRaw raw)
            throws AbstractDatabaseException {
        return extractToMap(raw.readColumns(), raw.readTypes());
    }

    /**
     * 
     * @param raw
     * @return
     * @throws AbstractDatabaseException
     */
    public static List<PEField> extractIds(final MetaRaw raw) throws AbstractDatabaseException {
        final List<String> ids = raw.readIds();
        final List<PEField> ret = new ArrayList<>();
        for (final String id : ids) {
            ret.add(buildPrimaryKey(id, raw));
        }
        return ret;
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private static PEField buildPrimaryKey(final String field, final MetaRaw raw) throws AbstractDatabaseException {
        final PEField schema = new PEField();
        // 1.Field Name
        schema.setName(field);
        // 2.Basic Information
        final int idx = index(raw.readNames(), field);
        schema.setColumnName(raw.readColumns().get(idx));
        schema.setColumnType(raw.readColumnTypes().get(idx));
        schema.setType(raw.readTypes().get(idx));
        // 3.Extension Info
        schema.setPrimaryKey(true);
        schema.setUnique(true);
        schema.setNullable(false);
        schema.setSubTable(false);
        schema.setForeignKey(false);
        return schema;
    }

    /** **/
    private static ConcurrentMap<String, DataType> extractToMap(final List<String> keys, final List<DataType> values) {
        final ConcurrentMap<String, DataType> types = new ConcurrentHashMap<>();
        for (int idx = 0; idx < keys.size(); idx++) {
            final String field = keys.get(idx);
            final DataType type = values.get(idx);
            types.put(field, type);
        }
        return types;
    }

    private MetaHelper() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
