package com.prayer.schema.ruler;

import org.junit.Test;

import com.prayer.base.exception.AbstractException;
import com.prayer.exception.schema.JsonTypeConfusedException;
import com.prayer.exception.schema.ZeroLengthException;

/**
 * 
 * @author Lang
 *
 */
public class _0251ContainerVerifierTestCase extends AbstractVerifierTestCase {
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
    @Test(expected = ZeroLengthException.class)
    public void testP0251Keys1Arr10006() throws AbstractException {
        testImport("zkeys/P0251keys-10006-1.json");
        failure("[E10006] Keys ==> (Failure) There is unexpected exception!");
    }

    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = JsonTypeConfusedException.class)
    public void testP0252Keys1Arr10002() throws AbstractException {
        testImport("zkeys/P0252keys-10002-1.json");
        failure("[E10002] Keys ==> (Failure) There is unexpected exception!");
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode);failure(equals);failure(toString ============================

}
