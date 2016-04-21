package com.prayer.util.warranter;

import com.prayer.exception.launcher.NumericValueException;
import com.prayer.facade.engine.Warranter;
import com.prayer.facade.resource.Inceptor;
import com.prayer.fantasm.exception.AbstractLauncherException;
import com.prayer.util.string.StringKit;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 数字格式验证
 * 
 * @author Lang
 *
 */
// 1.参数必须是数字存在
@Guarded
public class NumericWarranter implements Warranter {

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
        /** 1.参数是否为数值 **/
        for (final String key : keys) {
            final String value = inceptor.getString(key);
            if (StringKit.isNil(value) || !StringKit.digitsAndSigns(value)) {
                throw new NumericValueException(getClass(), key, value, inceptor.getFile());
            }
        }
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
