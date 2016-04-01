package com.prayer.fantasm.business.pre;

import com.prayer.exception.web.ServiceParamInvalidException;
import com.prayer.exception.web.ServiceParamMissingException;
import com.prayer.facade.entity.Ensurer;
import com.prayer.fantasm.exception.AbstractException;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * Ensurer的抽象类
 * 
 * @author Lang
 *
 */
@Guarded
public abstract class AbstractEnsurer<R> implements Ensurer<JsonObject, R> {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    /** 抽象方法，从子类中提供实现方法 **/
    protected abstract R extractValue(JsonObject data, String attr);

    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** 接口方法验证必须参数 **/
    @Override
    public R ensureRequired(@NotNull final JsonObject params, @NotNull @NotEmpty @NotBlank final String attr)
            throws AbstractException {
        R ret = null;
        if (params.containsKey(attr)) {
            try {
                ret = this.extractValue(params,attr);
            } catch (ClassCastException ex) {
                throw new ServiceParamInvalidException(getClass(), ex.getMessage());
            }
        } else {
            throw new ServiceParamMissingException(getClass(), attr);
        }
        return ret;
    }

    /** 接口方法验证可选参数 **/
    public R ensureOptional(@NotNull final JsonObject params, @NotNull @NotEmpty @NotBlank final String attr)
            throws AbstractException {
        R ret = null;
        if (params.containsKey(attr)) {
            try {
                ret = this.extractValue(params,attr);
            } catch (ClassCastException ex) {
                throw new ServiceParamInvalidException(getClass(), ex.getMessage());
            }
        }
        return ret;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
