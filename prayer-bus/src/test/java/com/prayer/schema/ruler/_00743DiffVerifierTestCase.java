package com.prayer.schema.ruler;

import org.junit.Test;

import com.prayer.base.exception.AbstractException;
import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.exception.schema.DuplicatedTablesException;

/**
 * 
 * @author Lang
 *
 */
public class _00743DiffVerifierTestCase extends AbstractVerifierTestCase {
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
     * @throws AbstractSchemaException
     */
    @Test(expected = DuplicatedTablesException.class)
    public void testP00743Meta10020ECombinatedValue4() throws AbstractException {
        importFile("P00743meta-mappingE-COMBINATED10020-1.json");
        failure("[E10003] Tow duplicated values ! __meta__ -> (table, subtable). (1) ");
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
