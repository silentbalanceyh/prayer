package com.prayer.script.js;

import static com.prayer.util.debug.Log.peError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.model.type.BinaryType;
import com.prayer.model.type.BooleanType;
import com.prayer.model.type.DateType;
import com.prayer.model.type.DecimalType;
import com.prayer.model.type.IntType;
import com.prayer.model.type.JsonType;
import com.prayer.model.type.LongType;
import com.prayer.model.type.ScriptType;
import com.prayer.model.type.StringType;
import com.prayer.model.type.XmlType;

/**
 * 
 * @author Lang
 *
 */
public final class JSValue {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(JSValue.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 
     * @param value
     * @return
     */
    public static StringType toString(final String value) {
        return new StringType(value);
    }
    /**
     * 
     * @param value
     * @return
     */
    public static IntType toInteger(final String value){
        return new IntType(value);
    }
    /**
     * 
     * @param value
     * @return
     */
    public static LongType toNumber(final String value){
        return new LongType(value);
    }
    /**
     * 
     * @param value
     * @return
     */
    public static BooleanType toLogical(final String value){
        return new BooleanType(value);
    }
    /**
     * 
     * @param value
     * @return
     */
    public static BinaryType toBinary(final String value){
        return new BinaryType(value);
    }
    /**
     * 
     * @param value
     * @return
     */
    public static DecimalType toDecimal(final String value){
        return new DecimalType(value);
    }
    /**
     * 
     * @param value
     * @return
     */
    public static DateType toDate(final String value){
        return new DateType(value);
    }
    /**
     * 
     * @param value
     * @return
     */
    public static ScriptType toScript(final String value) {
        ScriptType ret = null;
        try {
            ret = new ScriptType(value);
        } catch (AbstractDatabaseException ex) {
            peError(LOGGER,ex);
        }
        return ret;
    }

    /**
     * 
     * @param value
     * @return
     */
    public static XmlType toXml(final String value) {
        XmlType ret = null;
        try {
            ret = new XmlType(value);
        } catch (AbstractDatabaseException ex) {
            peError(LOGGER,ex);
        }
        return ret;
    }

    /**
     * 
     * @param value
     * @return
     */
    public static JsonType toJson(final String value) {
        JsonType ret = null;
        try {
            ret = new JsonType(value);
        } catch (AbstractDatabaseException ex) {
            peError(LOGGER,ex);
        }
        return ret;
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private JSValue() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
