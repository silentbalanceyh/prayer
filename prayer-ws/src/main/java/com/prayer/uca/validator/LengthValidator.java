package com.prayer.uca.validator;

import com.prayer.assistant.Extractor;
import com.prayer.assistant.Interruptor;
import com.prayer.assistant.Validator;
import com.prayer.base.exception.AbstractWebException;
import com.prayer.facade.kernel.Value;
import com.prayer.uca.WebValidator;

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
public class LengthValidator implements WebValidator {
    // ~ Static Fields =======================================
    /** 最小值 **/
    private final static String MIN_LENGTH_KEY = "minLength";
    /** 最大值 **/
    private final static String MAX_LENGTH_KEY = "maxLength";
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public boolean validate(@NotNull @NotBlank @NotEmpty final String name, @NotNull final Value<?> value,
            @NotNull final JsonObject config) throws AbstractWebException {
        // 1.检查Validator提供的配置信息
        Interruptor.interruptNumberConfig(getClass(), name, config, MIN_LENGTH_KEY);
        Interruptor.interruptNumberConfig(getClass(), name, config, MAX_LENGTH_KEY);
        // 2.检查值
        final Integer minLength = Extractor.getNumber(config,MIN_LENGTH_KEY);
        final Integer maxLength = Extractor.getNumber(config,MAX_LENGTH_KEY);
        return Validator.verifyLength(value.literal(), minLength, maxLength);
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
