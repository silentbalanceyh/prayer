package com.prayer.util.digraph;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.SystemEnum.NodeStatus;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.util.digraph.NodeData;

/**
 * 图的构建类
 * 
 * @author Lang
 *
 */
public class Graphic {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 当前图的顶点数组信息 **/
    private transient ConcurrentMap<String, Node> nodes = new ConcurrentHashMap<>();
    /** 图中的Key -> Key的关系表 **/
    private transient Edges mapping;
    /** 遍历顺序 **/
    private transient ConcurrentMap<Integer, String> ordered = new ConcurrentHashMap<>();

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 构建图，使用数据以及关系
     * 
     * @param data
     *            数据对应数组
     * @param fromTo
     *            对应表，从T -> T的对应表信息
     */
    public Graphic(final Node[] nodes, final Edges fromTo) {
        /** 构建内部HashMap **/
        this.buildData(nodes);
        /** 构建关系表 **/
        this.mapping = fromTo;
        /** 有向邻接表构建 **/
        this.buildGraphic();
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================

    // ~ Graphic: Visit ======================================
    /**
     * 重设当前图的节点访问状态
     */
    public void reset() {
        for (final String key : this.nodes.keySet()) {
            this.nodes.get(key).setStatus(NodeStatus.WHITE);
        }
    }

    // ~ Graphic: Operation ==================================
    /**
     * 获取系统中当前节点的一个副本
     * 
     * @return
     */
    public ConcurrentMap<String, Node> getVertex() {
        final ConcurrentMap<String, Node> map = new ConcurrentHashMap<>();
        for (final String key : this.nodes.keySet()) {
            final Node nodeRef = this.nodes.get(key);
            final Node node = new Node(nodeRef.getData());
            map.put(key, node);
        }
        return map;
    }

    /**
     * 
     * @param key
     * @return
     */
    public Node getVertex(final String key) {
        return this.getVertex().get(key);
    }

    /**
     * 
     * @param key
     * @return
     */
    public Node getVertexOrd(final int idx) {
        final String nodeKey = this.ordered.get(idx);
        return this.getVertex().get(nodeKey);
    }

    /**
     * 获取原节点引用
     * 
     * @return
     */
    public ConcurrentMap<String, Node> getVertexRef() {
        return this.nodes;
    }

    /**
     * 
     * @param idx
     * @return
     */
    public Node getVertexRefOrd(final int idx) {
        final String nodeKey = this.ordered.get(idx);
        return this.getVertexRef(nodeKey);
    }

    /**
     * 根据Key值获取原节点引用
     * 
     * @param key
     * @return
     */
    public Node getVertexRef(final String key) {
        return this.nodes.get(key);
    }

    /**
     * 获取当前节点的所有边集
     * 
     * @return
     */
    public Edges getEdges() {
        return this.mapping;
    }

    /**
     * 从当前图中移除Node
     * 
     * @param key
     */
    public void removeNode(final String key) {
        /**
         * 从当前节点中删除key
         */
        this.nodes.remove(key);
        /**
         * 从当前节点的边界中删除key
         */
        this.mapping.remove(key);
        /**
         * 重新构建新图
         */
        this.buildGraphic();
    }

    /**
     * 设置遍历的Order
     * 
     * @param orders
     */
    public void setOrder(final List<String> orders) {
        final ConcurrentMap<Integer, String> newOrd = new ConcurrentHashMap<>();
        final int size = orders.size();
        for (int idx = 0; idx < size; idx++) {
            final String key = orders.get(idx);
            newOrd.put(idx + 1, key);
        }
        this.ordered = newOrd;
    }

    // ~ Private Methods =====================================
    // ~ Graphic : Build =====================================
    private void buildOrder() {
        this.ordered.clear();
        if (!this.nodes.isEmpty()) {
            int order = 1;
            for (final String key : this.nodes.keySet()) {
                this.ordered.put(order, key);
                order++;
            }
        }
    }

    private void buildData(final Node[] nodes) {
        this.nodes.clear();
        for (final Node node : nodes) {
            this.nodes.putIfAbsent(node.getKey(), node);
        }
        /** 遍历的Order **/
        this.buildOrder();
    }

    private void buildGraphic() {
        for (final Node node : nodes.values()) {
            final List<Node> items = this.buildItems(node.getKey());
            this.buildLinks(node, items);
        }
    }

    /**
     * 
     * @param vtNode
     * @param eNodes
     */
    private void buildLinks(final Node vtNode, final List<Node> eNodes) {
        final int size = eNodes.size();
        /** size大于0的情况 **/
        if (Constants.ZERO < size) {
            /**
             * vNode只有一个邻接节点
             */
            vtNode.addAdjacent(eNodes.get(Constants.IDX));
            // 从0开始遍历，但遍历到length - 2
            for (int idx = 0; idx < size - 1; idx++) {
                if (null != eNodes.get(idx) && null != eNodes.get(idx + Constants.ONE)) {
                    eNodes.get(idx).addAdjacent(eNodes.get(idx + Constants.ONE));
                }
            }
        }
        /** 不论是否拥有邻接表，都需要计算入度 **/
        this.buildIndegree(vtNode);
    }

    /** 计算顶点的入度 **/
    private void buildIndegree(final Node vNode) {
        if (null != vNode) {
            final List<String> indegree = this.mapping.findFroms(vNode.getKey());
            vNode.setIndegree(indegree.size());
        }
    }

    /**
     * 查找单个key对应的所有To信息，即查找邻接表信息
     * 
     * @param key
     */
    private List<Node> buildItems(final String inKey) {
        /** 传入key值 **/
        final List<Node> nodes = new ArrayList<>();
        /** 从mapping中查找传入的key对应的tokey值 **/
        final List<String> toKeys = this.mapping.findTos(inKey);
        for (final String toKey : toKeys) {
            final NodeData data = this.findData(toKey);
            nodes.add(new Node(data));
        }
        return nodes;
    }

    /**
     * 使用key查找GraphicData
     * 
     * @param key
     * @return
     */
    private NodeData findData(final String key) {
        NodeData data = null;
        for (final Node node : this.nodes.values()) {
            if (null != node && node.getKey().equals(key)) {
                data = node.getData();
            }
        }
        return data;
    }

    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
    /** 打印当前图信息，使用邻接表方式 **/
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        /** 1.打印所有的Array **/
        if (this.nodes != null) {
            builder.append("------------Node--------------\n");
            for (Node node : this.nodes.values()) {
                do {
                    if (null != node) {
                        String key = node.getKey();
                        if (null != key) {
                            Node value = this.getVertexRef(node.getKey());
                            if (null != value) {
                                builder.append("[V:").append(value.getKey()).append(",I:").append(value.getIndegree())
                                        .append("] -> ");
                                node = node.getAdjacent();
                            }
                        } else {
                            node = null;
                        }
                    }
                } while (null != node);
                builder.append("\n");
            }
        }
        return builder.toString();
    }
}
