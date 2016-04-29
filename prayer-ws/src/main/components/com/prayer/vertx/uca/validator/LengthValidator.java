package com.prayer.vertx.uca.validator;

import java.text.MessageFormat;

import com.prayer.exception.web._400ValidatorFailureException;
import com.prayer.facade.model.crucial.Value;
import com.prayer.facade.vtx.uca.WebValidator;
import com.prayer.fantasm.exception.AbstractWebException;
import com.prayer.fantasm.vtx.uca.AbstractUCA;
import com.prayer.model.web.StatusCode;
import com.prayer.util.vertx.Skewer;
import com.prayer.vertx.web.model.Envelop;

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
public class LengthValidator extends AbstractUCA implements WebValidator {
    // ~ Static Fields =======================================
    /** 最小值 **/
    private final static String MIN_LENGTH = "minLength";
    /** 最大值 **/
    private final static String MAX_LENGTH = "maxLength";
    /** Error **/
    private final static String MESSAGE = "The value \"{0}\" length must be between ( {1} ~ {2} ).";

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public Envelop validate(@NotNull @NotBlank @NotEmpty final String name, @NotNull final Value<?> value,
            @NotNull final JsonObject config) {
        Envelop stumer = Envelop.success();
        try {
            /** 1.检查提供的配置信息 **/
            this.skewerNumber(config, MIN_LENGTH, MAX_LENGTH);
            /** 2.读取配置 **/
            final Integer minLength = getNumber(config, MIN_LENGTH);
            final Integer maxLength = getNumber(config, MAX_LENGTH);
            /** 3.验证 **/
            final boolean ret = Skewer.skewerLength(value.literal(), minLength, maxLength);
            if (!ret) {
                throw new _400ValidatorFailureException(getClass(),
                        MessageFormat.format(MESSAGE, value.literal(), minLength, maxLength));
            }
        } catch (AbstractWebException ex) {
            stumer = Envelop.failure(ex, StatusCode.BAD_REQUEST);
        }
        return stumer;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
