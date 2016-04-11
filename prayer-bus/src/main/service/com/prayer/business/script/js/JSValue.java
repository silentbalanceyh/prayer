package com.prayer.business.script.js;

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
    public static StringType string(final String value) {
        return new StringType(value);
    }
    /**
     * 
     * @param value
     * @return
     */
    public static IntType integer(final String value){
        return new IntType(value);
    }
    /**
     * 
     * @param value
     * @return
     */
    public static LongType number(final String value){
        return new LongType(value);
    }
    /**
     * 
     * @param value
     * @return
     */
    public static BooleanType logical(final String value){
        return new BooleanType(value);
    }
    /**
     * 
     * @param value
     * @return
     */
    public static BinaryType binary(final String value){
        return new BinaryType(value);
    }
    /**
     * 
     * @param value
     * @return
     */
    public static DecimalType decimal(final String value){
        return new DecimalType(value);
    }
    /**
     * 
     * @param value
     * @return
     */
    public static DateType date(final String value){
        return new DateType(value);
    }
    /**
     * 
     * @param value
     * @return
     */
    public static ScriptType script(final String value) {
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
    public static XmlType xml(final String value) {
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
    public static JsonType json(final String value) {
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
