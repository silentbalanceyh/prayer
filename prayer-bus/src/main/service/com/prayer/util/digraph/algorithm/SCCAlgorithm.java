package com.prayer.util.digraph.algorithm;

import static com.prayer.util.reflection.Instance.singleton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import com.prayer.facade.util.digraph.Algorithm;
import com.prayer.facade.util.digraph.StrongConnect;
import com.prayer.util.digraph.CycleNode;
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
        final DigraphResult dfsRet = this.algorithm.DFS(graphic);
        /** 2.对原图执行的Order进行重新计算 **/
        final Node[] orders = buildOrder(graphic, dfsRet);
        /** 3.逆图计算，以及执行第二次DFS结果 **/
        final Graphic rtGraph = new Graphic(orders, graphic.getEdges().revert());
        /** 4.执行逆图的DFS **/
        final DigraphResult dfsRt = this.algorithm.DFS(rtGraph);
        /** 5.构建最终搜索树生成连通分量 **/
        return this.buildTree(dfsRt);
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    
    // ~ Kosaraju Algorithm ==================================
    /**
     * 构建最终连通分量SCC
     * @param dfsRet
     * @return
     */
    private List<CycleNode> buildTree(final DigraphResult dfsRet){
        final ConcurrentMap<Integer,String> path = dfsRet.getPath();
        final List<CycleNode> retTree = new ArrayList<>();
        System.out.println(path);
        return retTree;
    }
    /**
     * dfsRet中存储了原图的DFS访问顺序，这个方法生成新的GT中的顺序
     * 
     * @param data
     * @param dfsRet
     * @return
     */
    private Node[] buildOrder(final Graphic graphic, final DigraphResult dfsRet) {
        /** 1.计算逆图的访问顺序 **/
        final ConcurrentMap<Integer, String> path = dfsRet.getPath();
        /** 2.逆图的节点顺序 **/
        final List<String> keys = new ArrayList<>();
        for (final Integer key : path.keySet()) {
            keys.add(path.get(key));
        }
        /** 3.从数组末尾检索反向顺序，并且构建已经操作集 **/
        final int size = keys.size();
        final Set<String> processed = new HashSet<>();
        final List<Node> retList = new ArrayList<>();
        for (int idx = size - 1; idx >= 0; idx--) {
            final String key = keys.get(idx);
            /** 4.如果已经处理过则不再处理 **/
            if (!processed.contains(key)) {
                final Node nodeRef = graphic.getNode(key);
                final Node item = new Node(nodeRef.getData());
                retList.add(item);
                processed.add(key);
            }
        }
        return retList.toArray(new Node[] {});
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
