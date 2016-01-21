package com.prayer.schema.ruler;

import org.junit.Test;

import com.prayer.exception.schema.JsonTypeConfusedException;
import com.prayer.fantasm.exception.AbstractException;

/**
 * 
 * @author Lang
 *
 */
public class _0255JTypeVerifierTestCase extends AbstractVerifierTestCase {
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
    @Test(expected = JsonTypeConfusedException.class)
    public void testP0252Keys1Arr10002() throws AbstractException {
        testImport("zkeys/P0282keys-10002-1.json");
        failure("[E10002] Keys ==> (Failure) JsonType conflicts and do mot match expected!");
    }
    
    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = JsonTypeConfusedException.class)
    public void testP0252Keys2Arr10002() throws AbstractException {
        testImport("zkeys/P0282keys-10002-2.json");
        failure("[E10002] Keys ==> (Failure) JsonType conflicts and do mot match expected!");
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode);failure(equals);failure(toString ============================

}
