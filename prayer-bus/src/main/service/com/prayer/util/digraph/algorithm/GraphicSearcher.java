package com.prayer.util.digraph.algorithm;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.util.digraph.Graphic;
import com.prayer.util.digraph.Node;

/**
 * 
 * @author Lang
 *
 */
class GraphicSearcher {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 遍历路径 **/
    private ConcurrentMap<Integer, String> visitedMap = new ConcurrentHashMap<>();

    private int visited = 1;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    GraphicSearcher() {
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 深度优先算法
     * 
     * @param graphic
     * @return
     */
    public void DFS(Graphic graphic) {
        /** 1.将图中所有节点初始化为未访问状态 **/
        graphic.initVisited();
        /** 2.初始化当前对应信息 **/
        this.initSearcher();
        /** 3.获取图中顶级Node **/
        final Node[] nodes = graphic.getNodes();
        for (int idx = 0; idx < nodes.length; idx++) {
            // Root
            Node node = nodes[idx];
            // Root -> Next
            visitDFS(graphic, node);
        }
    }

    /**
     * 
     * @return
     */
    public ConcurrentMap<Integer, String> getResult() {
        return this.visitedMap;
    }

    // ~ Private Methods =====================================

    private void initSearcher() {
        this.visitedMap = new ConcurrentHashMap<>();
        this.visited = 1;
    }

    private void visitDFS(Graphic graphic, Node node) {
        if (!node.visited()) {
            node.setVisit(true);
            visitedMap.put(visited, node.getKey());
            System.out.println(visited + " -> " + node.getKey());
            visited++;
        }
        Node visited = node.getNext();
        while (visited != null) {
            Node willNode = graphic.getNode(visited.getKey());
            if (!willNode.visited()) {
                visitDFS(graphic, willNode);
            }
            visited = visited.getNext();
        }
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
