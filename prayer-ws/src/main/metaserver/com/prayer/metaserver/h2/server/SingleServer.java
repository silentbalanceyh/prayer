package com.prayer.metaserver.h2.server;

import static com.prayer.util.debug.Log.info;
import static com.prayer.util.reflection.Instance.singleton;

import java.text.MessageFormat;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.engine.Options;
import com.prayer.facade.engine.metaserver.h2.H2Server;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.fantasm.metaserver.h2.AbstractH2Server;
import com.prayer.metaserver.h2.server.poll.SingleClosurer;
import com.prayer.metaserver.h2.util.UriResolver;
import com.prayer.metaserver.model.MetaOptions;

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
    private static final String RMI_NAME = "H2/OPTIONS";
    // ~ Instance Fields =====================================
    /** 序列化 **/
    private transient Server database = null;
    /** 序列化 **/
    private transient Server console = null;
    /** **/
    private transient Options options = null;
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
        this.options = options;
        this.database = this.initDatabase(this.options);
        this.console = this.initWebConsole(this.options);
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
    public boolean start() throws AbstractException {
        boolean status = booter.startDatabase(this.database, false);
        /** 1.输出最终信息 **/
        if (!status) {
            return false;
        }
        /** 2.获取Web Console **/
        status = booter.startWebConsole(this.console);
        /** 3.如果启动成功，则启动Database **/
        if (!status) {
            return false;
        }
        /** 4.读取JsonObject **/
        registry(RMI_NAME, this.options.readOpts());
        /** 5.开启轮询线程 **/
        new Thread(new SingleClosurer(this.database, this::exit)).start();
        info(LOGGER, MessageFormat.format(Database.JDBC_URI, UriResolver.resolveJdbc(options)));
        return status;
    }

    /** **/
    @Override
    public boolean stop() throws AbstractException {
        /** 1.获取Web Console **/
        final JsonObject data = lookup(RMI_NAME);
        /** 2.必须从远程读取Options，保证Server和Client使用同样激活的配置 **/
        final Options remoteOpts = new MetaOptions(data);
        /** 3.停止H2 **/
        return stopper.stopDatabase(remoteOpts, false);
    }

    /** **/
    public void exit() {
        info(LOGGER, WebConsole.T_STOPPED);
        System.exit(0);
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
