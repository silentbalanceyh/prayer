package com.prayer.vertx.uca.validator;

import java.text.MessageFormat;

import com.prayer.exception.web._400ValidatorFailureException;
import com.prayer.facade.model.crucial.Value;
import com.prayer.facade.vtx.uca.Validator;
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
public class RegexValidator extends AbstractUCA implements Validator {
    // ~ Static Fields =======================================
    /** 最小值 **/
    private final static String REGEX = "regex";
    /** Error **/
    private final static String MESSAGE = "The value {0} does not match pattern {1}.";

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
            /** 1.检查Required **/
            this.skewerRequired(config, REGEX);
            /** 2.检查String **/
            this.skewerString(config, REGEX);
            /** 3.读取Pattern **/
            final String regex = this.getString(config, REGEX);
            /** 4.验证 **/
            final boolean ret = Skewer.skewerPattern(value.literal(), regex);
            if (!ret) {
                throw new _400ValidatorFailureException(getClass(),
                        MessageFormat.format(MESSAGE, value.literal(), regex));
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
