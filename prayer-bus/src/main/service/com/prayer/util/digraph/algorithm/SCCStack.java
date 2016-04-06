package com.prayer.util.digraph.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 
 * @author Lang
 *
 */
public class SCCStack {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** SCC中的Stack **/
    private transient Stack<String> keys = new Stack<>();
    // ~ Static Block ========================================
    /** 记录时间戳信息 **/
    private transient List<String> container = new ArrayList<>();

    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 压栈
     * 
     * @param key
     */
    public void push(final String key) {
        container.add(key);
        keys.push(key);
    }

    /**
     * 清栈
     */
    public void release() {
        while (!keys.isEmpty() && null != keys.peek()) {
            final String key = keys.pop();
            container.add(key);
        }
    }

    public void console() {
        final int size = this.container.size();
        for (int idx = 0; idx < size; idx++) {
            System.out.println((idx + 1) + " -> " + this.container.get(idx));
        }
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
