package com.prayer.console.thirdpart;

import com.prayer.facade.console.Connector;
import com.prayer.facade.console.message.H2Tidings;
import com.prayer.fantasm.console.AbstractConsoler;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public class H2ShellConsoler extends AbstractConsoler implements H2Tidings {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public String topic() {
        return TOPIC;
    }

    /** **/
    @Override
    public Connector connector() {
        return new H2Connector();
    }

    // ~ Executing ===========================================
    /** **/
    @Override
    public void execute(final JsonObject data) {
        // TODO Auto-generated method stub
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
