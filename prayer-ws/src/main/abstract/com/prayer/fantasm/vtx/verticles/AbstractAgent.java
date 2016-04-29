package com.prayer.fantasm.vtx.verticles;

import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.singleton;

import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;

import com.prayer.facade.engine.opts.Intaker;
import com.prayer.facade.resource.Inceptor;
import com.prayer.facade.resource.Point;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.resource.InceptBus;
import com.prayer.vertx.opts.ServerOptsIntaker;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import net.sf.oval.guard.Guarded;

/**
 * 抽象标准Verticle，Standard类型，包含了Server引用
 * 
 * @author Lang
 *
 */
@Guarded
public abstract class AbstractAgent extends AbstractVerticle {
    // ~ Static Fields =======================================
    /** 读取器 **/
    protected static final Inceptor INCEPTOR = InceptBus.build(Point.Server.class);
    /** **/
    private static final Intaker<ConcurrentMap<Integer, HttpServerOptions>> INTAKER = singleton(
            ServerOptsIntaker.class);
    // ~ Instance Fields =====================================
    /** 对象中的Server实例 **/
    private transient final HttpServer server;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** 根据端口初始化端口 **/
    public AbstractAgent(final int port) {
        HttpServerOptions options;
        try {
            options = INTAKER.ingest().get(port);
        } catch (AbstractException ex) {
            peError(getLogger(), ex);
            /** 异常情况使用默认配置 **/
            options = new HttpServerOptions();
            options.setPort(port);
        }
        this.server = vertx.createHttpServer(options);
    }

    // ~ Abstract Methods ====================================
    /** **/
    public abstract Logger getLogger();

    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** 读取当前Server的引用 **/
    protected HttpServer server() {
        return this.server;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
