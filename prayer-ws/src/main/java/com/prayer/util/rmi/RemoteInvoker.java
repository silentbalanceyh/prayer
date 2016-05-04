package com.prayer.util.rmi;

import static com.prayer.util.debug.Log.info;
import static com.prayer.util.debug.Log.jvmError;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.engine.cv.msg.MsgRmi;
import com.prayer.facade.engine.rmi.RemoteQuoter;
import com.prayer.facade.resource.Inceptor;
import com.prayer.facade.resource.Point;
import com.prayer.resource.InceptBus;
import com.prayer.util.io.NetKit;

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
    /** **/
    private static Registry REG = null;

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
    public static void registry(final RemoteQuoter<String> remote, final String pattern, final Object... params) {
        try {
            final int port = INCEPTOR.getInt(Point.RMI.RMI_PORT);
            final String address = buildAddrs(pattern, params);
            /** 1.初始化创建registry **/
            Registry registry = getRegistry(port);
            registry.rebind(address, remote);
            info(LOGGER,
                    MessageFormat.format(MsgRmi.RMI_REGINFO, address, remote.getClass().getName(), remote.getData()));
        } catch (RemoteException ex) {
            info(LOGGER, MessageFormat.format(MsgRmi.RMI_ERROR, ex.getMessage()));
            jvmError(LOGGER, ex);
        }
    }

    /**
     * 查找远程对象，打印日志信息
     * 
     * @param pattern
     * @param params
     */
    public static Object lookup(final String pattern, final Object... params) {
        return lookupLogs(pattern, true, params);
    }

    /**
     * 查找远程对象，可控制日志信息的打印
     * 
     * @param pattern
     * @param outLog
     * @param params
     * @return
     */
    public static Object lookupLogs(final String pattern, final boolean outLog, final Object... params) {
        Object retRef = null;
        try {
            retRef = lookupDirect(pattern, outLog, params);
        } catch (RemoteException | NotBoundException ex) {
            info(LOGGER, MessageFormat.format(MsgRmi.RMI_CLERROR, ex.getMessage()));
            jvmError(LOGGER, ex);
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
    public static Object lookupDirect(final String pattern, final boolean outLog, final Object... params)
            throws RemoteException, NotBoundException {
        final String address = buildAddrs(pattern, params);
        final int port = INCEPTOR.getInt(Point.RMI.RMI_PORT);
        final String host = INCEPTOR.getString(Point.RMI.RMI_HOST);
        final Registry registry = LocateRegistry.getRegistry(host, port);
        if (outLog) {
            info(LOGGER, MessageFormat.format(MsgRmi.RMI_LOOKUP, address));
        }
        Object retRef = registry.lookup(address);
        if (null != retRef) {
            if (outLog) {
                info(LOGGER, MessageFormat.format(MsgRmi.RMI_REFERENCE, retRef.getClass(),
                        String.valueOf(retRef.hashCode())));
            }
        }
        return retRef;
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private static Registry getRegistry(final int port) {
        if (null == REG) {
            try {
                if (NetKit.isTaken(port)) {
                    REG = LocateRegistry.getRegistry(INCEPTOR.getString(Point.RMI.RMI_HOST), port);
                } else {
                    REG = LocateRegistry.createRegistry(port);
                }
            } catch (RemoteException ex) {
                info(LOGGER, MessageFormat.format(MsgRmi.RMI_CLERROR, ex.getMessage()));
                jvmError(LOGGER, ex);
            }
        }
        return REG;
    }

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
