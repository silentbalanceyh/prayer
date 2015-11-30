package com.prayer.vx.configurator;

import static com.prayer.assistant.WebLogger.error;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.util.PropertyKit;
import com.prayer.util.cv.Resources;
import com.prayer.util.cv.Symbol;

import io.vertx.core.http.HttpServerOptions;
import jodd.util.StringUtil;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class ServerConfigurator { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerConfigurator.class);
    /** Server Config File **/
    private transient final PropertyKit LOADER = new PropertyKit(ServerConfigurator.class, Resources.SEV_CFG_FILE);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================

    /** 获取Http Server Options的选项信息 **/
    public HttpServerOptions getApiOptions() {
        return this.getOptions("server.api.port");
    }

    /** Web应用的选项信息 **/
    public HttpServerOptions getWebOptions() {
        return this.getOptions("server.web.port");
    }

    /** Api Remote 地址 **/
    public String getEndPoint() {
        final StringBuilder apiUrl = new StringBuilder();
        apiUrl.append("http://").append(LOADER.getString("server.host")).append(Symbol.COLON)
                .append(LOADER.getString("server.api.port")).append("/api");
        return apiUrl.toString();
    }

    // ~ Methods =============================================
    /**
     * 
     * @return
     * @throws SQLException
     */
    public ConcurrentMap<String, Server> getH2CDatabases() throws SQLException {
        final Set<String> clusters = new HashSet<>();
        clusters.addAll(Arrays.asList(LOADER.getArray("h2.database.cluster.source")));
        clusters.addAll(Arrays.asList(LOADER.getArray("h2.database.cluster.target")));
        final ConcurrentMap<String, Server> retMap = new ConcurrentHashMap<>();
        for (final String cluster : clusters) {
            retMap.put(cluster, this.getH2Database(cluster));
        }
        return retMap;
    }

    /**
     * 
     * @return
     */
    public boolean enabledH2Cluster() {
        boolean enabled = false;
        enabled = LOADER.getBoolean("h2.database.cluster.enabled");
        return enabled;
    }

    /**
     * 
     * @return
     */
    public String[] getClusterParams() throws SQLException {
        final List<String> params = new ArrayList<>();
        // 1.获取数据库名
        final String database = this.LOADER.getString("h2.database.cluster.database");
        // 2.获取数据库用户名和密码
        final String user = this.LOADER.getString("h2.database.cluster.user");
        final String password = this.LOADER.getString("h2.database.cluster.password");
        final String host = this.LOADER.getString("h2.database.cluster.host");
        {
            // 3.构造基本参数-user,-password
            params.add("-user");
            params.add(user);
            params.add("-password");
            params.add(password);
        }
        final ConcurrentMap<String, Server> servers = this.getH2CDatabases();
        {
            // 4.获取-urlSource
            final List<String> src = Arrays.asList(this.LOADER.getArray("h2.database.cluster.source"));
            final List<String> srcParams = new ArrayList<>();
            for (final String source : src) {
                srcParams.add(this.readH2Url(host, servers.get(source).getPort()));
            }
            params.add("-urlSource");
            final StringBuilder url = new StringBuilder();
            url.append("jdbc:h2:tcp://").append(StringUtil.join(srcParams.toArray(new String[] {}), Symbol.COMMA))
                    .append("/META/").append(database);
            params.add(url.toString());
        }
        {
            // 5.获取-urlTarget
            final List<String> dst = Arrays.asList(this.LOADER.getArray("h2.database.cluster.target"));
            final List<String> dstParams = new ArrayList<>();
            for (final String target : dst) {
                dstParams.add(this.readH2Url(host, servers.get(target).getPort()));
            }
            params.add("-urlTarget");
            final StringBuilder url = new StringBuilder();
            url.append("jdbc:h2:tcp://").append(StringUtil.join(dstParams.toArray(new String[] {}), Symbol.COMMA))
                    .append("/META/").append(database);
            params.add(url.toString());
        }
        {
            // 6.设置-serverList
            final List<String> serverParams = new ArrayList<>();
            for (final String server : servers.keySet()) {
                serverParams.add(this.readH2Url(host, servers.get(server).getPort()));
            }
            params.add("-serverList");
            params.add(StringUtil.join(serverParams.toArray(new String[] {}), Symbol.COMMA));
        }
        return params.toArray(new String[] {});
    }

    // ~ H2 Standalong =======================================
    /** H2 Database 创建 **/
    public Server getH2Database() throws SQLException {
        Server server = null;
        if (null == this.LOADER) {
            error(LOGGER, "H2 Database property file has not been initialized successfully !");
        } else {
            final int port = this.LOADER.getInt("h2.database.tcp.port");
            final boolean allowOthers = this.LOADER.getBoolean("h2.database.tcp.allow.others");
            final List<String> params = new ArrayList<>();
            params.add("-tcpPort");
            params.add(String.valueOf(port));
            if (allowOthers) {
                params.add("-tcpAllowOthers");
            }
            server = Server.createTcpServer(params.toArray(new String[] {}));
        }
        return server;
    }

    /** H2 Web Console **/
    public Server getH2WebConsole() throws SQLException {
        Server server = null;
        if (null == this.LOADER) {
            error(LOGGER, "H2 Web Console property file has not been initialized successfully !");
        } else {
            final int port = this.LOADER.getInt("h2.database.web.port");
            final boolean allowOthers = this.LOADER.getBoolean("h2.database.web.allow.others");
            final List<String> params = new ArrayList<>();
            params.add("-webPort");
            params.add(String.valueOf(port));
            if (allowOthers) {
                params.add("-webAllowOthers");
            }
            server = Server.createWebServer(params.toArray(new String[] {}));
        }
        return server;
    }

    // ~ Private Methods =====================================

    private String readH2Url(final String host, final int port) {
        final StringBuilder url = new StringBuilder();
        url.append(host).append(":").append(port);
        return url.toString();
    }

    private Server getH2Database(final String name) throws SQLException {
        Server server = null;
        if (null == this.LOADER) {
            error(LOGGER, "H2 Database (Cluster Mode) property file has not been initialized successfully !");
        } else {
            final int port = this.LOADER.getInt("h2.database." + name + ".tcp.port");
            final boolean allowOthers = this.LOADER.getBoolean("h2.database.tcp.allow.others");
            final String baseDir = this.LOADER.getString("h2.database." + name + ".baseDir");
            final List<String> params = new ArrayList<>();
            params.add("-tcpPort");
            params.add(String.valueOf(port));
            // Cluster Base Dir用法
            params.add("-baseDir");
            params.add("~/H2/" + baseDir);
            if (allowOthers) {
                params.add("-tcpAllowOthers");
            }
            server = Server.createTcpServer(params.toArray(new String[] {}));
        }
        return server;
    }

    private HttpServerOptions getOptions(final String portKey) {
        final HttpServerOptions options = new HttpServerOptions();
        if (null == this.LOADER) {
            error(LOGGER, "Server property loader has not been initialized successfully !");
        } else {
            // Basic Options
            options.setPort(this.LOADER.getInt(portKey));
            options.setHost(this.LOADER.getString("server.host"));
            // Whether support compression
            options.setCompressionSupported(this.LOADER.getBoolean("server.compression.support"));
            options.setAcceptBacklog(this.LOADER.getInt("server.accept.backlog"));
        }
        return options;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
