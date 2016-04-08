package com.prayer.facade.util.digraph;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.exception.system.RecurrenceReferenceException;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.util.digraph.Graphic;
import com.prayer.util.digraph.op.DigraphResult;

/**
 * 基本算法
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.ENG_PUBLIC)
public interface Algorithm {
    /**
     * 图的深度检索算法DFS
     * 
     * @param graphic
     * @return
     */
    @VertexApi(Api.TOOL)
    DigraphResult DFS(Graphic graphic);

    /**
     * 图的广度检索算法BFS
     * 
     * @param graphic
     * @return
     */
    @VertexApi(Api.TOOL)
    DigraphResult BFS(Graphic graphic);
    /**
     * 图的拓扑排序
     * @param graphic
     * @return
     */
    @VertexApi(Api.TOOL)
    DigraphResult topSort(Graphic graphic) throws RecurrenceReferenceException;
}
