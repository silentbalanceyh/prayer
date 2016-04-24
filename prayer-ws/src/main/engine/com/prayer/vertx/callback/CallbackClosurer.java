package com.prayer.vertx.callback;

import static com.prayer.util.debug.Log.info;
import static com.prayer.util.debug.Log.jvmError;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.engine.cv.MsgVertx;
import com.prayer.facade.engine.fun.OutLet;
import com.prayer.util.string.StringKit;
import com.prayer.vertx.util.RemoteRefers;

/**
 * 轮询器
 * 
 * @author Lang
 *
 */
public class CallbackClosurer implements Runnable {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(CallbackClosurer.class);
    // ~ Instance Fields =====================================
    /** **/
    private transient String stopAddr;
    /** **/
    private transient OutLet exit;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    public CallbackClosurer(final String stopAddr, final OutLet exit) {
        this.stopAddr = stopAddr;
        this.exit = exit;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void run() {
        /** 10s轮询一次 **/
        int duration = 10000;
        while (true) {
            try {
                /** 1.10s轮询一次 **/
                Thread.sleep(duration);
                final String isStop = RemoteRefers.lookup(this.stopAddr);
                /** 2.读取不到任何值就Stop **/
                if (StringKit.isNil(isStop)) {
                    /** 关闭信息的打印 **/
                    info(LOGGER, MessageFormat.format(MsgVertx.VX_STOPPED, getClass().getSimpleName()));
                    break;
                }
            } catch (InterruptedException ex) {
                jvmError(LOGGER, ex);
            }
        }
        exit.exit();
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
