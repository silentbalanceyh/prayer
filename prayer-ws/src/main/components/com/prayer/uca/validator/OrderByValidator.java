package com.prayer.uca.validator;

import com.prayer.constant.Constants;
import com.prayer.exception.web.UCAOrderBySpecificationException;
import com.prayer.facade.kernel.Value;
import com.prayer.fantasm.exception.AbstractWebException;
import com.prayer.uca.WebValidator;

import io.vertx.core.json.JsonArray;
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
public class OrderByValidator implements WebValidator {
    // ~ Static Fields =======================================
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
        final JsonArray orderBy = new JsonArray(value.literal());
        // OrderBy Size
        if (Constants.ZERO >= orderBy.size()) {
            throw new UCAOrderBySpecificationException(getClass(), "orders = " + value.literal());
        } else {
            final int size = orderBy.size();
            for (int idx = 0; idx < size; idx++) {
                final Object item = orderBy.getValue(idx);
                if (null != item && JsonObject.class != item.getClass()) {
                    throw new UCAOrderBySpecificationException(getClass(),
                            "idx = " + idx + ", item = " + item.toString());
                }
            }
        }
        return true;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
