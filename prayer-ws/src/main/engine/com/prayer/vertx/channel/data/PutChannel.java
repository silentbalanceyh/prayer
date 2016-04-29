package com.prayer.vertx.channel.data;

import com.prayer.facade.engine.fun.Invoker;
import com.prayer.fantasm.vtx.uca.AbstractChannel;

/**
 * DataRecord访问
 * @author Lang
 * 
 */
public class PutChannel extends AbstractChannel {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public Invoker getAction(){
        return this.getDataKit()::put;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
