package com.prayer.vertx.launcher;

import static com.prayer.util.debug.Log.peError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.launcher.MetaServerStoppedException;
import com.prayer.facade.engine.Launcher;
import com.prayer.facade.metaserver.h2.H2Messages;
import com.prayer.metaserver.h2.util.RemoteRefers;

import net.sf.oval.guard.Guarded;

/** **/
@Guarded
public class VertxLauncher implements Launcher {

    // ~ Static Fields =======================================
    /** Meta Server是否在运行 **/
    private static final boolean META_RUNNING = RemoteRefers.isRunning(H2Messages.RMI.OPTS_H2);
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(VertxLauncher.class);
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void start() {
        /** 1.判断Meta Server是否在运行 **/
        if(META_RUNNING){
            
        }else{
            /** Meta Server停止，不可启动Vertx **/
            peError(LOGGER,new MetaServerStoppedException(getClass()));
        }
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean running() {
        return true;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
