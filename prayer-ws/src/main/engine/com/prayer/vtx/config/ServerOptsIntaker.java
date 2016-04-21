package com.prayer.vtx.config;

import static com.prayer.util.Planar.flat;
import static com.prayer.util.reflection.Instance.singleton;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.facade.engine.Intaker;
import com.prayer.facade.engine.Warranter;
import com.prayer.facade.resource.Inceptor;
import com.prayer.facade.resource.Point;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.fantasm.exception.AbstractLauncherException;
import com.prayer.resource.InceptBus;
import com.prayer.util.warranter.NumericWarranter;
import com.prayer.util.warranter.ValueWarranter;

import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.net.NetServerOptions;
import io.vertx.core.net.TCPSSLOptions;

/**
 * Server, HttpServer, SSL, Network配置项
 * 单件模式
 * @author Lang
 *
 */
public class ServerOptsIntaker implements Intaker<ConcurrentMap<Integer, HttpServerOptions>> {
    // ~ Static Fields =======================================
    /** 服务选项配置 **/
    private static ConcurrentMap<Integer, HttpServerOptions> SERVERS;
    /** 读取器 **/
    private static final Inceptor INCEPTOR = InceptBus.build(Point.Server.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public ConcurrentMap<Integer, HttpServerOptions> ingest() throws AbstractException {
        /** 1.验证必须参数 **/
        warrantRequired();
        /** 2.验证数字 **/
        warrantNumeric();
        /** 3.生成HttpServerOptions **/
        return this.buildOpts();
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private ConcurrentMap<Integer, HttpServerOptions> buildOpts() {
        if (null == SERVERS) {
            SERVERS = new ConcurrentHashMap<>();
            final String[] keys = this.buildKeys();
            /** 1.迭代 **/
            for (final String key : keys) {
                final Integer port = INCEPTOR.getInt(key);
                /** 2.读取其他配置 **/
                if (!SERVERS.containsKey(port)) {
                    /** 添加配置 **/
                    SERVERS.put(port, this.buildOpt(port));
                }
            }
        }
        return SERVERS;
    }

    private HttpServerOptions buildOpt(final int port) {
        final HttpServerOptions opt = new HttpServerOptions();
        opt.setPort(port);
        // ~ Server Options ---------------------------------------
        this.injectServerOpts(opt);
        // ~ Http Server Options ----------------------------------
        this.injectHttpOpts(opt);
        // ~ Network Options --------------------------------------
        this.injectNetOpts(opt);
        // ~ SSL Options ------------------------------------------
        this.injectSslOpts(opt);
        return opt;
    }

    private void injectServerOpts(final HttpServerOptions opt) {
        /** Host **/
        opt.setHost(INCEPTOR.getString(Point.Server.HOST));
        /** Accept Backlog **/
        opt.setAcceptBacklog(
                flat(INCEPTOR.getInt(Point.Server.ACCEPT_BACKLOG), NetServerOptions.DEFAULT_ACCEPT_BACKLOG));
    }

    private void injectSslOpts(final HttpServerOptions opt) {
        /** Idle Time out **/
        opt.setIdleTimeout(flat(INCEPTOR.getInt(Point.Server.TcpSSL.IDLE_TIMEOUT), TCPSSLOptions.DEFAULT_IDLE_TIMEOUT));
        /** So Linger **/
        opt.setSoLinger(INCEPTOR.getInt(Point.Server.TcpSSL.SO_LINGER));
        /** Tcp No Delay **/
        opt.setTcpNoDelay(INCEPTOR.getBoolean(Point.Server.TcpSSL.TCP_NO_DELAY));
        /** Tcp Keep Alive **/
        opt.setTcpKeepAlive(INCEPTOR.getBoolean(Point.Server.TcpSSL.TCP_KEEP_ALIVE));
        /** Use Pooled Buffers **/
        opt.setUsePooledBuffers(INCEPTOR.getBoolean(Point.Server.TcpSSL.USE_POOLED_BUF));
    }

    private void injectNetOpts(final HttpServerOptions opt) {
        /** Receive Buffer Size **/
        opt.setReceiveBufferSize(INCEPTOR.getInt(Point.Server.Network.REC_BUFFER_SIZE));
        /** Send Buffer Size **/
        opt.setSendBufferSize(INCEPTOR.getInt(Point.Server.Network.SEND_BUFFER_SIZE));
        /** Reuse Address **/
        opt.setReuseAddress(INCEPTOR.getBoolean(Point.Server.Network.REUSE_ADDR));
        /** Traffic Class **/
        opt.setTrafficClass(INCEPTOR.getInt(Point.Server.Network.TRAFFIC_CLASS));
    }

    private void injectHttpOpts(final HttpServerOptions opt) {
        /** Compression Support **/
        opt.setCompressionSupported(INCEPTOR.getBoolean(Point.Server.Http.COMPRESSION_SUPPORT));
        /** Max Chunk Size **/
        opt.setMaxChunkSize(
                flat(INCEPTOR.getInt(Point.Server.Http.MAX_CHUNK_SIZE), HttpServerOptions.DEFAULT_MAX_CHUNK_SIZE));
        /** Max Header Size **/
        opt.setMaxHeaderSize(
                flat(INCEPTOR.getInt(Point.Server.Http.MAX_HEADER_SIZE), HttpServerOptions.DEFAULT_MAX_HEADER_SIZE));
        /** Initial Line Length **/
        opt.setMaxInitialLineLength(flat(INCEPTOR.getInt(Point.Server.Http.MAX_INITIAL_LINE_LENGTH),
                HttpServerOptions.DEFAULT_MAX_INITIAL_LINE_LENGTH));
        /** Web Socket Frame Size **/
        opt.setMaxWebsocketFrameSize(flat(INCEPTOR.getInt(Point.Server.Http.MAX_WEBSOCKET_FSIZE),
                HttpServerOptions.DEFAULT_MAX_WEBSOCKET_FRAME_SIZE));
    }
    
    private void warrantRequired() throws AbstractLauncherException {
        final Warranter vWter = singleton(ValueWarranter.class);
        final String[] params = new String[] { "server.port.api", "server.port.web", "server.host" };
        vWter.warrant(INCEPTOR, params);
    }

    private void warrantNumeric() throws AbstractLauncherException {
        final Warranter vWter = singleton(NumericWarranter.class);
        vWter.warrant(INCEPTOR, buildKeys());
    }

    private String[] buildKeys() {
        return new String[] { "server.port.api", "server.port.web" };
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
