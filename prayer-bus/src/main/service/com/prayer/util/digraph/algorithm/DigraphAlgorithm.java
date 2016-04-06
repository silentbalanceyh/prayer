package com.prayer.util.digraph.algorithm;

import static com.prayer.util.reflection.Instance.singleton;

import java.util.concurrent.ConcurrentMap;

import com.prayer.facade.util.digraph.Algorithm;
import com.prayer.util.digraph.Graphic;

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
    private transient GraphicSearcher searcher = singleton(GraphicSearcher.class);

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 深度检索算法
     */
    @Override
    public ConcurrentMap<Integer, String> DFS(@NotNull final Graphic graphic) {
        /** 1.执行搜索 **/
        this.searcher.DFS(graphic);
        /** 2.获取搜索算法的结果 **/
        return this.searcher.getResult();
    }

    @Override
    public ConcurrentMap<Integer, String> BFS(@NotNull final Graphic graphic) {
        // TODO Auto-generated method stub
        return null;
    }

    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
