package com.prayer.schema.altimeter;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.schema.IdentifierReferenceException;
import com.prayer.fantasm.exception.AbstractException;

/**
 * 
 * @author Lang
 *
 */
public class _0266IDRefTestCase extends AbstractAltimeterTestCase {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(_0266IDRefTestCase.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    public Logger getLogger() {
        return LOGGER;
    }

    // ~ Methods =============================================
    /**
     * 
     * @throws AbstractException
     */
    @Test(expected = IdentifierReferenceException.class)
    public void testP0266Identifier() throws AbstractException {
        this.executeAltimeter("P037altimeter001");
        failure("[E10037] The identifier could not be refered to the same table, duplicated H2 metadata found.");
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
