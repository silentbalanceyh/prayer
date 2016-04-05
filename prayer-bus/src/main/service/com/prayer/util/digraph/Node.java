package com.prayer.util.digraph;

import com.prayer.constant.SystemEnum.NodeStatus;
import com.prayer.facade.util.graphic.NodeData;

import net.sf.oval.constraint.NotNull;

/**
 * 当前邻接表的边信息
 * 
 * @author Lang
 *
 */
public class Node {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 当前节点对应的标签信息 **/
    @NotNull
    private transient String key = null;
    /** 当前节点中的数据信息 **/
    @NotNull
    private transient NodeData data = null;
    /** 这个节点的下一个节点引用 **/
    private transient Node next = null;
    /** 节点权重 **/
    private transient int weight = 0;
    /** 初始状态 **/
    private transient NodeStatus status = NodeStatus.WHITE;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param data
     */
    public Node(@NotNull final NodeData data) {
        this.data = data;
        if (null != this.data) {
            this.key = this.data.getKey();
        }
        this.status = NodeStatus.WHITE;
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 重设访问状态
     */
    public void resetStatus(){
        this.status = NodeStatus.WHITE;
    }
    /**
     * 被访问的的状态迁移
     */
    public void moveStatus() {
        switch (this.status) {
        case WHITE:
        case GRAY:
            this.status = NodeStatus.BLACK;
            break;
        case BLACK:
            this.status = NodeStatus.BLACK;
            break;
        }
    }
    /**
     * 
     * @return
     */
    public NodeStatus getStatus(){
        return this.status;
    }
    /**
     * 获取节点中的数据信息
     * 
     * @return
     */
    public NodeData getData() {
        return this.data;
    }

    /**
     * 
     * @return
     */
    public String getKey() {
        return this.key;
    }

    /**
     * 读取当前这个节点的下一个节点
     * 
     * @return
     */
    public Node getNext() {
        return this.next;
    }

    /**
     * 将一个节点添加到当前节点中
     * 
     * @param node
     */
    public void addNext(@NotNull final Node node) {
        this.next = node;
    }

    /**
     * 
     * @param weight
     */
    public void setWeight(final int weight) {
        this.weight = weight;
    }

    /**
     * 
     * @return
     */
    public int getWeight() {
        return this.weight;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
