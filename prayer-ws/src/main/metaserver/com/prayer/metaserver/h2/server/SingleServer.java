package com.prayer.metaserver.h2.server;

import static com.prayer.util.debug.Log.info;
import static com.prayer.util.reflection.Instance.singleton;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.engine.opts.Options;
import com.prayer.facade.metaserver.h2.H2Messages;
import com.prayer.facade.metaserver.h2.H2Server;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.fantasm.metaserver.h2.AbstractH2Server;
import com.prayer.metaserver.h2.callback.CallbackClosurer;
import com.prayer.metaserver.h2.util.RemoteRefers;
import com.prayer.metaserver.h2.util.UriResolver;
import com.prayer.model.web.options.JsonOptions;

import io.vertx.core.json.JsonArray;
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
    // ~ Instance Fields =====================================
    /** 序列化 **/
    private transient List<Server> database = new ArrayList<>();
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
    public static H2Server create(@NotNull @InstanceOfAny(JsonOptions.class) final Options options) {
        if (null == INSTANCE) {
            INSTANCE = new SingleServer(options);
        }
        return INSTANCE;
    }

    // ~ Constructors ========================================
    /** **/
    private SingleServer(final Options options) {
        this.options = options;
        this.database = this.initDatabase(prepareNode(this.options));
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
        /** 数据库列表 **/
        boolean status = booter.startDatabase(this.database);
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
        RemoteRefers.registry(H2Messages.RMI.OPTS_H2, this.options.readOpts());

        /** 5.开启轮询线程，监控到database停止过后就将主线程停止 **/
        new Thread(new CallbackClosurer(this.database, this::exit)).start();

        info(LOGGER, MessageFormat.format(Database.JDBC_URI, UriResolver.resolveJdbc(options)));
        return status;
    }

    /** **/
    @Override
    public boolean stop() throws AbstractException {
        /** 1.获取Web Console **/
        final JsonObject data = RemoteRefers.lookup(H2Messages.RMI.OPTS_H2);
        /** 2.必须从远程读取Options，保证Server和Client使用同样激活的配置 **/
        final Options remoteOpts = new JsonOptions(data);
        /** 3.停止H2 **/
        return stopper.stopDatabase(prepareNode(remoteOpts));
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    /**
     * 准备Single的单独数据
     * 
     * @param options
     * @return
     */
    private JsonArray prepareNode(final Options options) {
        final JsonObject opts = options.readOpts();
        /** 提取Server信息 **/
        final JsonObject server = new JsonObject();
        server.put("tcp.port", opts.getJsonObject("nodes").getInteger("tcp.port"));
        server.put("tcp.allow.others", opts.getJsonObject("extension").getBoolean("tcp.allow.others"));
        server.put("baseDir", opts.getJsonObject("server").getString("database"));
        server.put("host", opts.getJsonObject("nodes").getString("host"));
        /** 提取Array **/
        final JsonArray optsArr = new JsonArray();
        optsArr.add(server);
        return optsArr;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
