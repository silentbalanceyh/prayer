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
public class _012ContainerVerifierTestCase extends AbstractVerifierTestCase {
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
    public void testP012Fields1Attr10006() throws AbstractException {
        testImport("fields/P012fields-attr10006-1.json");
        failure("[E10006] Fields -> length ==> Attribute length is 0 (Zero) but it shouldn't be !");
    }

    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = JsonTypeConfusedException.class)
    public void testP013Fields1Attr10002() throws AbstractException {
        testImport("fields/P013fields-attr10002-1.json");
        failure("[E10002] Fields ==> Every element of __fields__ node must be Json Object !");
    }

    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = JsonTypeConfusedException.class)
    public void testP013Fields2Attr10002() throws AbstractException {
        testImport("fields/P013fields-attr10002-2.json");
        failure("[E10002] Fields ==> Every element of __fields__ node must be Json Object !");
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
