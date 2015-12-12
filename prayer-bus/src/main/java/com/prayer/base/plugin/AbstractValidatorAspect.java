package com.prayer.base.plugin;

import static com.prayer.util.bus.BusinessLogger.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.model.h2.schema.FieldModel;
import com.prayer.model.kernel.GenericSchema;
import com.prayer.model.type.DataType;
import com.prayer.util.Instance;
import com.prayer.util.bus.BusinessLogger;

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

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractValidatorAspect.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** **/
    protected FieldModel getField(final Object instance, final String field) {
        final GenericSchema schema = Instance.field(instance, "_schema");
        FieldModel fieldSchema = null;
        if (null == schema || null == field) {
            info(LOGGER, BusinessLogger.I_AOP_V_ERROR, schema, field);
        } else {
            fieldSchema = schema.getFields().get(field);
        }
        return fieldSchema;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}