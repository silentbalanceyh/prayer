package com.prayer.console.thirdpart.h2;

import java.util.ArrayList;
import java.util.List;

import com.prayer.facade.console.Connector;
import com.prayer.facade.console.message.H2Tidings;
import com.prayer.facade.constant.Constants;
import com.prayer.fantasm.console.AbstractTerminal;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public class H2ShellTerminal extends AbstractTerminal implements H2Tidings {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    private transient final Connector connector = new H2ShellConnector();

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

    // ~ Executing ===========================================
    /** **/
    @Override
    public Runnable execute(final JsonObject data) {
        /** 1.参数 **/
        final List<String> params = new ArrayList<>();
        params.add("-url");
        params.add(data.getJsonObject("server").getString(Data.URL));
        params.add("-user");
        params.add(data.getJsonObject("server").getString("username"));
        params.add("-password");
        params.add(data.getJsonObject("server").getString("password"));
        /** 2.开启新线程 **/
        return new H2ShellThread(params.toArray(Constants.T_STR_ARR));
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
