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

    // ~ Graphic: Start/End ==================================
    /**
     * 返回To中的键集，这个键对应节点中的key
     * 
     * @return
     */
    public Set<String> toKeys() {
        return new HashSet<>(this.toList);
    }

    /**
     * 返回From中的键集
     * 
     * @return
     */
    public Set<String> fromKeys() {
        return new HashSet<>(this.fromList);
    }

    // ~ Graphic：Revert ====================================
    /**
     * 生成反向关系，将原来的To和From置换
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

    // ~ Graphic: Search ====================================
    /**
     * 根据索引查找From
     * 
     * @param to
     * @return
     */
    public String findFrom(final int idx) {
        return this.fromList.get(idx);
    }

    /**
     * 根据索引查找To
     * 
     * @param from
     * @return
     */
    public String findTo(final int idx) {
        return this.toList.get(idx);
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
                tos.add(this.findTo(idx));
            }
        }
        return tos;
    }

    /**
     * 查找当前To中所有的From
     * 
     * @param from
     * @return
     */
    public List<String> findFroms(final String to) {
        List<String> tos = new ArrayList<>();
        for (int idx = 0; idx < this.toList.size(); idx++) {
            final String check = this.toList.get(idx);
            if (check.equals(to)) {
                tos.add(this.findFrom(idx));
            }
        }
        return tos;
    }

    // ~ Graphic: Container ===================================
    /**
     * 清除当前的From和To
     */
    public void clear() {
        this.fromList.clear();
        this.toList.clear();
    }

    /**
     * 返回整体尺寸，防止数组越界，以短节点为主
     * 
     * @return
     */
    public int size() {
        return Math.min(fromList.size(), toList.size());
    }

    /**
     * 删除和key相关的所有关系，必须使用索引移除，保证一一对应
     * 
     * @param key
     */
    public void remove(final String key) {
        /** 获取需要Remove的Key的所有索引值 **/
        final List<Integer> removed = new ArrayList<>();
        /** 从fromList中抽取索引 **/
        int size = this.fromList.size();
        for (int idx = 0; idx < size; idx++) {
            final String fromKey = this.fromList.get(idx);
            if (fromKey.equals(key)) {
                removed.add(idx);
            }
        }
        /** 从toList中抽取索引 **/
        size = this.toList.size();
        for (int idx = 0; idx < size; idx++) {
            final String toKey = this.toList.get(idx);
            if (toKey.equals(key)) {
                removed.add(idx);
            }
        }
        /**
         * 移除所有removed位置的内容，注意这里的索引信息不可以使用Integer，如果使用Integer会识别成Object类型，
         * 调用remove(Object)，不仅仅如此，还需要逆序移除，移除多个时如果不使用逆序会出问题
         * 
         **/
        size = this.fromList.size();
        for (int idx = size - 1; idx >= 0; idx--) {
            if (removed.contains(idx)) {
                this.fromList.remove(idx);
                this.toList.remove(idx);
            }
        }
    }

    // ~ Graphic: Init ========================================
    /**
     * 根据From和To添加Edge边信息
     * 
     * @param from
     * @param to
     */
    public void addEdge(final String from, final String to) {
        this.fromList.add(from);
        this.toList.add(to);
    }

    /**
     * 将整个Edges边中的信息分别添加到当前Edges中
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
