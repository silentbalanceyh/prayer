package com.prayer.plugin.validator;

import static com.prayer.util.Error.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.AbstractDatabaseException;
import com.prayer.exception.database.ContentErrorException;
import com.prayer.kernel.i.Validator;
import com.prayer.kernel.i.Value;
import com.prayer.util.cv.Constants;

import jodd.json.JsonException;
import jodd.json.JsonParser;
import net.sf.oval.constraint.InstanceOf;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.Pre;

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
