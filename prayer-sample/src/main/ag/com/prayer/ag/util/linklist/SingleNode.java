package com.prayer.ag.util.linklist;

/**
 * 单项链表
 * 
 * @author Lang
 *
 */
public class SingleNode<T> {
    // ~ Static Fields =======================================
    // 当前节点中的值
    private T value = null;
    // 当前节点中的下一个节点的引用
    private SingleNode<T> next;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     */
    public SingleNode(){
        this(null);
    }
    /**
     * 
     * @param value
     */
    public SingleNode(final T value) {
        this.value = value;
        // 初始化的时候下一个节点为null
        this.next = null;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * @return the value
     */
    public T getValue() {
        return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(final T value) {
        this.value = value;
    }

    /**
     * @return the next
     */
    public SingleNode<T> getNext() {
        return next;
    }

    /**
     * @param next
     *            the next to set
     */
    public void setNext(final SingleNode<T> next) {
        this.next = next;
    }

    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
