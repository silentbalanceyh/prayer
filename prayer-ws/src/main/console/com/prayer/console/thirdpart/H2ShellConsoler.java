package com.prayer.console.thirdpart;

import java.util.ArrayList;
import java.util.List;

import com.prayer.console.OutGoing;
import com.prayer.console.util.Outer;
import com.prayer.facade.console.Connector;
import com.prayer.facade.console.message.H2Tidings;
import com.prayer.facade.constant.Constants;
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
    /** **/
    private transient final Connector connector = new H2Connector();

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
    public void execute(final JsonObject data) {
        /** 1.参数 **/
        final List<String> params = new ArrayList<>();
        params.add("-url");
        params.add(data.getJsonObject("server").getString(Data.URL));
        params.add("-user");
        params.add(data.getJsonObject("server").getString("username"));
        params.add("-password");
        params.add(data.getJsonObject("server").getString("password"));
        /** 2.开启新线程 **/
        Thread shell = new Thread(new H2ShellThread(params.toArray(Constants.T_STR_ARR)));
        shell.start();
        try {
            /** 3.等待子线程结束  **/
            shell.join();
            /** 4.退出 **/
            OutGoing.outExit(this.topic());
        } catch (InterruptedException ex) {
            Outer.outLn(Error.THREAD, ex.getMessage());
        }
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
