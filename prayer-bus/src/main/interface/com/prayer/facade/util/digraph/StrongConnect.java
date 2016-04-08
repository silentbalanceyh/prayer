package com.prayer.facade.util.digraph;

import java.util.List;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.util.digraph.CycleNode;
import com.prayer.util.digraph.Graphic;

/**
 * 强连通分量检测接口，用于检查有向图的SCC
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.ENG_PUBLIC)
public interface StrongConnect {
    /**
     * Kosaraju算法检测强连通分量
     * 
     * @param 传入图引用
     * @return 返回一个单链表的数组，每个元素是一个链表构成的环
     */
    @VertexApi(Api.TOOL)
    List<CycleNode> findSCC(Graphic graphic);
}
