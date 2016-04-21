package com.prayer.console.thirdpart.hc;

import com.prayer.facade.console.Connector;
import com.prayer.facade.console.message.HCTidings;
import com.prayer.facade.constant.Constants;
import com.prayer.fantasm.console.AbstractTerminal;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public class HazelcastTerminal extends AbstractTerminal implements HCTidings {

    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    private transient final Connector connector = new HazelcastConnector();
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
        return this.connector;
    }

    /** **/
    @Override
    public Runnable execute(final JsonObject data) {
        return new HazelcastThread(Constants.T_STR_ARR);
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
