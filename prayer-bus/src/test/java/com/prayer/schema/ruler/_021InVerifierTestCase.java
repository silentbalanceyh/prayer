package com.prayer.schema.ruler;

import org.junit.Test;

import com.prayer.exception.schema.FKAttrTypeException;
import com.prayer.exception.schema.FKColumnTypeException;
import com.prayer.fantasm.exception.AbstractException;

public class _021InVerifierTestCase extends AbstractVerifierTool {
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
    @Test(expected = FKAttrTypeException.class)
    public void testP0211FKey1Type10014() throws AbstractException {
        testImport("rels/P0211field-FkType10014-1.json");
        failure("[E10014] Fields -> ( foreignkey = true ) ==> (Failure) Attribute 'type' of FK is wrong! ");
    }

    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = FKAttrTypeException.class)
    public void testP0211FKey2Type10014() throws AbstractException {
        testImport("rels/P0211field-FkType10014-2.json");
        failure("[E10014] Fields -> ( foreignkey = true ) ==> (Failure) Attribute 'type' of FK is wrong! ");
    }

    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = FKColumnTypeException.class)
    public void testP0212FKey1Type10015() throws AbstractException {
        testImport("rels/P0212field-FkCType10015-1.json");
        failure("[E10015] Fields -> ( foreignkey = true ) ==> (Failure) Attribute 'columnType' of FK is wrong! ");
    }

    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = FKColumnTypeException.class)
    public void testP0212FKey2Type10015() throws AbstractException {
        testImport("rels/P0212field-FkCType10015-2.json");
        failure("[E10015] Fields -> ( foreignkey = true ) ==> (Failure) Attribute 'columnType' of FK is wrong! ");
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode);failure(equals);failure(toString ============================

}
