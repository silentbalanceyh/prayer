package com.prayer.plugin.validator;

import static com.prayer.util.debug.Log.jvmError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.exception.AbstractDatabaseException;
import com.prayer.constant.Constants;
import com.prayer.exception.database.ContentErrorException;
import com.prayer.facade.kernel.Validator;
import com.prayer.facade.kernel.Value;

import jodd.json.JsonException;
import jodd.json.JsonParser;
import net.sf.oval.constraint.InstanceOf;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.Pre;

/**
 * Internal Extractor：Json格式验证器
 * 
 * @author Lang
 *
 */
@Guarded
final class JsonValidator implements Validator { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonValidator.class);
    /** **/
    @NotNull
    private transient final JsonParser PARSER = new JsonParser();

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    @Pre(expr = "_this.PARSER != null", lang = Constants.LANG_GROOVY)
    public boolean validate(@InstanceOf(Value.class) final Value<?> value, final Object... params)
            throws AbstractDatabaseException {
        boolean ret = false;
        if (null == value) {
            ret = true;
        } else {
            try {
                PARSER.parse(value.literal());
                ret = true;
            } catch (JsonException ex) {
                jvmError(LOGGER,ex);
                final AbstractDatabaseException error = new ContentErrorException(getClass(), "Json", value.literal());
                jvmError(LOGGER,error);     
                throw error;    // NOPMD
            }

        }
        return ret;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
