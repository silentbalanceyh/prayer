package com.prayer.accessor;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.exception.AbstractDatabaseException;
import com.prayer.facade.entity.Entity;
import com.prayer.facade.metadata.mapper.IBatisMapper;
import com.prayer.facade.metadata.mapper.PEScriptMapper;
import com.prayer.model.vertx.PEScript;
import com.prayer.util.reflection.Instance;

/**
 * 
 * @author Lang
 *
 */
public class PEScriptAMTestCase extends AbstractAMTestCase<PEScript> {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(PEScriptAMTestCase.class);
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    // ~ Methods =============================================
    /** **/
    @Test
    public void testInsert() throws AbstractDatabaseException {
        final Entity instance = new PEScript(this.readData("/mapper/pescript/script.json"));
        final Entity inserted = this.getAccessor().insert(instance);
        
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
