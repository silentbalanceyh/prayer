package com.prayer.util.digraph;

import com.prayer.constant.SystemEnum.NodeStatus;
import com.prayer.facade.util.digraph.NodeData;

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
    /** 当前节点中的数据信息 **/
    @NotNull
    private transient NodeData data = null;
    /** 这个节点的下一个节点引用 **/
    private transient Node next = null;
    /** 节点权重 **/
    private transient int weight = 0;
    /** 节点的状态：Tarjan算法 **/
    private transient NodeStatus status = NodeStatus.WHITE;
    /** 当前节点的入度 **/
    private transient int indegree = 0;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param data
     */
    public Node(@NotNull final NodeData data) {
        this.data = data;
        this.status = NodeStatus.WHITE;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Graphic: Status =====================================
    /**
     * 设置当前节点的访问状态
     * 
     * @param status
     */
    // WHITE：未访问
    // GRAY：中间态
    // BLACK：已经访问
    public void setStatus(final NodeStatus status) {
        this.status = status;
    }

    /**
     * 返回节点中的访问状态
     * 
     * @return
     */
    public NodeStatus getStatus() {
        return this.status;
    }
    /**
     * 当前节点是否已经访问过
     * @return
     */
    public boolean visited(){
        return NodeStatus.BLACK == this.status;
    }
    // ~ Graphic: Data =======================================
    /**
     * 获取节点中的数据信息，使用NodeData接口
     * 
     * @return
     */
    public NodeData getData() {
        return this.data;
    }

    /**
     * 返回节点中的Key值信息，即使用的标签信息
     * 
     * @return
     */
    public String getKey() {
        String key = null;
        if(null != this.data){
            key = this.data.getKey();
        }
        return key;
    }

    // ~ Graphic: Reference ==================================
    /**
     * 添加当前节点中的一个邻接点
     * 
     * @param node
     */
    public void addAdjacent(@NotNull final Node node) {
        this.next = node;
    }

    /**
     * 读取当前这个节点的邻接点
     * 
     * @return
     */
    public Node getAdjacent() {
        return this.next;
    }

    // ~ Graphic：Integer ====================================
    /**
     * 设置当前节点权重
     * 
     * @param weight
     */
    public void setWeight(final int weight) {
        this.weight = weight;
    }

    /**
     * 读取当前节点权重
     * 
     * @return
     */
    public int getWeight() {
        return this.weight;
    }

    /**
     * 设置当前节点的入度
     * 
     * @param indegree
     */
    public void setIndegree(final int indegree) {
        this.indegree = indegree;
    }

    /**
     * 读取当前节点的入度
     * 
     * @return
     */
    public int getIndegree() {
        return this.indegree;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
