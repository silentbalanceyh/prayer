package com.prayer.business.digraph;

import static com.prayer.util.reflection.Instance.singleton;

import org.junit.Test;
import org.slf4j.Logger;

import com.prayer.AbstractCommonTool;
import com.prayer.business.impl.ordered.OrderedGraphicer;
import com.prayer.facade.util.digraph.Algorithm;
import com.prayer.fantasm.exception.AbstractSystemException;
import com.prayer.util.digraph.Graphic;
import com.prayer.util.digraph.algorithm.DigraphAlgorithm;
import com.prayer.util.digraph.op.DigraphResult;

/**
 * 
 * @author Lang
 *
 */
public class GraphicBFSTestCase extends AbstractCommonTool {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    private transient Algorithm algorithm = singleton(DigraphAlgorithm.class);
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
    public void testTableCycle() throws AbstractSystemException {

    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
