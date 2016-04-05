package com.prayer.util.digraph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 连线
 * 
 * @author Lang
 *
 */
public class Edges {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 开始节点 **/
    private transient List<String> fromList = new ArrayList<>();
    /** 结束节点 **/
    private transient List<String> toList = new ArrayList<>();

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 返回To集合
     * 
     * @return
     */
    public Set<String> toKeys() {
        return new HashSet<>(this.toList);
    }

    /**
     * 返回From集合
     * 
     * @return
     */
    public Set<String> fromKeys() {
        return new HashSet<>(this.fromList);
    }

    /**
     * 生成反向关系
     * 
     * @return
     */
    public Edges revert() {
        final Edges edges = new Edges();
        final int size = this.size();
        for (int idx = 0; idx < size; idx++) {
            edges.addEdge(this.toList.get(idx), this.fromList.get(idx));
        }
        return edges;
    }

    /**
     * 根据To查找From
     * 
     * @param to
     * @return
     */
    public String findFrom(final int idx) {
        return this.fromList.get(idx);
    }

    /**
     * 查找当前From中所有的Tos
     * 
     * @param from
     * @return
     */
    public List<String> findTos(final String from) {
        List<String> tos = new ArrayList<>();
        for (int idx = 0; idx < this.fromList.size(); idx++) {
            final String check = this.fromList.get(idx);
            if (check.equals(from)) {
                tos.add(this.toList.get(idx));
            }
        }
        return tos;
    }

    /**
     * 根据From查找To
     * 
     * @param from
     * @return
     */
    public String findTo(final int idx) {
        return this.toList.get(idx);
    }

    /**
     * 
     */
    public void clear() {
        this.fromList.clear();
        this.toList.clear();
    }

    /**
     * 添加边信息
     * 
     * @param from
     * @param to
     */
    public void addEdge(final String from, final String to) {
        this.fromList.add(from);
        this.toList.add(to);
    }

    /**
     * 添加整个边信息
     * 
     * @param edges
     */
    public void addEdge(final Edges edges) {
        final int size = edges.size();
        for (int idx = 0; idx < size; idx++) {
            final String from = edges.findFrom(idx);
            final String to = edges.findTo(idx);
            this.addEdge(from, to);
        }
    }

    /**
     * 返回小的尺寸，防止数组越界
     * 
     * @return
     */
    public int size() {
        return Math.min(fromList.size(), toList.size());
    }

    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
    /** **/
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        final int length = this.size();
        for (int idx = 0; idx < length; idx++) {
            builder.append("[ ").append(this.fromList.get(idx)).append(" -> ").append(this.toList.get(idx))
                    .append("]\n");
        }
        return builder.toString();
    }
}
