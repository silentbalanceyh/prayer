package com.prayer.schema.ruler;

import org.junit.Test;

import com.prayer.exception.schema.DuplicatedAttrException;
import com.prayer.exception.schema.DuplicatedColumnException;
import com.prayer.fantasm.exception.AbstractException;

/**
 * 
 * @author Lang
 *
 */
public class _015DuplicatedVerifierTestCase extends AbstractVerifierTestCase {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = DuplicatedAttrException.class)
    public void testP015Fields1name10007() throws AbstractException {
        testImport("fields/P015field-name10007-1.json");
        failure("[E10007] Fields -> name ==> (Failure) Attribute 'name' exists more than once ( Duplicated )! ");
    }

    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = DuplicatedColumnException.class)
    public void testP016Fields1columnName10008() throws AbstractException {
        testImport("fields/P016field-columnName10008-1.json");
        failure("[E10008] Fields -> columnName ==> (Failure) Database column 'columnName' exists more than once ( Duplicated )! ");
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode);failure(equals);failure(toString ============================

}
