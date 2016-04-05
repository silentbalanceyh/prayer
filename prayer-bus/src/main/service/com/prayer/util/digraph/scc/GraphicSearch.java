package com.prayer.util.digraph.scc;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.SystemEnum.NodeStatus;
import com.prayer.util.digraph.Graphic;
import com.prayer.util.digraph.Node;

/**
 * 
 * @author Lang
 *
 */
public class GraphicSearch {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 遍历路径 **/
    private static ConcurrentMap<Integer, String> visitedMap = new ConcurrentHashMap<>();

    private static int visited = 1;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 深度优先算法
     * 
     * @param graphic
     * @return
     */
    public static void DFS(Graphic graphic) {
        graphic.resetNodes();
        final Node[] nodes = graphic.getNodes();
        for (int idx = 0; idx < nodes.length; idx++) {
            // Root
            Node node = nodes[idx];
            if (NodeStatus.BLACK != node.getStatus()) {
                node.moveStatus();
                visitedMap.put(visited, node.getKey() + ":" + node.getStatus().toString());
                System.out.println(visited + " -> " + visitedMap.get(visited));
                visited++;
            }
            // Root -> Next
            if (null != node.getNext()) {
                node = node.getNext();
                visitDFS(graphic, node);
            }
            System.out.println("--------");
        }
    }

    public static void visitDFS(Graphic graphic, Node node) {
        if (node != null && NodeStatus.BLACK != node.getStatus()) {
            node.moveStatus();
            visitedMap.put(visited, node.getKey() + ":" + node.getStatus().toString());
            System.out.println(visited + " -> " + visitedMap.get(visited));
            visited++;
        }
        Node visitedNode = graphic.getNode(node.getKey());
        visitedNode = visitedNode.getNext();
        if (visitedNode != null && NodeStatus.BLACK != visitedNode.getStatus()) {
            visitDFS(graphic, visitedNode);
        }
    }
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
