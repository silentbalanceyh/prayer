package com.prayer.facade.util.digraph;

import java.util.concurrent.ConcurrentMap;

import com.prayer.util.digraph.Graphic;

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
    ConcurrentMap<Integer, String> DFS(Graphic graphic);

    /**
     * 图的广度检索算法BFS
     * 
     * @param graphic
     * @return
     */
    ConcurrentMap<Integer, String> BFS(Graphic graphic);
}
