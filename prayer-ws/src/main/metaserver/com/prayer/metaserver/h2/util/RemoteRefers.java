package com.prayer.metaserver.h2.util;

import static com.prayer.util.debug.Log.jvmError;

import java.rmi.RemoteException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.engine.rmi.StandardQuoter;
import com.prayer.facade.resource.Inceptor;
import com.prayer.facade.resource.Point;
import com.prayer.resource.InceptBus;
import com.prayer.util.rmi.CommonOptionsQuoter;
import com.prayer.util.rmi.RemoteInvoker;

import io.vertx.core.json.JsonObject;

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
     * @param options
     */
    public static void registry(final String name, final JsonObject options) {
        final String pattern = INCEPTOR.getString(Point.RMI.META_SERVER);
        try {
            final StandardQuoter quoter = new CommonOptionsQuoter(options.encode());
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
    public static JsonObject lookup(final String name) {
        JsonObject data = new JsonObject();
        try {
            final String pattern = INCEPTOR.getString(Point.RMI.META_SERVER);
            final StandardQuoter quoter = (StandardQuoter) RemoteInvoker.lookup(pattern, name);
            if(null != quoter){
                data = new JsonObject(quoter.service(null));
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
        final JsonObject data = lookup(name);
        return !data.isEmpty();
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
