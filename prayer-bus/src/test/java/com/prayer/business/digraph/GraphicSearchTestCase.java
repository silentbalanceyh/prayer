package com.prayer.business.digraph;

import static com.prayer.util.reflection.Instance.singleton;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;

import com.prayer.AbstractCommonTool;
import com.prayer.business.impl.ordered.OrderedGraphicer;
import com.prayer.facade.util.digraph.Algorithm;
import com.prayer.facade.util.digraph.StrongConnect;
import com.prayer.fantasm.exception.AbstractSystemException;
import com.prayer.util.digraph.CycleNode;
import com.prayer.util.digraph.Graphic;
import com.prayer.util.digraph.algorithm.DigraphAlgorithm;
import com.prayer.util.digraph.op.DigraphResult;
import com.prayer.util.digraph.scc.KosarajuSCC;

/**
 * 
 * @author Lang
 *
 */
public class GraphicSearchTestCase extends AbstractCommonTool {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    private transient Algorithm algorithm = singleton(DigraphAlgorithm.class);
    /** **/
    private transient StrongConnect kosaraju = singleton(KosarajuSCC.class);
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    protected Logger getLogger() {
        // TODO Auto-generated method stub
        return null;
    }

    /** **/
    @Override
    protected Class<?> getTarget() {
        // TODO Auto-generated method stub
        return null;
    }

    // ~ Methods =============================================
    /** **/
    @Test
    public void testBFS() throws AbstractSystemException {
        final OrderedGraphicer executor = new OrderedGraphicer();
        final Graphic graphic = executor.build("deploy/digraph3/schema");
        System.out.println(graphic);
        DigraphResult result = algorithm.BFS(graphic);
        System.out.println(result);
    }

    /** **/
    @Test
    public void testDFS() throws AbstractSystemException {
        final OrderedGraphicer executor = new OrderedGraphicer();
        final Graphic graphic = executor.build("deploy/digraph3/schema");
        System.out.println(graphic);
        DigraphResult result = algorithm.DFS(graphic);
        System.out.println(result);
    }
    /** **/
    @Test
    public void testKosaraju() throws AbstractSystemException{
        final OrderedGraphicer executor = new OrderedGraphicer();
        final Graphic graphic = executor.build("deploy/digraph3/schema");
        System.out.println(graphic);
        List<CycleNode> nodes = this.kosaraju.findSCC(graphic);
        for(final CycleNode node: nodes){
            System.out.println(node);
        }
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
