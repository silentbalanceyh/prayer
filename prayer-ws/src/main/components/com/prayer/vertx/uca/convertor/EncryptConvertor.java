package com.prayer.vertx.uca.convertor;

import static com.prayer.util.reflection.Instance.singleton;

import java.util.Locale;

import com.prayer.facade.model.crucial.Value;
import com.prayer.facade.util.Encryptor;
import com.prayer.facade.vtx.uca.Convertor;
import com.prayer.fantasm.exception.AbstractWebException;
import com.prayer.fantasm.vtx.uca.AbstractUCA;
import com.prayer.model.web.StatusCode;
import com.prayer.util.encryptor.MD5Encryptor;
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
public class EncryptConvertor extends AbstractUCA implements Convertor {
    // ~ Static Fields =======================================
    /** 加密使用的算法 **/
    private final static String ALGORITHM = "algorithm";

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public Envelop convert(@NotNull @NotBlank @NotEmpty final String name, @NotNull final Value<?> value,
            @NotNull final JsonObject config) {
        Envelop stumer = Envelop.success();
        try {
            /** 1.检查Required **/
            this.skewerRequired(config, ALGORITHM);
            /** 2.检查String **/
            this.skewerString(config, ALGORITHM);
            /** 3.读取算法 **/
            final String algorithm = getString(config, ALGORITHM);
            final String encrypted = this.encrypt(value.literal(), algorithm);
            stumer = Envelop.success(new JsonObject().put("value", encrypted));
        } catch (AbstractWebException ex) {
            stumer = Envelop.failure(ex, StatusCode.BAD_REQUEST);
        }
        return stumer;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private String encrypt(final String value, final String algorithm) {
        String ret = null;
        Encryptor encryptor = null;
        switch (algorithm.toUpperCase(Locale.getDefault())) {
        case "MD5": {
            encryptor = singleton(MD5Encryptor.class);
        }
            break;
        case "SHA1": {

        }
            break;
        case "RSA": {

        }
            break;
        default: {
            ret = value;
            break;
        }
        }
        if (null != encryptor) {
            ret = encryptor.encrypt(value);
        }
        return ret;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
