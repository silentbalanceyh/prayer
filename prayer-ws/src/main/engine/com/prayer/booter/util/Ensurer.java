package com.prayer.booter.util;

import static com.prayer.util.debug.Log.info;
import static com.prayer.util.reflection.Instance.singleton;

import java.text.MessageFormat;

import org.slf4j.Logger;

import com.prayer.business.deployment.impl.DeployBllor;
import com.prayer.exception.launcher.MetaServerStoppedException;
import com.prayer.facade.business.instantor.deployment.DeployInstantor;
import com.prayer.facade.engine.cv.RmiKeys;
import com.prayer.facade.engine.cv.msg.MsgCommon;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.metaserver.h2.util.RemoteRefers;

/**
 * 判断元数据服务器是否启动
 * 
 * @author Lang
 *
 */
public final class Ensurer {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 判断元数据服务器是否启动
     * 
     * @param clazz
     * @param logger
     * @param appName
     * @return
     */
    public static boolean running(final Class<?> clazz, final Logger logger) throws AbstractException {
        /** 不需要调用Laucher接口 **/
        boolean running = RemoteRefers.isRunning(RmiKeys.META_SERVER_OPTS);
        if (running) {
            info(logger, MessageFormat.format(MsgCommon.META_RUN, clazz.getSimpleName()));
        } else {
            /** Meta Server停止，不可启动Vertx **/
            throw new MetaServerStoppedException(clazz);
        }
        return running;
    }

    /**
     * 将现存系统元数据清空
     * 
     * @param clazz
     * @param logger
     * @param appName
     * @return
     */
    public static boolean purge(final Class<?> clazz, final Logger logger) throws AbstractException {
        /** **/
        final DeployInstantor instantor = singleton(DeployBllor.class);
        boolean purged = instantor.purge();
        if (purged) {
            info(logger, MessageFormat.format(MsgCommon.META_PURGE_RET, clazz.getSimpleName(), purged));
        }
        return purged;
    }
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
