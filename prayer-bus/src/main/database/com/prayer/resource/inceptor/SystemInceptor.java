package com.prayer.resource.inceptor;

import com.prayer.fantasm.resource.AbstractInceptor;

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
public class SystemInceptor extends AbstractInceptor {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 使用默认的Key构造
     */
    public SystemInceptor() {
        super("file.system.config");
    }
    /**
     * 
     * @param key
     */
    public SystemInceptor(@NotNull @NotEmpty @NotBlank final String key){
        super(key);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
