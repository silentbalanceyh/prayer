package com.prayer.schema.ruler;

import org.junit.Test;

import com.prayer.base.exception.AbstractException;
import com.prayer.exception.schema.ColumnsMissingException;

/**
 * 
 * @author Lang
 *
 */
public class _0262UniqueVerifierTestCase extends AbstractVerifierTestCase {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = ColumnsMissingException.class)
    public void testP34Cross1Spec10023() throws AbstractException {
        testImport("zkeys/P034cross-10023-1.json");
        failure("[E10023] Cross ==> (Failure) Column missing in '__fields__' definition.");
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
