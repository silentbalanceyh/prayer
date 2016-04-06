package com.prayer.util.digraph.algorithm;

import static com.prayer.util.reflection.Instance.singleton;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ConcurrentMap;

import com.prayer.facade.util.digraph.Algorithm;
import com.prayer.facade.util.digraph.StrongConnect;
import com.prayer.util.digraph.CycleNode;
import com.prayer.util.digraph.Edges;
import com.prayer.util.digraph.Graphic;
import com.prayer.util.digraph.Node;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 强连通分量检测接口
 * 
 * @author Lang
 *
 */
@Guarded
public class SCCAlgorithm implements StrongConnect {

    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 基本算法库BFS/DFS **/
    private transient Algorithm algorithm = singleton(DigraphAlgorithm.class);

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * Kosaraju算法
     */
    @Override
    public List<CycleNode> execKosaraju(@NotNull final Graphic graphic) {
        /** 1.对原图执行DFS **/
        System.out.println(graphic);
        final ConcurrentMap<Integer, String> dfsRet = this.algorithm.DFS(graphic);
        /** 2.对原图执行的Order进行重新计算 **/
        final Node[] orders = buildOrder(graphic, dfsRet);
        /** 3.逆图计算，以及执行第二次DFS结果 **/
        final Graphic rtGraph = this.buildRevertGraphic(orders, graphic.getEdges());
        System.out.println("---");
        final ConcurrentMap<Integer, String> dfsRtRet = this.algorithm.DFS(rtGraph);
        return null;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    /**
     * dfsRet中存储了原图的DFS访问顺序，这个方法生成新的GT中的顺序
     * 
     * @param data
     * @param dfsRet
     * @return
     */
    private Node[] buildOrder(final Graphic graphic, final ConcurrentMap<Integer, String> dfsRet) {
        final Stack<Integer> revertOrd = new Stack<Integer>();
        for (final Integer order : dfsRet.keySet()) {
            revertOrd.push(order);
        }
        // 将Order反转，因为使用了堆栈，自动反转
        final Node[] retNodes = new Node[dfsRet.size()];
        int idx = 0;
        while (!revertOrd.isEmpty()) {
            final Integer order = revertOrd.pop();
            final String key = dfsRet.get(order);
            final Node node = graphic.getNode(key);
            // 将这个Node填充到新的Node数组中
            retNodes[idx] = new Node(node.getData());
            idx++;
        }
        return retNodes;
    }

    private Graphic buildRevertGraphic(final Node[] data, Edges fromTo) {
        final int length = data.length;
        final Node[] nodes = new Node[length];
        for (int idx = 0; idx < length; idx++) {
            final Node node = new Node(data[idx].getData());
            nodes[idx] = node;
        }
        return new Graphic(nodes, fromTo.revert());
    }

    /**
     * 根据原图各个顶点DFS得到的遍历结束时间，获取节点新的访问次序；
     * 
     * @param data
     * @param dfsRet
     * @return
     */
    // 1.在函数返回时： 新的Order的[i]的含义：原图上第i个节点在逆图的访问次序
    // 2.上边步骤导致结果：后访问的节点在新的访问次数中先访问
    private Node[] newOrder(final Node[] data, final ConcurrentMap<Integer, String> dfsRet) {
        final Node[] newData = Arrays.copyOf(data, data.length);

        return newData;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
