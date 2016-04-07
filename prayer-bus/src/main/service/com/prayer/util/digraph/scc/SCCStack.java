package com.prayer.util.digraph.scc;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

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
     * 清栈，并且仅仅清除一个元素
     */
    public void pop(final String inKey) {
        if (!keys.isEmpty() && null != keys.peek() && inKey.equals(keys.peek())) {
            final String key = keys.pop();
            container.add(key);
        }
    }

    /**
     * 清栈，将栈全部清除
     */
    public void pop() {
        while (!keys.isEmpty() && null != keys.peek()) {
            final String key = keys.pop();
            container.add(key);
        }
    }

    /**
     * 获取详细访问路径
     * 
     * @return
     */
    public ConcurrentMap<Integer, String> path() {
        final ConcurrentMap<Integer, String> ret = new ConcurrentHashMap<>();
        final int size = this.container.size();
        for (int idx = 0; idx < size; idx++) {
            ret.put(idx + 1, this.container.get(idx));
        }
        return ret;
    }

    public void console(final String key) {
        System.out.print("Key : " + key + " = ");
        for (int idx = 0; idx < this.container.size(); idx++) {
            System.out.print(this.container.get(idx) + ",");
        }
        System.out.println("Size : " + this.keys.size());
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
