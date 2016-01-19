package com.prayer.base.plugin;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.prayer.constant.Constants;
import com.prayer.facade.schema.Schema;
import com.prayer.model.meta.database.PEField;
import com.prayer.model.type.DataType;
import com.prayer.util.reflection.Instance;

/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractValidatorAspect { // NOPMD
    // ~ Static Fields =======================================
    /** 符合该验证器的属性 **/
    protected static final DataType[] T_TEXT = new DataType[] { DataType.STRING, DataType.XML, DataType.JSON,
            DataType.SCRIPT };
    /** 数值类型 **/
    protected static final DataType[] T_NUMBER = new DataType[] { DataType.INT, DataType.LONG };

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** **/
    protected PEField getField(final Object instance, final String field) {
        final Schema schema = Instance.field(instance, "_schema");
        PEField fieldSchema = null;
        if (null != schema && null != field) {
            final List<PEField> fields = Arrays.asList(schema.fields());
            fieldSchema = fields.stream().filter(item -> item.getName().equals(field)).collect(Collectors.toList())
                    .get(Constants.IDX);
        }
        return fieldSchema;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
