package com.prayer.vertx.uca.katana;

import static com.prayer.util.debug.Log.jvmError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.web._400ConfigFormatException;
import com.prayer.facade.vtx.uca.WebKatana;
import com.prayer.fantasm.exception.AbstractWebException;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

/**
 * 
 * @author Lang
 *
 */
public class NumberKatana implements WebKatana {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(NumberKatana.class);
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================

    @Override
    public void interrupt(@NotNull final JsonObject config, @NotNull @NotBlank @NotEmpty final String name)
            throws AbstractWebException {
        try {
            config.getInteger(name);
        } catch (ClassCastException ex) {
            jvmError(LOGGER, ex);
            throw new _400ConfigFormatException(getClass(), name, config, Integer.class.getName());
        }
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}