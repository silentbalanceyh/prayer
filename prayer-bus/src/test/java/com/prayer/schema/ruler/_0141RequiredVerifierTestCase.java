package com.prayer.schema.ruler;

import org.junit.Test;

import com.prayer.exception.schema.RequiredAttrMissingException;
import com.prayer.fantasm.exception.AbstractException;

/**
 * 
 * @author Lang
 *
 */
public class _0141RequiredVerifierTestCase extends AbstractVerifierTestCase {
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
    @Test(expected = RequiredAttrMissingException.class)
    public void testP0141Fields1Required10001() throws AbstractException {
        testImport("fields/P0141field-required10001-1.json");
        failure("[E10001] Fields ==> (Failure) There is unexpected exception);failure( 'columnType' missing! ");
    }

    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = RequiredAttrMissingException.class)
    public void testP0141Fields2Required10001() throws AbstractException {
        testImport("fields/P0141field-required10001-2.json");
        failure("[E10001] Fields ==> (Failure) There is unexpected exception);failure( 'columnName' missing! ");
    }

    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = RequiredAttrMissingException.class)
    public void testP0141Fields3Required10001() throws AbstractException {
        testImport("fields/P0141field-required10001-3.json");
        failure("[E10001] Fields ==> (Failure) There is unexpected exception);failure( 'name' missing! ");
    }

    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = RequiredAttrMissingException.class)
    public void testP0141Fields4Required10001() throws AbstractException {
        testImport("fields/P0141field-required10001-4.json");
        failure("[E10001] Fields ==> (Failure) There is unexpected exception);failure( 'type' missing! ");
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode);failure(equals);failure(toString ============================

}
