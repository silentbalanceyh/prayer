package com.prayer.business.impl.ordered;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.util.digraph.Graphic;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class DatabaseGraphicer {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 从Key到Data的映射 **/
    private transient ConcurrentMap<String, String> dataMap = new ConcurrentHashMap<>();
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 
     * @param tables
     * @return
     */
    public Graphic build(@NotNull final Set<String> tables){
        
        return null;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
