package com.prayer.util.digraph.algorithm;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.SystemEnum.NodeStatus;
import com.prayer.facade.fun.graphic.Searcher;
import com.prayer.util.digraph.Graphic;
import com.prayer.util.digraph.Node;
import com.prayer.util.digraph.op.DigraphResult;
import com.prayer.util.digraph.scc.TrackingStack;

/**
 * 
 * @author Lang
 *
 */
public class DigraphSearcher {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 遍历路径 **/
    private ConcurrentMap<Integer, String> visitedMap = new ConcurrentHashMap<>();
    /** 辅助堆栈信息 **/
    private transient TrackingStack stack = new TrackingStack();
    /** 遍历轨迹 **/
    private transient int visited = 1;

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
    public DigraphResult DFS(Graphic graphic) {
        /** DFS算法 **/
        this.visit(graphic, this::visitDFS);
        return new DigraphResult(this.visitedMap, this.stack.path());
    }

    /**
     * 广度优先算法
     * 
     * @param graphic
     */
    public DigraphResult BFS(Graphic graphic) {
        /** BFS算法 **/
        this.visit(graphic, this::visitBFS);
        return new DigraphResult(this.visitedMap, this.stack.path());
    }
    // ~ Private Methods =====================================

    private void visit(final Graphic graphic, final Searcher searchFun) {
        /** 1.将图中所有节点初始化为未访问状态 **/
        graphic.reset();
        /** 2.初始化当前对应信息 **/
        this.initialize();
        /** 3.获取图的节点数 **/
        final int size = graphic.getVertexRef().size();
        /** 4.按照图中顺序遍历 **/
        for (int idx = 0; idx < size; idx++) {
            /** 5.读取Order下的Node，Order从1开始 **/
            final Node node = graphic.getVertexOrd(idx + 1);
            /** 6.递归调用 **/
            searchFun.visit(graphic, node);
        }
    }

    private void initialize() {
        this.visitedMap = new ConcurrentHashMap<>();
        this.stack = new TrackingStack();
        this.visited = 1;
    }

    private void visitBFS(final Graphic graphic, Node node) {
        /** 1.获取原始节点引用 **/
        final Node ajdRef = graphic.getVertexRef(node.getKey());
        /** 2.将搜索到的节点设置成访问 **/
        this.setVisit(ajdRef);
        /** 3.获取当前节点的下一个节点 **/
        final Node ajd = node.getAdjacent();
        if (null != ajd) {
            /** 能够读取到时递归，有了递归就可以不使用循环 **/
            visitBFS(graphic, ajd);
        }
    }

    private void setVisit(final Node nodeRef) {
        if (null != nodeRef && !nodeRef.visited()) {
            /** 1.节点设置成访问 **/
            nodeRef.setStatus(NodeStatus.BLACK);
            /** 2.将该节点压入到堆栈中 **/
            this.stack.push(nodeRef.getKey());
            this.visitedMap.put(visited, nodeRef.getKey());
            visited++;
        }
    }

    private void visitDFS(final Graphic graphic, final Node node) {
        /** 1.获取原始节点引用 **/
        final Node ajdRef = graphic.getVertexRef(node.getKey());
        /** 2.将搜索到的节点设置成访问 **/
        this.setVisit(ajdRef);
        /** 3.获取原始节点引用 **/
        final Node adj = this.findUnvisited(graphic, node);
        if (null != adj) {
            /** 递归读取，有了递归过后可以不使用循环 **/
            visitDFS(graphic, adj);
        }
    }

    private Node findUnvisited(final Graphic graphic, final Node node) {
        Node ret = null;
        if (null != node) {
            /** 1.获取当前节点的引用 **/
            Node nodeRef = graphic.getVertexRef(node.getKey());
            /** 2.定义当前节点的邻接点 **/
            Node adj = nodeRef;
            do {
                /** 3.判断该节点是否访问 **/
                Node adjRef = graphic.getVertexRef(adj.getKey());
                if (!adjRef.visited()) {
                    /** 3.3.如果未访问，则直接返回adj节点 **/
                    ret = adj;
                    break;
                } else {
                    /** 3.2.如果已经访问，则直接返回当前邻接点的下一个节点 **/
                    adj = adj.getAdjacent();
                }
            } while (null != adj);
            if (null == ret) {
                this.stack.pop(node.getKey());
            }
        }
        return ret;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
