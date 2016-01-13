package com.prayer.schema.ruler;

import org.junit.Test;

import com.prayer.base.exception.AbstractException;
import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.exception.schema.InvalidValueException;

/**
 * 
 * @author Lang
 *
 */
public class _00712InOrNotVerifierTestCase extends AbstractVerifierTestCase {
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
    public void testP00712Meta10005RelationValue1() throws AbstractException {
        testImport("P00712meta-mappingRELATION10005-1.json");
        failure("[E10005] Meta ( category = RELATION ) attribute mapping must be DIRECT (1)");
    }
    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = InvalidValueException.class)
    public void testP00713Meta10005RelationValue2() throws AbstractException {
        testImport("P00713meta-mappingRELATION10005-2.json");
        failure("[E10005] Meta ( category = RELATION ) attribute policy mustn't be ASSIGNED (1)");
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
