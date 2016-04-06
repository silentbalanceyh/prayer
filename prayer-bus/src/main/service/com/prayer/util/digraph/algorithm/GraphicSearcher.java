package com.prayer.util.digraph.algorithm;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.facade.fun.graphic.Searcher;
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
    /** 节点访问的Order **/
    private int visited = 1;
    /** 辅助堆栈信息 **/
    private transient SCCStack stack = new SCCStack();

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
        /** DFS算法 **/
        this.visit(graphic, this::visitDFS);
    }

    /**
     * 广度优先算法
     * 
     * @param graphic
     */
    public void BFS(Graphic graphic) {
        /** BFS算法 **/
        this.visit(graphic, this::visitBFS);
    }

    /**
     * 
     * @return
     */
    public ConcurrentMap<Integer, String> getResult() {
        return this.visitedMap;
    }

    // ~ Private Methods =====================================

    private void visit(final Graphic graphic, final Searcher searchFun) {
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
            if (!node.visited()) {
                searchFun.visit(graphic, node);
            }
            this.stack.release();
        }
        // Debug
        this.stack.console();
    }

    private void initSearcher() {
        this.visitedMap = new ConcurrentHashMap<>();
        this.visited = 1;
        this.stack = new SCCStack();
    }

    private void visitBFS(Graphic graphic, Node node) {
        do {
            Node visitedNode = graphic.getNode(node.getKey());
            if (!visitedNode.visited()) {
                visitedNode.setVisit(true);
                visitedMap.put(visited, visitedNode.getKey());
                System.out.println(visited + " -> " + visitedNode.getKey());
                visited++;
            }
            node = node.getNext();
        } while (null != node);
    }

    private void visitDFS(Graphic graphic, Node node) {
        /** 1.直接访问，不判断 **/
        this.stack.push(node.getKey());
        node.setVisit(true);
        visitedMap.put(visited, node.getKey());
        // System.out.println(visited + " -> " + node.getKey());
        visited++;
        /** 2.检索下一个节点，执行递归 **/
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
