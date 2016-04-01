package com.prayer.dao.impl.data.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.fantasm.dao.AbstractMetaDalor;
import com.prayer.model.meta.vertx.PEScript;

/**
 * 
 * @author Lang
 *
 */
public class ScriptMetaDalor extends AbstractMetaDalor<PEScript,String> {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(ScriptMetaDalor.class);
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** 日志器 **/
    @Override
    public Logger getLogger() {
        return LOGGER;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
