package com.prayer.facade.util.digraph;

import com.prayer.exception.system.RecurrenceReferenceException;
import com.prayer.util.digraph.Graphic;
import com.prayer.util.digraph.op.DigraphResult;

/**
 * 基本算法
 * 
 * @author Lang
 *
 */
public interface Algorithm {
    /**
     * 图的深度检索算法DFS
     * 
     * @param graphic
     * @return
     */
    DigraphResult DFS(Graphic graphic);

    /**
     * 图的广度检索算法BFS
     * 
     * @param graphic
     * @return
     */
    DigraphResult BFS(Graphic graphic);
    /**
     * 图的拓扑排序
     * @param graphic
     * @return
     */
    DigraphResult topSort(Graphic graphic) throws RecurrenceReferenceException;
}
