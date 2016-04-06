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
class DigraphSearcher {
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
    DigraphSearcher() {
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
     * 节点最终访问顺序
     * 
     * @return
     */
    public ConcurrentMap<Integer, String> getResult() {
        return this.visitedMap;
    }

    /**
     * 节点完整路径：完整路径可以计算节点访问的离开路径
     * 
     * @return
     */
    public ConcurrentMap<Integer, String> getPath() {
        return this.stack.path();
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
            /**
             * 从根节点开始执行检索
             */
            Node node = nodes[idx];
            searchFun.visit(graphic, node);
            /**
             * 某一个元素执行完需要清栈，如果出现多层的操作则该内容需要做清理
             */
            this.stack.pop();
        }
    }

    private void initSearcher() {
        this.visitedMap = new ConcurrentHashMap<>();
        this.visited = 1;
        this.stack = new SCCStack();
    }

    private void visitBFS(final Graphic graphic, Node node) {
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

    private void setVisit(final Graphic graphic, final Node node) {
        if (null != node) {
            final Node nodeRef = graphic.getNode(node.getKey());
            if (!nodeRef.visited()) {
                nodeRef.setVisit(true);
                visitedMap.put(visited, node.getKey());
                this.stack.push(node.getKey());
                visited++;
            }
        }
    }

    private void visitDFS(final Graphic graphic, final Node node) {
        /** 1.将Root中第一个邻接点设置为已经访问 **/
        this.setVisit(graphic, node);
        /** 2.外循环查找邻接点 **/
        Node next = node;
        do {
            /** 3.寻找邻接点执行递归 **/
            next = this.findUnvisited(graphic, next);
            /** 4.只有找到节点不为null才会执行递归 **/
            if (null != next) {
                visitDFS(graphic, next);
            }
        } while (null != next);
    }

    private Node findUnvisited(final Graphic graphic, final Node node) {
        Node nodeRef = null;
        if (null != node) {
            /** 1.如果传入节点不为空，则查找这个节点的原节点引用 **/
            final Node rootRef = graphic.getNode(node.getKey());
            /** 2.根据原节点引用获取下一个未访问的邻接点 **/
            nodeRef = rootRef.getNext();
            while (null != nodeRef) {
                /** 3.检查找到的邻接点是否访问过 **/
                final Node nextRef = graphic.getNode(nodeRef.getKey());
                if (!nextRef.visited()) {
                    break;
                } else {
                    nodeRef = nodeRef.getNext();
                }
            }

        }
        if (null == nodeRef) {
            this.stack.pop(node.getKey());
        }
        return nodeRef;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
