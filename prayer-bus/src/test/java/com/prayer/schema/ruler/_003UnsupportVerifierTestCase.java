package com.prayer.schema.ruler;

import org.junit.Test;

import com.prayer.base.exception.AbstractException;
import com.prayer.exception.schema.UnsupportAttrException;

/**
 * 
 * @author Lang
 *
 */
public class _003UnsupportVerifierTestCase extends AbstractVerifierTestCase {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Test(expected = UnsupportAttrException.class)
    public void testP003Unsupport10017() throws AbstractException {
        this.importFile("P003root-fields10017.json");
        failure("[E10017] Unsupport Attr occurs ! ");
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
