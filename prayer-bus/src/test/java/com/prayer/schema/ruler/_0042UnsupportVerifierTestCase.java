package com.prayer.schema.ruler;

import org.junit.Test;

import com.prayer.base.exception.AbstractException;
import com.prayer.exception.schema.UnsupportAttrException;
/**
 * 
 * @author Lang
 *
 */
public class _0042UnsupportVerifierTestCase extends AbstractVerifierTestCase{
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Test(expected = UnsupportAttrException.class)
    public void testP004Meta10002Attr() throws AbstractException {
        this.testImport("P004meta-attr10002.json");
        failure("[E10017] Unsupport Attr occurs !");
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
