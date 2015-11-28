package com.prayer.kernel.model;

import java.math.BigDecimal;

import com.prayer.exception.AbstractDatabaseException;
import com.prayer.kernel.i.Value;
import com.prayer.model.type.BinaryType;
import com.prayer.model.type.BooleanType;
import com.prayer.model.type.DataType;
import com.prayer.model.type.DateType;
import com.prayer.model.type.DecimalType;
import com.prayer.model.type.IntType;
import com.prayer.model.type.JsonType;
import com.prayer.model.type.LongType;
import com.prayer.model.type.ScriptType;
import com.prayer.model.type.StringType;
import com.prayer.model.type.XmlType;
import com.prayer.util.cv.Resources;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.InstanceOf;
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
final class ValueTransducer implements Transducer { // NOPMD
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    @NotNull
    public Value<?> getValue(@NotNull final JsonObject data, @NotNull @InstanceOf(DataType.class) final DataType type,
            @NotNull @NotEmpty @NotBlank final String field) throws AbstractDatabaseException{
        Value<?> ret = null;
        switch (type) {
        case BOOLEAN: {
            final boolean value = data.getBoolean(field, Boolean.FALSE);
            ret = new BooleanType(value);
        }
            break;
        case INT: {
            final int value = data.getInteger(field, 0);
            ret = new IntType(value);
        }
            break;
        case LONG: {
            final long value = data.getLong(field, 0L);
            ret = new LongType(value);
        }
            break;
        case DECIMAL: {
            final BigDecimal value = BigDecimal.valueOf(data.getDouble(field, 0.0));
            ret = new DecimalType(value);
        }
            break;
        case DATE: {
            final String value = data.getString(field);
            ret = new DateType(value);
        }
            break;
        case BINARY: {
            final byte[] bytes = data.getString(field).getBytes(Resources.SYS_ENCODING);
            ret = new BinaryType(bytes);
        }
            break;
        case XML: {
            final String value = data.getString(field);
            ret = new XmlType(value);
        }
            break;
        case SCRIPT: {
            final String value = data.getString(field);
            ret = new ScriptType(value);
        }
            break;
        case JSON: {
            final String value = data.getString(field);
            ret = new JsonType(value);
        }
            break;
        default: {
            final String value = data.getString(field);
            ret = new StringType(value);
        }
            break;
        }
        return ret;
    }

    /** **/
    @Override
    @NotNull
    public Value<?> getValue(@NotNull @InstanceOf(DataType.class) final DataType type, final String value)
            throws AbstractDatabaseException { // NOPMD
        Value<?> ret = null;
        switch (type) {
        case BOOLEAN:
            ret = new BooleanType(value);
            break;
        case INT:
            ret = new IntType(value);
            break;
        case LONG:
            ret = new LongType(value);
            break;
        case DECIMAL:
            ret = new DecimalType(value);
            break;
        case DATE:
            ret = new DateType(value);
            break;
        case BINARY:
            ret = new BinaryType(value);
            break;
        case XML:
            ret = new XmlType(value);
            break;
        case SCRIPT:
            ret = new ScriptType(value);
            break;
        case JSON:
            ret = new JsonType(value);
            break;
        default:
            ret = new StringType(value);
            break;
        }
        return ret;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
