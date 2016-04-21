package com.prayer.metaserver.warranter;

import com.prayer.exception.launcher.ParameterMissedException;
import com.prayer.facade.engine.Warranter;
import com.prayer.facade.resource.Inceptor;
import com.prayer.fantasm.exception.AbstractLauncherException;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 单值验证
 * 
 * @author Lang
 *
 */
// 1.参数必须存在
@Guarded
public class ValueWarranter implements Warranter {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void warrant(@NotNull final Inceptor inceptor, @NotNull final String... keys)
            throws AbstractLauncherException {
        /** 1.参数是否存在 **/
        for (final String key : keys) {
            if (!inceptor.contains(key)) {
                throw new ParameterMissedException(getClass(), key, inceptor.getFile());
            }
        }
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
