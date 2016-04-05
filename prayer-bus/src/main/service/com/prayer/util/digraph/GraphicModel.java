package com.prayer.util.digraph;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.Constants;
import com.prayer.facade.util.graphic.GraphicData;

/**
 * 图的构建类
 * 
 * @author Lang
 *
 */
public class GraphicModel {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 当前图的顶点数组信息 **/
    private transient VertexNode[] nodes;
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
    public GraphicModel(final VertexNode[] nodes, final Edges fromTo) {
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

    // ~ Private Methods =====================================
    private void buildGraphic() {
        final int length = this.nodes.length;
        for (int idx = 0; idx < length; idx++) {
            final VertexNode node = this.nodes[idx];
            if (null != node) {
                final List<EdgeNode> items = this.buildItems(node.getKey());
                this.buildLinks(node, items);
            }
        }
    }

    /**
     * 
     * @param vtNode
     * @param eNodes
     */
    private void buildLinks(final VertexNode vtNode, final List<EdgeNode> eNodes) {
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
    private List<EdgeNode> buildItems(final String inKey) {
        /** 传入key值 **/
        final List<EdgeNode> nodes = new ArrayList<>();
        /** 将传入的key值和mapping做匹配 **/
        for (final String key : this.mapping.fromKeys()) {
            if (null != key && key.equals(inKey)) {
                final List<String> toKeys = this.mapping.findTos(key);
                for (final String toKey : toKeys) {
                    /** 去掉本节点 **/
                    if (!toKey.equals(inKey)) {
                        final GraphicData data = this.findData(toKey);
                        nodes.add(new EdgeNode(data));
                    }
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
    private GraphicData findData(final String key) {
        GraphicData data = null;
        for (final VertexNode node : this.nodes) {
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
            final VertexNode item = this.nodes[idx];
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
                final VertexNode node = this.nodes[idx];
                final String key = node.getKey();
                if (null != key) {
                    builder.append('[').append(idx).append(',').append(key).append(",Weight : ")
                            .append(node.getWeight()).append("]");
                }
                if (null != node.getNext()) {
                    EdgeNode next = node.getNext();
                    if (null != next) {
                        final Integer firstIdx = this.idxMap.get(next.getKey());
                        builder.append(" -> [").append(firstIdx).append(',').append(next.getKey()).append("]");
                        while (null != next.getNext()) {
                            next = next.getNext();
                            final Integer nodeIdx = this.idxMap.get(next.getKey());
                            builder.append(" -> [").append(nodeIdx).append(',').append(next.getKey()).append("]");
                        }
                    }
                }
                builder.append("\n");
            }
        }
        return builder.toString();
    }
}
