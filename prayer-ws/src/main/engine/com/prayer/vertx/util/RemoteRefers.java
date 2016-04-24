package com.prayer.vertx.util;

import static com.prayer.util.debug.Log.jvmError;

import java.rmi.RemoteException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.constant.Constants;
import com.prayer.facade.engine.rmi.StandardQuoter;
import com.prayer.facade.resource.Inceptor;
import com.prayer.facade.resource.Point;
import com.prayer.resource.InceptBus;
import com.prayer.util.rmi.CommonOptionsQuoter;
import com.prayer.util.rmi.RemoteInvoker;
import com.prayer.util.string.StringKit;

/**
 * 
 * @author Lang
 *
 */
public final class RemoteRefers {
    // ~ Static Fields =======================================
    /** **/
    private static final Inceptor INCEPTOR = InceptBus.build(Point.RMI.class);
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(RemoteRefers.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * RMI写入
     * 
     * @param name
     * @param information
     */
    public static void registry(final String name, final String information) {
        final String pattern = INCEPTOR.getString(Point.RMI.VERTX);
        try {
            final StandardQuoter quoter = new CommonOptionsQuoter(information);
            RemoteInvoker.registry(quoter, pattern, name);
        } catch (RemoteException ex) {
            jvmError(LOGGER, ex);
        }
    }

    /**
     * 
     * @param name
     * @return
     */
    public static String lookup(final String name) {
        String data = Constants.EMPTY_STR;
        try {
            final String pattern = INCEPTOR.getString(Point.RMI.VERTX);
            final StandardQuoter quoter = (StandardQuoter) RemoteInvoker.lookupLogs(pattern,false,name);
            if (null != quoter) {
                data = quoter.getData();
            }
        } catch (RemoteException ex) {
            jvmError(LOGGER, ex);
        }
        return data;
    }

    /**
     * 
     * @param name
     * @return
     */
    public static boolean isRunning(final String name) {
        final String data = lookup(name);
        return StringKit.isNonNil(data);
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private RemoteRefers() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
