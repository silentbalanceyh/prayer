package com.prayer.util.digraph.algorithm;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.SystemEnum.NodeStatus;
import com.prayer.facade.fun.graphic.Searcher;
import com.prayer.util.digraph.Graphic;
import com.prayer.util.digraph.Node;
import com.prayer.util.digraph.op.DigraphResult;
import com.prayer.util.digraph.scc.SCCStack;

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
    private transient SCCStack stack = new SCCStack();
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
        /** 3.获取图中顶级Node **/
        final ConcurrentMap<String, Node> nodes = graphic.getVertex();
        for (final Node node : nodes.values()) {
            searchFun.visit(graphic, node);
        }
    }

    private void initialize() {
        this.visitedMap = new ConcurrentHashMap<>();
        this.stack = new SCCStack();
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
        
        // /** 1.将Root中第一个邻接点设置为已经访问 **/
        // this.setVisit(graphic, node);
        // /** 2.外循环查找邻接点 **/
        // Node next = node;
        // do {
        // /** 3.寻找邻接点执行递归 **/
        // next = this.findUnvisited(graphic, next);
        // /** 4.只有找到节点不为null才会执行递归 **/
        // if (null != next) {
        // visitDFS(graphic, next);
        // }
        // } while (null != next);
    }

    private Node findUnvisited(final Graphic graphic, final Node node) {
        return null;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
