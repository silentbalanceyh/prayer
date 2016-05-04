package com.prayer.vertx.actor.worker;

import java.util.concurrent.atomic.AtomicInteger;

import com.prayer.facade.constant.Constants;
import com.prayer.fantasm.vtx.verticles.AbstractWorker;

import net.sf.oval.constraint.NotNull;
/**
 * 用POST方法替代GET行为的Worker
 * @author Lang
 *
 */
public class PostGetWorker extends AbstractWorker{
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
