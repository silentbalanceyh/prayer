package com.prayer.vertx.web.router;

import static com.prayer.util.Converter.fromStr;

import com.prayer.facade.resource.Point;
import com.prayer.facade.vtx.route.Fabricator;
import com.prayer.fantasm.vtx.route.AbstractFabricator;

import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.CorsHandler;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class CorsFabricator extends AbstractFabricator implements Fabricator {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void immitRouter(@NotNull final Router router) {
        router.route().order(orders().getInt(Point.Web.Orders.CORS)).handler(this.buildCors());
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    /**
     * 创建跨域信息，直接从配置文件中读取
     * @return
     */
    private CorsHandler buildCors() {
        final CorsHandler handler = CorsHandler.create(this.securitor().getString(Point.Security.Cors.ORIGIN));
        handler.allowCredentials(this.securitor().getBoolean(Point.Security.Cors.CREDENTIALS));
        /** Headers **/
        final String[] headers = this.securitor().getArray(Point.Security.Cors.HEADERS);
        for (final String header : headers) {
            handler.allowedHeader(header);
        }
        /** Methods **/
        final String[] methods = this.securitor().getArray(Point.Security.Cors.METHODS);
        for (final String method : methods) {
            handler.allowedMethod(fromStr(HttpMethod.class, method));
        }
        // TODO: Vertx-Web：可扩展Cors Handler
        return handler;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
