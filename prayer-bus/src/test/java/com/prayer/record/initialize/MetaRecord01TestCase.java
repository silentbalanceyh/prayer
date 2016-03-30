package com.prayer.record.initialize;

import static com.prayer.util.reflection.Instance.instance;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.AbstractCommonTool;
import com.prayer.model.crucial.MetaRecord;

/**
 * 
 * @author Lang
 *
 */
public class MetaRecord01TestCase extends AbstractCommonTool {

    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(MetaRecord01TestCase.class);
    /** **/
    private static final String IDENTIFIER = "meta-script";
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

    /** **/
    @Override
    protected Class<?> getTarget() {
        return MetaRecord.class;
    }
    // ~ Methods =============================================
    /** **/
    @Test
    public void testC050104Constructor(){
        assertNotNull(message(TST_CONS, getTarget().getName()), instance(getTarget().getName(), IDENTIFIER));
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
