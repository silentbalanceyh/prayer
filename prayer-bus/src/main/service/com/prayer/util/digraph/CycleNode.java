package com.prayer.util.digraph;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 环形单链表，使用算法检测的返回值
 * 
 * @author Lang
 *
 */
@Guarded
public class CycleNode {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    @NotBlank
    @NotEmpty
    private transient String key = null;
    // ~ Static Block ========================================
    /** **/
    private transient CycleNode next = null;

    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public CycleNode(@NotNull @NotBlank @NotEmpty final String key) {
        this.key = key;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 获取下一个节点
     * 
     * @return
     */
    public CycleNode getNext() {
        return this.next;
    }

    /**
     * 添加当前节点的下一个节点
     * 
     * @param next
     */
    public void addNext(@NotNull final CycleNode next) {
        this.next = next;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
