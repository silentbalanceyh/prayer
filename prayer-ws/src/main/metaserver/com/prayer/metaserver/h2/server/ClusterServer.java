package com.prayer.metaserver.h2.server;

import static com.prayer.util.debug.Log.info;
import static com.prayer.util.debug.Log.jvmError;
import static com.prayer.util.reflection.Instance.singleton;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.h2.tools.CreateCluster;
import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.engine.cv.RmiKeys;
import com.prayer.facade.engine.opts.Options;
import com.prayer.facade.metaserver.h2.H2Messages;
import com.prayer.facade.metaserver.h2.H2Server;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.fantasm.metaserver.h2.AbstractH2Server;
import com.prayer.metaserver.h2.callback.MetaServerClosurer;
import com.prayer.metaserver.h2.util.ParamsResolver;
import com.prayer.metaserver.h2.util.RemoteRefers;
import com.prayer.metaserver.h2.util.UriResolver;
import com.prayer.model.web.options.JsonOptions;
import com.prayer.util.Converter;

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
public class ClusterServer extends AbstractH2Server {
    // ~ Static Fields =======================================
    /** **/
    private static H2Server INSTANCE = null;
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(ClusterServer.class);
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
            INSTANCE = new ClusterServer(options);
        }
        return INSTANCE;
    }

    // ~ Constructors ========================================
    /** **/
    private ClusterServer(final Options options) {
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
        /** 4.创建Cluster **/
        status = this.createCluster(options);
        if (!status) {
            return false;
        }

        /** 5.读取JsonObject **/
        RemoteRefers.registry(RmiKeys.META_SERVER_OPTS, this.options.readOpts());
        /** 6.开启轮询线程，监控到database停止过后就将主线程停止 **/
        new Thread(new MetaServerClosurer(this.database, this::exit)).start();

        info(LOGGER, MessageFormat.format(Database.JDBC_URI, UriResolver.resolveJdbc(options)));
        return status;
    }

    /** **/
    @Override
    public boolean stop() throws AbstractException {
        /** 1.获取Web Console **/
        final JsonObject data = RemoteRefers.lookup(RmiKeys.META_SERVER_OPTS);
        /** 2.远程读取 **/
        final Options remoteOpts = new JsonOptions(data);
        /** 3.停止H2 **/
        return stopper.stopDatabase(prepareNode(remoteOpts));
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private boolean createCluster(final Options options) {
        boolean ret = false;
        try {
            final CreateCluster cluster = new CreateCluster();
            final ConcurrentMap<String, String> params = this.prepareParams(options);
            cluster.execute(params.get("-urlSource"), params.get("-urlTarget"), params.get("-user"),
                    params.get("-password"), params.get("-serverList"));
            info(LOGGER, Cluster.INFO_CLUSTER);
            ret = true;
        } catch (SQLException ex) {
            jvmError(LOGGER, ex);
            ex.printStackTrace();
            info(LOGGER, MessageFormat.format(Cluster.ERROR_CLUSTER, ex.getMessage()));
        }
        return ret;
    }

    private ConcurrentMap<String, String> prepareParams(final Options options) {
        /** 1.获取数据库用户名和密码 **/
        final ConcurrentMap<String, String> params = new ConcurrentHashMap<>();
        final JsonObject opts = options.readOpts();
        {
            params.put("-user", opts.getJsonObject("server").getString("username"));
            params.put("-password", opts.getJsonObject("server").getString("password"));
        }
        /** 2.Source和Target **/
        {
            params.put("-urlSource", this.prepareParams(opts, "source"));
            info(LOGGER, MessageFormat.format(Cluster.INFO_SRCS,
                    opts.getJsonObject("cluster").getJsonArray("source").encode()));
            params.put("-urlTarget", this.prepareParams(opts, "target"));
            info(LOGGER, MessageFormat.format(Cluster.INFO_DEST,
                    opts.getJsonObject("cluster").getJsonArray("target").encode()));
        }
        /** 3.设置Server参数 **/
        {
            final JsonArray sources = opts.getJsonObject("nodes").getJsonArray("source");
            final JsonArray targets = opts.getJsonObject("nodes").getJsonArray("target");
            final JsonArray servers = Converter.merge(sources, targets);
            final String host = opts.getJsonObject("cluster").getString("host");
            final String sevList = ParamsResolver.resolve(servers, host);
            params.put("-serverList", sevList);
        }
        return params;
    }

    private String prepareParams(final JsonObject data, final String key) {
        final boolean encryption = data.getJsonObject("extension").getBoolean("encryption");
        final String host = data.getJsonObject("cluster").getString("host");
        final String database = data.getJsonObject("server").getString("database");
        /** 1.Host信息提取 **/
        final JsonArray nodes = data.getJsonObject("nodes").getJsonArray(key);
        final String hostParts = ParamsResolver.resolve(nodes, host);
        /** 2.返回参数 **/
        final StringBuilder uri = new StringBuilder();
        uri.append(MessageFormat.format(H2Messages.URI_CLUSTER, hostParts, database));
        if (encryption) {
            uri.append(";PASSWORD_HASH=TRUE");
        }
        return uri.toString();
    }

    private JsonArray prepareNode(final Options options) {
        final JsonObject opts = options.readOpts();
        /** 提取Server中的tcp.allow.others **/
        final boolean allowOthers = opts.getJsonObject("extension").getBoolean("tcp.allow.others");
        final String host = opts.getJsonObject("cluster").getString("host");
        final JsonArray optsArr = new JsonArray();
        /** 构造Source **/
        final JsonArray sources = opts.getJsonObject("nodes").getJsonArray("source");
        /** 构造Target **/
        final JsonArray targets = opts.getJsonObject("nodes").getJsonArray("target");
        final JsonArray servers = Converter.merge(sources, targets);
        int length = servers.size();
        for (int idx = 0; idx < length; idx++) {
            final JsonObject item = servers.getJsonObject(idx);
            item.put("tcp.allow.others", allowOthers);
            if (!item.containsKey("host")) {
                item.put("host", host);
            }
            optsArr.add(item);
        }
        return optsArr;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
