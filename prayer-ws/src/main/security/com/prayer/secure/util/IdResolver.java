package com.prayer.secure.util;

import com.prayer.constant.SystemEnum.Credential;
import com.prayer.util.string.StringMater;

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
public final class IdResolver {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    /** 根据用户名计算登录认证模式 **/
    public static Credential resolve(@NotNull @NotEmpty @NotBlank final String username) {
        Credential credential = Credential.USERNAME;
        if (StringMater.isEmail(username)) {
            credential = Credential.EMAIL;
        } else if (StringMater.isMobile(username)) {
            credential = Credential.MOBILE;
        }
        return credential;
    }

    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private IdResolver() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
