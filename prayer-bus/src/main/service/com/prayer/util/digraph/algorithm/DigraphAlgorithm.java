package com.prayer.util.digraph.algorithm;

import static com.prayer.util.reflection.Instance.singleton;

import com.prayer.exception.system.RecurrenceReferenceException;
import com.prayer.facade.util.digraph.Algorithm;
import com.prayer.util.digraph.Graphic;
import com.prayer.util.digraph.op.DigraphResult;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class DigraphAlgorithm implements Algorithm {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 搜索算法 **/
    private transient DigraphSearcher searcher = singleton(DigraphSearcher.class);
    /** 排序算法 **/
    private transient DigraphSorter sorter = singleton(DigraphSorter.class);

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 深度优先检索
     */
    @Override
    public DigraphResult DFS(@NotNull final Graphic graphic) {
        return this.searcher.DFS(graphic);
    }

    /**
     * 广度优先检索
     * 
     * @param graphic
     * @return
     */
    @Override
    public DigraphResult BFS(@NotNull final Graphic graphic) {
        return this.searcher.BFS(graphic);
    }

    /**
     * 拓扑排序
     */
    @Override
    public DigraphResult topSort(@NotNull final Graphic graphic) throws RecurrenceReferenceException {
        return this.sorter.topologicalSort(graphic);
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
