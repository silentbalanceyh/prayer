package com.prayer.metaserver.h2.server;

import static com.prayer.util.debug.Log.info;
import static com.prayer.util.debug.Log.jvmError;
import static com.prayer.util.reflection.Instance.singleton;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.engine.Options;
import com.prayer.facade.engine.metaserver.h2.H2RemoteQuoter;
import com.prayer.facade.engine.metaserver.h2.H2Server;
import com.prayer.facade.resource.Inceptor;
import com.prayer.facade.resource.Point;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.fantasm.metaserver.h2.AbstractH2Server;
import com.prayer.metaserver.h2.rmi.H2ServerQuoter;
import com.prayer.metaserver.h2.util.UriResolver;
import com.prayer.metaserver.model.MetaOptions;
import com.prayer.resource.InceptBus;
import com.prayer.rmi.RemoteInvoker;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.InstanceOfAny;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class SingleServer extends AbstractH2Server {
    // ~ Static Fields =======================================
    /** **/
    private static H2Server INSTANCE = null;
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(SingleServer.class);
    /** **/
    private static final String RMI_WEBNAME = "H2/WEB/SINGLE";
    /** **/
    private static final String RMI_DBNAME = "H2/TCP/SINGLE";
    // ~ Instance Fields =====================================
    /** **/
    private transient Options options = null;
    /** **/
    private transient Server database = null;
    /** **/
    private transient final ServerBooter booter;
    /** **/
    private transient final ServerStopper stopper;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 
     * @param options
     * @return
     */
    public static H2Server create(@NotNull @InstanceOfAny(MetaOptions.class) final Options options) {
        if (null == INSTANCE) {
            INSTANCE = new SingleServer(options);
        }
        return INSTANCE;
    }

    // ~ Constructors ========================================
    /** **/
    private SingleServer(final Options options) {
        super(options);
        this.options = options;
        /** 启动、停止 **/
        this.booter = singleton(ServerBooter.class);
        this.stopper = singleton(ServerStopper.class);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    /** **/
    @Override
    public Server getTcpRef() {
        return this.database;
    }

    /** **/
    @Override
    public boolean start() throws AbstractException {
        /** 1.获取Web Console **/
        boolean status = booter.startWebConsole(this.getWebRef());
        /** 2.如果启动成功，则启动Database **/
        if (!status) {
            return false;
        }
        this.database = this.initDatabase(this.options);
        status = booter.startDatabase(this.database, false);
        /** 3.输出最终信息 **/
        if (!status) {
            return false;
        }
        info(LOGGER, MessageFormat.format(Database.JDBC_URI, UriResolver.resolveJdbc(options)));
        /** 4.RMI注册Web **/
        this.registry(RMI_WEBNAME);
        return status;
    }

    /** **/
    @Override
    public boolean stop() throws AbstractException {
        /** 1.获取Web Console **/
        final Server server = this.lookup(RMI_WEBNAME);
        System.out.println(server);
        boolean status = stopper.stopWebConsole(server);
        if (!status) {
            return false;
        }

        /** 2.获取Remote Ref **/
        return false;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private Server lookup(final String name) {
        final Inceptor inceptor = InceptBus.build(Point.RMI.class);
        final String pattern = inceptor.getString(Point.RMI.META_SERVER);
        Server server = null;
        try {
            final Object retRef = RemoteInvoker.lookup(pattern, name);
            final H2RemoteQuoter serverRef = (H2RemoteQuoter) retRef;
            server = serverRef.service(null);
        } catch (RemoteException ex) {
            jvmError(LOGGER, ex);
        }
        return server;
    }

    /**
     * 注册远程引用
     * 
     * @return
     */
    private boolean registry(final String name) {
        final Inceptor inceptor = InceptBus.build(Point.RMI.class);
        final String pattern = inceptor.getString(Point.RMI.META_SERVER);
        try {
            final H2RemoteQuoter webRef = new H2ServerQuoter(this.getWebRef());
            RemoteInvoker.registry(webRef, pattern, name);
        } catch (RemoteException ex) {
            jvmError(LOGGER, ex);
        }
        return false;
    }

    private Server initDatabase(final Options options) {
        /** 1.参数表 **/
        final List<String> params = new ArrayList<>();
        final JsonObject config = options.readOpts();
        // 1.1.端口号提取
        params.add("-tcpPort");
        params.add(String.valueOf(config.getJsonObject("nodes").getInteger("tcp.port")));
        // 1.2.baseDir的计算
        params.add("-baseDir");
        params.add("~/H2/" + config.getJsonObject("server").getString("database"));
        // 1.3.是否允许连接
        if (config.getJsonObject("extension").getBoolean("tcp.allow.others")) {
            params.add("-tcpAllowOthers");
        }
        /** Server实例化 **/
        Server server = null;
        try {
            server = Server.createTcpServer(params.toArray(new String[] {}));
        } catch (SQLException ex) {
            jvmError(getLogger(), ex);
        }
        return server;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
