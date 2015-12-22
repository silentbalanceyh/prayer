package com.prayer.plugin.validator;

import static com.prayer.util.Log.jvmError;
import static com.prayer.util.Log.peError;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.exception.AbstractDatabaseException;
import com.prayer.exception.database.ContentErrorException;
import com.prayer.facade.kernel.Validator;
import com.prayer.facade.kernel.Value;
import com.prayer.model.type.ScriptType;
import com.prayer.util.cv.Constants;

import net.sf.oval.constraint.InstanceOf;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.constraint.Size;
import net.sf.oval.guard.Guarded;

/**
 * Internal Format：Script格式验证器
 * 
 * @author Lang
 *
 */
@Guarded
final class ScriptValidator implements Validator { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(ScriptType.class);
    /** **/
    private static final ScriptEngine ENGINE = new ScriptEngineManager().getEngineByName(Constants.SCRIPT_ENGINE);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public boolean validate(@NotNull @InstanceOf(Value.class) final Value<?> value,
            @Size(min = 0, max = 0) final Object... params) throws AbstractDatabaseException {
        boolean ret = false;
        if (null == value) {
            ret = true;
        } else {
            try {
                ENGINE.eval(value.literal());
                ret = true;
            } catch (ScriptException ex) {
                jvmError(LOGGER, ex);
                final AbstractDatabaseException error = new ContentErrorException(getClass(), "JavaScript",
                        value.literal());// NOPMD
                peError(LOGGER, error);
                throw error;
            }
        }
        return ret;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
