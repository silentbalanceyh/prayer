package com.prayer.fantasm.console;

import com.prayer.console.util.OutGoing;
import com.prayer.facade.console.Connector;
import com.prayer.facade.console.Consoler;
import com.prayer.facade.console.message.SharedTidings;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractTerminal implements Consoler {
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
    public abstract Runnable execute(JsonObject data);

    // ~ Override Methods ====================================
    /** **/
    @Override
    public boolean start(final String args[]) {
        /** 1.打印头部信息 **/
        OutGoing.outHeader(this.topic());
        /** 2.连接5秒 **/
        OutGoing.outConnect(this.topic());
        /** 3.连接RMI **/
        if (connector().connecting(args)) {
            OutGoing.outLn(SharedTidings.SUCCESS);
            /** 4.构建线程 **/
            final Runnable thread = this.execute(connector().read());
            /** 5.执行线程最终退出 **/
            this.exit(thread);
        } else {
            OutGoing.outExit(SharedTidings.Error.FAILURE);
        }
        return true;
    }

    // ~ Methods =============================================
    /**
     * 
     * @param thread
     */
    private void exit(final Runnable thread) {
        Thread shell = new Thread(thread);
        shell.start();
        try {
            /** 3.等待子线程结束 **/
            shell.join();
            /** 4.退出 **/
            OutGoing.outExit(null);
        } catch (InterruptedException ex) {
            OutGoing.outLn(SharedTidings.Error.THREAD, ex.getMessage());
        }
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
