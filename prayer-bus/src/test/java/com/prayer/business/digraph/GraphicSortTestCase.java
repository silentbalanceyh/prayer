package com.prayer.business.digraph;

import static com.prayer.util.reflection.Instance.singleton;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.slf4j.Logger;

import com.prayer.AbstractCommonTool;
import com.prayer.business.impl.ordered.OrderedGraphicer;
import com.prayer.exception.system.RecurrenceReferenceException;
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
public class GraphicSortTestCase extends AbstractCommonTool {
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
    /** 正常排序 **/
    @Test
    public void testSorting() throws AbstractSystemException {
        final OrderedGraphicer executor = new OrderedGraphicer();
        final Graphic graphic = executor.build("deploy/digraph7/schema");
        DigraphResult result = algorithm.topSort(graphic);
        assertNotNull(result);
    }

    /** 异常排序 **/
    @Test(expected = RecurrenceReferenceException.class)
    public void testSortingError() throws AbstractSystemException {
        final OrderedGraphicer executor = new OrderedGraphicer();
        final Graphic graphic = executor.build("deploy/digraph3/schema");
        DigraphResult result = algorithm.topSort(graphic);
        assertNull(result);
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
