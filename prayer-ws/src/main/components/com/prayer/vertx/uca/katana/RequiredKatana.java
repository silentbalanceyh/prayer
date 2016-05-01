package com.prayer.vertx.uca.katana;

import com.prayer.exception.web._400ConfigRequiredException;
import com.prayer.facade.vtx.uca.Katana;
import com.prayer.fantasm.exception.AbstractWebException;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 配置中的必须属性验证
 * 
 * @author Lang
 *
 */
@Guarded
public class RequiredKatana implements Katana {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void interrupt(@NotNull final JsonObject config, @NotNull @NotBlank @NotEmpty final String name)
            throws AbstractWebException {
        if (!config.containsKey(name)) {
            throw new _400ConfigRequiredException(getClass(), name, config);
        }
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
