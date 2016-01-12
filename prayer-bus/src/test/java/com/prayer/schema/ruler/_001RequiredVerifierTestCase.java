package com.prayer.schema.ruler;

import org.junit.Test;

import com.prayer.base.exception.AbstractException;
import com.prayer.exception.schema.RequiredAttrMissingException;

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
        this.importFile("P001root-fields10001.json");
        failure("[E10001] Required Attr missing!");
    }
    
    /** **/
    @Test(expected = RequiredAttrMissingException.class)
    public void testP001Keys10001() throws AbstractException {
        this.importFile("P001root-keys10001.json");
        failure("[E10001] Required Attr missing!");
    }
    
    /** **/
    @Test(expected = RequiredAttrMissingException.class)
    public void testP001Metas10001() throws AbstractException {
        this.importFile("P001root-meta10001.json");
        failure("[E10001] Required Attr missing!");
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
