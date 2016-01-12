package com.prayer.schema.ruler;

import org.junit.Test;

import com.prayer.base.exception.AbstractException;
import com.prayer.exception.schema.JsonTypeConfusedException;

/**
 * 
 * @author Lang
 *
 */
public class _002JTypeVerifierTestCase extends AbstractVerifierTestCase {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Test(expected = JsonTypeConfusedException.class)
    public void testP002Fields10002() throws AbstractException {
        this.importFile("P002root-fields10002.json");
        failure("[E10002] Json Type confused for __fields__ ");
    }

    /** **/
    @Test(expected = JsonTypeConfusedException.class)
    public void testP002Keys10002() throws AbstractException {
        this.importFile("P002root-keys10002.json");
        failure("[E10002] Json Type confused for __keys__ ");
    }

    /** **/
    @Test(expected = JsonTypeConfusedException.class)
    public void testP002Meta10002() throws AbstractException {
        this.importFile("P002root-meta10002.json");
        failure("[E10002] Json Type confused for __meta__ ");
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}