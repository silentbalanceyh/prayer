package com.prayer.schema.ruler;

import org.junit.Test;

import com.prayer.base.exception.AbstractException;
import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.constant.DBConstants;
import com.prayer.constant.Resources;
import com.prayer.exception.schema.BTableNotExistingException;

/**
 * 
 * @author Lang
 *
 */
public class _00744TableVerifierTestCase extends AbstractVerifierTestCase {
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
    @Test(expected = BTableNotExistingException.class)
    public void testP00744Meta10027ECombinatedTable() throws AbstractException {
        // TODO: 目前只有SQL模式才检查
        if (Resources.DB_MODE.equals(DBConstants.MODE_SQL)) {
            importFile("P00744meta-mappingE-COMBINATED10027-1.json");
            failure("[E10027] Table does not exist! ");
        }
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
