package com.prayer.schema.ruler;

import org.junit.Test;

import com.prayer.base.exception.AbstractException;
import com.prayer.exception.schema.RequiredAttrMissingException;

/**
 * 
 * @author Lang
 *
 */
public class _0041RequiredVerifierTestCase extends AbstractVerifierTestCase {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Test(expected = RequiredAttrMissingException.class)
    public void testP004Meta10001Name() throws AbstractException {
        this.testImport("P004meta-name10001.json");
        failure("[E10001] Required Attr missing! -> __meta__ -> name ");
    }
    /** **/
    @Test(expected = RequiredAttrMissingException.class)
    public void testP004Meta10001Namespace() throws AbstractException {
        this.testImport("P004meta-namespace10001.json");
        failure("[E10001] Required Attr missing! -> __meta__ -> namespace ");
    }
    /** **/
    @Test(expected = RequiredAttrMissingException.class)
    public void testP004Meta10001Category() throws AbstractException {
        this.testImport("P004meta-category10001.json");
        failure("[E10001] Required Attr missing! -> __meta__ -> category ");
    }
    /** **/
    @Test(expected = RequiredAttrMissingException.class)
    public void testP004Meta10001Table() throws AbstractException {
        this.testImport("P004meta-table10001.json");
        failure("[E10001] Required Attr missing! -> __meta__ -> table ");
    }
    /** **/
    @Test(expected = RequiredAttrMissingException.class)
    public void testP004Meta10001Identifier() throws AbstractException {
        this.testImport("P004meta-identifier10001.json");
        failure("[E10001] Required Attr missing! -> __meta__ -> identifier ");
    }
    /** **/
    @Test(expected = RequiredAttrMissingException.class)
    public void testP004Meta10001Mapping() throws AbstractException {
        this.testImport("P004meta-mapping10001.json");
        failure("[E10001] Required Attr missing! -> __meta__ -> mapping ");
    }
    /** **/
    @Test(expected = RequiredAttrMissingException.class)
    public void testP004Meta10001Policy() throws AbstractException {
        this.testImport("P004meta-policy10001.json");
        failure("[E10001] Required Attr missing! -> __meta__ -> policy ");
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
