package com.prayer.fantasm.console;

import com.prayer.console.OutGoing;
import com.prayer.console.util.Outer;
import com.prayer.facade.console.Connector;
import com.prayer.facade.console.Consoler;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractConsoler implements Consoler {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    /** 当前Consoler名称 **/
    public abstract String topic();

    /** 连接方法 **/
    public abstract Connector connector();

    /** 参数设置 **/
    public abstract void execute(JsonObject data);

    // ~ Override Methods ====================================
    /** **/
    @Override
    public boolean start() {
        /** 1.打印头部信息 **/
        OutGoing.outHeader();
        /** 2.连接5秒 **/
        OutGoing.outConnect(this.topic());
        /** 3.连接RMI **/
        if (connector().connecting()) {
            /** 4.连接成功 **/
            this.execute(connector().read());
        } else {
            OutGoing.outExit("Connection refused.");
        }
        return true;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
