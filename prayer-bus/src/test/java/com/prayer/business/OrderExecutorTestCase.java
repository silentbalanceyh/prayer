package com.prayer.business;

import org.junit.Test;
import org.slf4j.Logger;

import com.prayer.AbstractCommonTool;
import com.prayer.business.impl.ordered.OrderExecutor;
import com.prayer.fantasm.exception.AbstractSystemException;
/**
 * 
 * @author Lang
 *
 */
public class OrderExecutorTestCase extends AbstractCommonTool {

    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    @Override
    protected Logger getLogger() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Class<?> getTarget() {
        // TODO Auto-generated method stub
        return null;
    }
    // ~ Methods =============================================
    /** **/
    @Test
    public void testExecute() throws AbstractSystemException {
        final OrderExecutor executor = new OrderExecutor();
        executor.execute("deploy/test/schema");
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
