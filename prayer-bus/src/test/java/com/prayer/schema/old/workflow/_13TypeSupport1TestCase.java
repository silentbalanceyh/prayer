package com.prayer.schema.old.workflow;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.exception.schema.UnsupportAttrException;

/**
 * 
 * @author Lang
 *
 */
@Ignore
public class _13TypeSupport1TestCase extends AbstractSchemaTestCase {    // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(_13TypeSupport1TestCase.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    // ~ Methods =============================================
    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = UnsupportAttrException.class)
    public void testP24Fields1Support10017() throws AbstractSchemaException {
        testImport("types/P024field-Type1STRING-Support10017-1.json",
                "[E10017] Fields ==> (Failure) There is unexpected exception!");
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
