package com.prayer.uca.validator;

import com.prayer.facade.kernel.Value;
import com.prayer.fantasm.exception.AbstractWebException;
import com.prayer.uca.WebValidator;
import com.prayer.util.web.Extractor;
import com.prayer.util.web.Interruptor;
import com.prayer.util.web.Validator;

import io.vertx.core.json.JsonObject;
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
public class RegexValidator implements WebValidator{
    // ~ Static Fields =======================================
    /** Regex Expression **/
    private final static String REGEX_KEY = "regex";
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================

    /** **/
    @Override
    public boolean validate(@NotNull @NotBlank @NotEmpty final String name, @NotNull final Value<?> value,
            @NotNull final JsonObject config) throws AbstractWebException {
        // 1.检查Validator提供的配置信息
        Interruptor.interruptRequired(getClass(), name, config, REGEX_KEY);
        Interruptor.interruptStringConfig(getClass(), name, config, REGEX_KEY);
        // 2.检查值
        final String regex = Extractor.getString(config, REGEX_KEY);
        // 3.检查Pattern
        return Validator.verifyPattern(value.literal(), regex);
    }
    
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
