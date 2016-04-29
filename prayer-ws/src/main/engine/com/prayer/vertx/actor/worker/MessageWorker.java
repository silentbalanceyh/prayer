package com.prayer.vertx.actor.worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.fantasm.vtx.verticles.AbstractWorker;

/**
 * VertX中的Worker部署，根据当前Worker的Class读取对应的EventBus的信息
 * 
 * @author Lang
 *
 */
public class MessageWorker extends AbstractWorker {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageWorker.class);
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
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
