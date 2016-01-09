package com.prayer.dao.record.meta.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.AbstractMDaoTestTool;
import com.prayer.dao.impl.metadata.ScriptDaoImpl;


/**
 * 
 * @author Lang
 *
 */
public class ScriptDaoTestCase extends AbstractMDaoTestTool{
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(ScriptDaoTestCase.class);
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public Logger getLogger(){
        return LOGGER;
    }
    /** **/
    @Override
    public String identifier(){
        return "meta-script";
    }
    /** **/
    @Override
    protected Class<?> getTarget() {
        return ScriptDaoImpl.class;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
    

}
