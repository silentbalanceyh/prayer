package com.prayer.kernel.validator;

import static com.prayer.util.Error.info;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.AbstractDatabaseException;
import com.prayer.exception.metadata.ContentErrorException;
import com.prayer.kernel.Validator;
import com.prayer.kernel.Value;
import com.prayer.model.type.ScriptType;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * Internal Format：Script格式验证器
 * 
 * @author Lang
 *
 */
@Guarded
final class ScriptValidator implements Validator {    // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(ScriptType.class);
    /** **/
    private static final ScriptEngine ENGINE = new ScriptEngineManager().getEngineByName("nashorn");

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
                ENGINE.eval(value.literal());
                ret = true;
            } catch (ScriptException ex) {
                info(LOGGER, "[E] Script error! Output = " + value, ex);
                throw new ContentErrorException(getClass(), "JavaScript", value.literal());    // NOPMD
            }
        }
        return ret;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
