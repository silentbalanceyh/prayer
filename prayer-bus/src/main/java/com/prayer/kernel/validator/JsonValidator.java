package com.prayer.kernel.validator;

import static com.prayer.util.Error.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.AbstractDatabaseException;
import com.prayer.exception.metadata.ContentErrorException;
import com.prayer.kernel.Validator;
import com.prayer.kernel.Value;

import jodd.json.JsonException;
import jodd.json.JsonParser;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * Internal Format：Json格式验证器
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
    private transient final JsonParser PARSER = new JsonParser();

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public boolean validate(@NotNull final Value<?> value, final Object... params) throws AbstractDatabaseException {
        boolean ret = false;
        if (null == value) {
            ret = true;
        } else {
            try {
                PARSER.parse(value.literal());
                ret = true;
            } catch (JsonException ex) {
                info(LOGGER, "[E] Json Format Error! Output = " + value.literal(), ex);
                throw new ContentErrorException(getClass(), "Json", value.literal()); // NOPMD
            }

        }
        return ret;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
