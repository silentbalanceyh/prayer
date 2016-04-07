package com.prayer.util.digraph.scc;

import static com.prayer.util.reflection.Instance.singleton;

import java.util.List;

import com.prayer.facade.util.digraph.Algorithm;
import com.prayer.facade.util.digraph.StrongConnect;
import com.prayer.util.digraph.CycleNode;
import com.prayer.util.digraph.Graphic;
import com.prayer.util.digraph.algorithm.DigraphAlgorithm;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 强连通分量检测接口
 * 
 * @author Lang
 *
 */
@Guarded
public class TarjanSCC implements StrongConnect {

    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 基本算法库BFS/DFS **/
    @SuppressWarnings("unused")
    private transient Algorithm algorithm = singleton(DigraphAlgorithm.class);

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * Kosaraju算法
     */
    @Override
    public List<CycleNode> findSCC(@NotNull final Graphic graphic) {
        // TODO: 还没实现该算法
        return null;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    
    // ~ Kosaraju Algorithm ==================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
