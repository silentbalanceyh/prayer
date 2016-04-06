package com.prayer.facade.fun.graphic;

import com.prayer.util.digraph.Graphic;
import com.prayer.util.digraph.Node;

/**
 * 
 * @author Lang
 *
 */
@FunctionalInterface
public interface Searcher {
    /**
     * 访问节点的函数接口
     * @param graphic
     * @param node
     */
    void visit(Graphic graphic,Node node);
}
