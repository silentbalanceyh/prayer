package com.prayer.util.digraph;

import com.prayer.facade.util.graphic.GraphicData;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * Graphic中的顶点节点类，因为使用了邻接表，所以顶点节点和路径上的节点结构有所区别
 * 
 * @author Lang
 *
 */
@Guarded
public class VertexNode {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 对应的标签信息 **/
    @NotNull
    private transient String key = null;
    /** 当前节点中包含的数据信息 **/
    @NotNull
    private transient GraphicData data = null;
    /** 这个节点的下一个节点引用 **/
    private transient EdgeNode next = null;
    /** 这个节点的权值 **/
    private transient int weight = 0;
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** 构造节点的时候必须传入对应的数据 **/
    @PostValidateThis
    public VertexNode(@NotNull final GraphicData data) {
        this.data = data;
        if (null != this.data) {
            this.key = this.data.getKey();
        }
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 
     * @return
     */
    public String getKey(){
        return this.key;
    }
    /**
     * 读取数据本身
     * @return
     */
    public GraphicData getData(){
        return this.data;
    }
    /**
     * 读取当前这个顶点连接的下一个节点
     * 
     * @return
     */
    public EdgeNode getNext() {
        return this.next;
    }
    /**
     * 获取权值
     * @return
     */
    public int getWeight(){
        return this.weight;
    }
    /**
     * 设置权值
     * @param weight
     */
    public void setWeight(final int weight){
        this.weight = weight;
    }
    /**
     * 将一个节点添加到当前顶点节点中
     * 
     * @param node
     */
    public void addNext(@NotNull final EdgeNode node) {
        this.next = node;
    }
    // ~ hashCode,equals,toString ============================

}
