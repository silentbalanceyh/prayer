package com.prayer.schema.ruler;

import org.junit.Test;

import com.prayer.exception.system.JsonParserException;
import com.prayer.exception.system.ResourceIOException;
import com.prayer.fantasm.exception.AbstractException;

/**
 * 
 * @author Lang
 *
 */
public class _000IOVerifierTestCase extends AbstractVerifierTestCase {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** **/
    @Test(expected = ResourceIOException.class)
    public void testP000System20002() throws AbstractException {
        this.testImport("P000json20003.json");
        failure("[E20002] ResourceIOException !");
    }
    /** **/
    @Test(expected = JsonParserException.class)
    public void testP000System20003() throws AbstractException {
        this.testImport("P000json20003.txt");
        failure("[E20003] JsonParserException !");
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
