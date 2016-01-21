package com.prayer.schema.ruler;

import org.junit.Test;

import com.prayer.exception.schema.RequiredAttrMissingException;
import com.prayer.fantasm.exception.AbstractException;

/**
 * 
 * @author Lang
 *
 */
public class _001RequiredVerifierTestCase extends AbstractVerifierTestCase {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** **/
    @Test(expected = RequiredAttrMissingException.class)
    public void testP001Fields10001() throws AbstractException {
        this.testImport("P001root-fields10001.json");
        failure("[E10001] Required Attr missing! -> __fields__ ");
    }
    
    /** **/
    @Test(expected = RequiredAttrMissingException.class)
    public void testP001Keys10001() throws AbstractException {
        this.testImport("P001root-keys10001.json");
        failure("[E10001] Required Attr missing! -> __keys__ ");
    }
    
    /** **/
    @Test(expected = RequiredAttrMissingException.class)
    public void testP001Metas10001() throws AbstractException {
        this.testImport("P001root-meta10001.json");
        failure("[E10001] Required Attr missing! -> __meta__ ");
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
