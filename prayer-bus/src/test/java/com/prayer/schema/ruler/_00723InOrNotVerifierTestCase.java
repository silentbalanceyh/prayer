package com.prayer.schema.ruler;

import org.junit.Test;

import com.prayer.exception.schema.InvalidValueException;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.fantasm.exception.AbstractSchemaException;

/**
 * 
 * @author Lang
 *
 */
public class _00723InOrNotVerifierTestCase extends AbstractVerifierTestCase {
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
    @Test(expected = InvalidValueException.class)
    public void testP00723Meta10005EPartialValue1() throws AbstractException {
        testImport("P00723meta-mappingE-PARTIAL10005-1.json");
        failure("[E10005] Meta ( category = RELATION && mapping = PARTIAL ) attribute policy must be ASSIGNED (1)");
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
