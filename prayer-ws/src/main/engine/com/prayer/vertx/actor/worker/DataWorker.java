package com.prayer.vertx.actor.worker;

import java.util.concurrent.atomic.AtomicInteger;

import com.prayer.facade.constant.Constants;
import com.prayer.fantasm.vtx.verticles.AbstractWorker;

import net.sf.oval.constraint.NotNull;

/**
 * VertX中的Worker部署，根据当前Worker的Class读取对应的EventBus的信息
 * 
 * @author Lang
 *
 */
public class DataWorker extends AbstractWorker {
    // ~ Static Fields =======================================
    /** **/
    @NotNull
    private static final AtomicInteger COUNTER = new AtomicInteger(Constants.ONE);
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public AtomicInteger getCounter(){
        return COUNTER;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
