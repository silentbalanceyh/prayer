package com.prayer.vertx.uca.validator;

import static com.prayer.util.reflection.Instance.singleton;

import java.text.MessageFormat;

import com.prayer.business.instantor.util.UtilBllor;
import com.prayer.exception.web._400ValidatorFailureException;
import com.prayer.facade.business.instantor.uca.UtilInstantor;
import com.prayer.facade.model.crucial.Value;
import com.prayer.facade.vtx.uca.Validator;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.fantasm.vtx.uca.AbstractUCA;
import com.prayer.model.web.StatusCode;
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
public class MissingValidator extends AbstractUCA implements Validator {
    // ~ Static Fields =======================================
    /** Schema的ID **/
    private final static String IDENTIFIER = "identifier";
    /** 需要检查的字段名 **/
    private final static String FIELD = "field";
    /** Error **/
    private final static String MESSAGE = "The record ( identifier = \"{0}\", {1} = \"{2}\" ) does not exist in database.";
    // ~ Instance Fields =====================================
    /** **/
    private transient UtilInstantor instantor = singleton(UtilBllor.class);

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
            /** 1.检查基本配置类型 **/
            this.skewerString(config, IDENTIFIER, FIELD);
            /** 2.读取配置 **/
            final String identifier = getString(config, IDENTIFIER);
            final String field = getString(config, FIELD);
            /** 3.验证 **/
            final boolean ret = this.instantor.checkUnique(identifier, field, value.literal());
            if (!ret) {
                throw new _400ValidatorFailureException(getClass(),
                        MessageFormat.format(MESSAGE, identifier, field, value.literal()));
            }
        } catch (AbstractException ex) {
            stumer = Envelop.failure(ex, StatusCode.BAD_REQUEST);
        }
        return stumer;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
