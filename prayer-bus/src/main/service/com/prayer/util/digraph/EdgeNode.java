package com.prayer.util.digraph;

import com.prayer.facade.util.graphic.GraphicData;

import net.sf.oval.constraint.NotNull;

/**
 * 当前邻接表的边信息
 * @author Lang
 *
 */
public class EdgeNode {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 当前节点对应的标签信息 **/
    @NotNull
    private transient String key = null;
    /** 当前节点中的数据信息 **/
    @NotNull
    private transient GraphicData data = null;
    /** 这个节点的下一个节点引用 **/
    private transient EdgeNode next = null;
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    
    public EdgeNode(@NotNull final GraphicData data){
        this.data = data;
        if(null != this.data){
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
     * 读取当前这个节点的下一个节点
     * @return
     */
    public EdgeNode getNext(){
        return this.next;
    }
    /**
     * 将一个节点添加到当前节点中
     * @param node
     */
    public void addNext(@NotNull final EdgeNode node){
        this.next = node;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
