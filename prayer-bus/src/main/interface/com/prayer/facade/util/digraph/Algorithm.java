package com.prayer.facade.util.digraph;

import com.prayer.util.digraph.Graphic;
import com.prayer.util.digraph.algorithm.DigraphResult;

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
}
