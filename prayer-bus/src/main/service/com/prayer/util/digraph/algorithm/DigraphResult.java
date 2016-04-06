package com.prayer.util.digraph.algorithm;

import java.util.concurrent.ConcurrentMap;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * DFS算法的最终返回结果
 * 
 * @author Lang
 *
 */
@Guarded
public class DigraphResult {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /**
     * 节点的最终访问顺序
     */
    @NotNull
    private transient ConcurrentMap<Integer, String> orders;
    /**
     * 最终节点的访问路径顺序
     */
    @NotNull
    private transient ConcurrentMap<Integer, String> fullPath;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param orders
     * @param fullPath
     */
    @PostValidateThis
    public DigraphResult(@NotNull final ConcurrentMap<Integer, String> orders,
            @NotNull final ConcurrentMap<Integer, String> fullPath) {
        this.orders = orders;
        this.fullPath = fullPath;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 获取节点最终的访问顺序
     * 
     * @return
     */
    public ConcurrentMap<Integer, String> getOrder() {
        return this.orders;
    }

    /**
     * 获取节点访问的路径顺序
     * 
     * @return
     */
    public ConcurrentMap<Integer, String> getPath() {
        return this.fullPath;
    }
    
    
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
