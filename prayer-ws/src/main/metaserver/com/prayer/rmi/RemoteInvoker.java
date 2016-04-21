package com.prayer.rmi;

import static com.prayer.util.debug.Log.info;
import static com.prayer.util.debug.Log.jvmError;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.engine.rmi.RmiMessages;
import com.prayer.facade.resource.Inceptor;
import com.prayer.facade.resource.Point;
import com.prayer.resource.InceptBus;

/**
 * RMI服务端
 * 
 * @author Lang
 *
 */
public class RemoteInvoker {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(RemoteInvoker.class);
    /** **/
    private static final Inceptor INCEPTOR = InceptBus.build(Point.RMI.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 注册远程对象，并且启动RMI Server
     * 
     * @param remote
     * @param uri
     * @param name
     */
    public static void registry(final Remote remote, final String pattern, final Object... params) {
        try {
            final int port = INCEPTOR.getInt(Point.RMI.RMI_PORT);
            final String address = buildAddrs(pattern, params);
            info(LOGGER, MessageFormat.format(RmiMessages.RMI_ADDR, address));
            /** 1.初始化创建registry **/
            final Registry registry = LocateRegistry.createRegistry(port);
            registry.rebind(address, remote);
            info(LOGGER, MessageFormat.format(RmiMessages.RMI_REGISTRY, remote.getClass(),
                    String.valueOf(remote.hashCode())));
        } catch (RemoteException ex) {
            info(LOGGER, MessageFormat.format(RmiMessages.RMI_ERROR, ex.getMessage()));
            jvmError(LOGGER, ex);
        }
    }

    /**
     * 查找远程对象
     * 
     * @param pattern
     * @param params
     */
    public static Object lookup(final String pattern, final Object... params) {
        Object retRef = null;
        try {
            final String address = buildAddrs(pattern, params);
            final int port = INCEPTOR.getInt(Point.RMI.RMI_PORT);
            final String host = INCEPTOR.getString(Point.RMI.RMI_HOST);
            final Registry registry = LocateRegistry.getRegistry(host, port);
            info(LOGGER, MessageFormat.format(RmiMessages.RMI_LOOKUP, address));
            retRef = registry.lookup(address);
            if (null != retRef) {
                info(LOGGER, MessageFormat.format(RmiMessages.RMI_REFERENCE, retRef.getClass(),
                        String.valueOf(retRef.hashCode())));
            }
        } catch (RemoteException | NotBoundException ex) {
            info(LOGGER, MessageFormat.format(RmiMessages.RMI_CLERROR, ex.getMessage()));
            jvmError(LOGGER, ex);
            ex.printStackTrace();
        }
        return retRef;
    }

    /**
     * 查找远程对象
     * 
     * @param pattern
     * @param params
     * @return
     */
    public static Object lookupDirect(final String pattern, final Object... params)
            throws RemoteException, NotBoundException {
        final String address = buildAddrs(pattern, params);
        final int port = INCEPTOR.getInt(Point.RMI.RMI_PORT);
        final String host = INCEPTOR.getString(Point.RMI.RMI_HOST);
        final Registry registry = LocateRegistry.getRegistry(host, port);
        info(LOGGER, MessageFormat.format(RmiMessages.RMI_LOOKUP, address));
        Object retRef = registry.lookup(address);
        if (null != retRef) {
            info(LOGGER, MessageFormat.format(RmiMessages.RMI_REFERENCE, retRef.getClass(),
                    String.valueOf(retRef.hashCode())));
        }
        return retRef;
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private static String buildAddrs(final String pattern, final Object... params) {
        final int port = INCEPTOR.getInt(Point.RMI.RMI_PORT);
        final String host = INCEPTOR.getString(Point.RMI.RMI_HOST);
        final List<Object> paramsList = new ArrayList<>();
        paramsList.add(host);
        paramsList.add(String.valueOf(port));
        paramsList.addAll(Arrays.asList(params));
        return MessageFormat.format(pattern, paramsList.toArray());
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
