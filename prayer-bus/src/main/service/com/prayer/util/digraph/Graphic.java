package com.prayer.util.digraph;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.Constants;
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
    private transient Node[] nodes;
    /** 图中的Key -> Index的对应表 **/
    private transient ConcurrentMap<String, Integer> idxMap = new ConcurrentHashMap<>();
    /** 图中的Key -> Key的关系表 **/
    private transient Edges mapping;

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
        /** 转成Array **/
        this.nodes = nodes;
        /** 构建索引表 **/
        this.idxMap = this.buildIdxMap();
        /** 构建关系表 **/
        this.mapping = fromTo;
        /** 有向邻接表构建 **/
        this.buildGraphic();
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** 
     * 初始化
     */
    public void initVisited(){
        for(Node node: this.nodes){
            node.setVisit(false);
        }
    }
    /**
     * 
     * @return
     */
    public Node[] getNodes() {
        return this.nodes;
    }

    /**
     * 根据Key值获取对应的节点信息
     * 
     * @param key
     * @return
     */
    public Node getNode(final String key) {
        final int idx = this.idxMap.get(key);
        return this.nodes[idx];
    }
    // ~ Private Methods =====================================
    private void buildGraphic() {
        final int length = this.nodes.length;
        for (int idx = 0; idx < length; idx++) {
            final Node node = this.nodes[idx];
            if (null != node) {
                final List<Node> items = this.buildItems(node.getKey());
                this.buildLinks(node, items);
            }
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
            // 设置权值
            vtNode.setWeight(size);
            // 只有一个
            vtNode.addNext(eNodes.get(Constants.IDX));
            // 从0开始遍历，但遍历到length - 2
            for (int idx = 0; idx < size - 1; idx++) {
                if (null != eNodes.get(idx) && null != eNodes.get(idx + Constants.ONE)) {
                    eNodes.get(idx).addNext(eNodes.get(idx + Constants.ONE));
                }
            }
        }
    }

    /**
     * 查找单个key对应的所有To信息
     * 
     * @param key
     */
    private List<Node> buildItems(final String inKey) {
        /** 传入key值 **/
        final List<Node> nodes = new ArrayList<>();
        /** 将传入的key值和mapping做匹配 **/
        for (final String key : this.mapping.fromKeys()) {
            if (null != key && key.equals(inKey)) {
                final List<String> toKeys = this.mapping.findTos(key);
                for (final String toKey : toKeys) {
                    /** 去掉本节点 **/
                    //if (!toKey.equals(inKey)) {
                        final NodeData data = this.findData(toKey);
                        nodes.add(new Node(data));
                    //}
                }
            }
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
        for (final Node node : this.nodes) {
            if (null != node && node.getKey().equals(key)) {
                data = node.getData();
            }
        }
        return data;
    }

    private ConcurrentMap<String, Integer> buildIdxMap() {
        final ConcurrentMap<String, Integer> map = new ConcurrentHashMap<>();
        final int length = this.nodes.length;
        for (int idx = 0; idx < length; idx++) {
            final Node item = this.nodes[idx];
            if (null != item && null != item.getKey()) {
                map.put(item.getKey(), idx);
            }
        }
        return map;
    }

    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
    /** **/
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        /** 1.打印所有的Array **/
        if (this.nodes != null) {
            builder.append("------------Node--------------\n");
            final int length = this.nodes.length;
            for (int idx = 0; idx < length; idx++) {
                Node node = this.nodes[idx];
                do {
                    Node value = this.getNode(node.getKey());
                    builder.append(value.hashCode());
                    builder.append("[I:").append(this.idxMap.get(value.getKey())).append(",V:").append(value.getKey())
                            .append("] -> ");
                    node = node.getNext();
                } while (null != node);
                builder.append("\n");
            }
        }
        return builder.toString();
    }
}
