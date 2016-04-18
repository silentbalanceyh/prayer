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
public class DatabaseInceptor extends AbstractInceptor {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 使用默认的Key构造
     */
    public DatabaseInceptor() {
        super("file.database.config");
    }
    
    /**
     * 使用文件获取子操作
     * @param key
     */
    public DatabaseInceptor(@NotNull @NotEmpty @NotBlank final String key){
        super(key);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
