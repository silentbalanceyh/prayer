package com.prayer.util.digraph.algorithm;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.Constants;
import com.prayer.exception.system.RecurrenceReferenceException;
import com.prayer.util.digraph.Graphic;
import com.prayer.util.digraph.Node;
import com.prayer.util.digraph.op.DigraphResult;

/**
 * 图的排序
 * 
 * @author Lang
 *
 */
public class DigraphSorter {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    private transient final ConcurrentMap<Integer, String> orders = new ConcurrentHashMap<>();
    /** **/
    private transient final ConcurrentMap<Integer, String> path = new ConcurrentHashMap<>();

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 伪拓扑排序
     * 
     * @param graphic
     */
    public DigraphResult topologicalSort(final Graphic graphic) throws RecurrenceReferenceException {
        this.initData();
        /**
         * 1.从graphic中查找入度为0的点
         */
        int visited = 1;
        do {
            final String key = this.findIndegreeZero(graphic);
            if (null == key) {
                /**
                 * 不能找到，表示图有环
                 */
                throw new RecurrenceReferenceException(getClass(), "\n" + graphic.toString());
            }
            /**
             * 将找到的节点移除
             */
            graphic.removeNode(key);
            /**
             * 生成结果
             */
            orders.put(visited, key);
            path.put(visited, key);
            visited++;
        } while (!graphic.getVertexRef().isEmpty());
        return new DigraphResult(this.orders, this.path);
    }

    // ~ Private Methods =====================================
    private void initData() {
        this.orders.clear();
        this.path.clear();
    }

    /**
     * 查找Graphic图中入度为0的点
     * 
     * @param graphic
     * @return
     */
    private String findIndegreeZero(final Graphic graphic) {
        final ConcurrentMap<String, Node> nodes = graphic.getVertexRef();
        String retKey = null;
        for (final Node node : nodes.values()) {
            if (Constants.ZERO == node.getIndegree()) {
                retKey = node.getKey();
            }
        }
        return retKey;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
