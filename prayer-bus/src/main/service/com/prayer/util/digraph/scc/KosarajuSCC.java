package com.prayer.util.digraph.scc;

import static com.prayer.util.reflection.Instance.singleton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.prayer.facade.util.digraph.Algorithm;
import com.prayer.facade.util.digraph.StrongConnect;
import com.prayer.util.digraph.CycleNode;
import com.prayer.util.digraph.Graphic;
import com.prayer.util.digraph.Node;
import com.prayer.util.digraph.algorithm.DigraphAlgorithm;
import com.prayer.util.digraph.op.DigraphResult;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 强连通分量检测接口
 * 
 * @author Lang
 * 
 */
@Guarded
public class KosarajuSCC implements StrongConnect {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 基本算法库BFS/DFS/Sort **/
    private transient Algorithm algorithm = singleton(DigraphAlgorithm.class);

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * Kosaraju算法，算法步骤如下：
     */
    // 1.创建一个空栈‘S’，对图做DFS遍历，在顶点访问完成后加入到栈中，访问完成是回溯返回时，而不是第一次发现该节点时
    // 2.得到该图的逆图，即将所有的边反向
    // 3.从‘S’中依次弹出每个顶点，设为‘V’，将V看做图的源点，做第二次DFS遍历
    // 4.可以找到的以‘V’为起点的连通分量
    @Override
    public List<CycleNode> findSCC(@NotNull final Graphic graphic) {
        /** 1.对原图执行DFS **/
        final DigraphResult dfsRet = this.algorithm.DFS(graphic);
        System.out.println(graphic);
        System.out.println(dfsRet);
        /** 2.读取原图的Path **/
        final ConcurrentMap<Integer, String> path = dfsRet.getPath();
        /** 3.读取逆图的Order **/
        final List<String> orders = this.buildOrders(path);
        /** 4.构建逆图 **/
        final Node[] nodes = graphic.getVertex().values().toArray(new Node[] {});
        final Graphic rt = new Graphic(nodes, graphic.getEdges().revert());
        /** 5.设置逆图的Order **/
        rt.setOrder(orders);
        /** 6.在逆图上执行第二次DFS **/
        final DigraphResult rtRet = this.algorithm.DFS(rt);
        System.out.println(rt);
        System.out.println(rtRet);
        return CycleBuilder.buildKosaraju(rtRet);
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    /**
     * 构建逆图的遍历顺序
     * @param path
     * @return
     */
    private List<String> buildOrders(final ConcurrentMap<Integer, String> path) {
        final List<String> orders = new ArrayList<>();
        final int size = path.size();
        for (int idx = size; idx > 0; idx--) {
            final String key = path.get(idx);
            if (!orders.contains(key)) {
                orders.add(key);
            }
        }
        return orders;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
