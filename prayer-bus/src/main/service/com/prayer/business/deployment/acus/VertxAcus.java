package com.prayer.business.deployment.acus;

import static com.prayer.util.debug.Log.info;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.prayer.facade.business.instantor.deployment.acus.DeployAcus;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.constant.Symbol;
import com.prayer.facade.resource.Inceptor;
import com.prayer.facade.resource.Point;
import com.prayer.fantasm.business.deployment.acus.AbstractEntityAcus;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.meta.vertx.PEAddress;
import com.prayer.model.meta.vertx.PERoute;
import com.prayer.model.meta.vertx.PEVerticle;
import com.prayer.resource.InceptBus;
import com.prayer.resource.Injections;
import com.prayer.util.io.IOKit;
import com.prayer.util.io.JacksonKit;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class VertxAcus extends AbstractEntityAcus implements DeployAcus {
    // ~ Static Fields =======================================

    /** 日志记录器 **/
    private static final Logger LOGGER = LoggerFactory.getLogger(VertxAcus.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public boolean deploy(@NotNull @NotBlank @NotEmpty final String folder) throws AbstractException {
        /** 1.发布Verticle **/
        this.deployVerticle(folder);
        /** 2.发布Route **/
        this.deployRoute(folder);
        /** 3.发布Address **/
        this.deployAddress(folder);
        return true;
    }

    /** **/
    @Override
    public boolean purge() throws AbstractException {
        info(LOGGER, "[PG] 2.<Start>.Vertx's ( Verticles ) purging start...");
        accessor(PEVerticle.class).purge();
        info(LOGGER, "[PG] 2.<End>.Vertx's ( Verticles ) data have been purged successfully.");
        info(LOGGER, "[PG] 3.<Start>.Vertx's ( Routes ) purging start...");
        accessor(PERoute.class).purge();
        info(LOGGER, "[PG] 3.<End>.Vertx's ( Routes ) data have been purged successfully.");
        info(LOGGER, "[PG] 4.<Start>.Vertx's ( Addresses ) purging start...");
        accessor(PEAddress.class).purge();
        info(LOGGER, "[PG] 4.<End>.Vertx's ( Addresses ) data have been purged successfully.");
        return true;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private void deployAddress(final String folder) throws AbstractException {
        final TypeReference<List<PEAddress>> typeRef = new TypeReference<List<PEAddress>>() {
        };
        info(LOGGER, "[DP] 4.<Start>.Vertx's ( Addresses ) deployment start...");
        final String target = folder + "/vertx/address.json";
        final List<PEAddress> addresses = JacksonKit.fromFile(typeRef, target);
        if (!addresses.isEmpty()) {
            accessor(PEAddress.class).insert(addresses.toArray(new PEAddress[] {}));
        }
        info(LOGGER, "[DP] 4.<End>.( EVX_ADDRESS ) Vertx's ( Addresses ) have been deployed successfully. Size = "
                + addresses.size() + ", File = " + target);
    }

    /** URI Hooker专用 **/
    private void injectUriHooker(final List<PERoute> routes) throws AbstractException {
        final List<PERoute> hookerLst = new ArrayList<>();
        for (final PERoute route : routes) {
            /** 1.钩子挂载点 **/
            if (Constants.ZERO <= route.getPath().indexOf(Symbol.COLON)) {
                /** 2.拷贝数据 **/
                final JsonObject data = route.toJson();
                final PERoute hooker = new PERoute(data);
                /** 3.特殊钩子数据 **/
                hooker.setRequestHandler(Injections.Web.URI_HOOKER);
                final Inceptor inceptor = InceptBus.build(Point.Web.class, Point.Web.HANDLER_ORDERS);
                hooker.setOrder(inceptor.getInt(Point.Web.Orders.Api.URI));
                /** 4.Hooker需要打开Mimes限制 **/
                final List<String> mimes = new ArrayList<>();
                mimes.add("*/*");
                hooker.setConsumerMimes(mimes);
                hooker.setProducerMimes(mimes);
                /** 4.设置null的ID **/
                hooker.id(null);
                hookerLst.add(hooker);
            }
        }
        if (!hookerLst.isEmpty()) {
            accessor(PERoute.class).insert(hookerLst.toArray(new PERoute[] {}));
        }
    }

    private void deployRoute(final String folder) throws AbstractException {
        final TypeReference<List<PERoute>> typeRef = new TypeReference<List<PERoute>>() {
        };
        info(LOGGER, "[DP] 3.<Start>.Vertx's ( Routes ) deployment start...");
        final String targetFolder = folder + "/vertx/route/";
        final List<String> files = IOKit.listFiles(targetFolder);
        for (final String file : files) {
            final String target = targetFolder + file;
            final List<PERoute> routes = JacksonKit.fromFile(typeRef, target);
            if (!routes.isEmpty()) {
                accessor(PERoute.class).insert(routes.toArray(new PERoute[] {}));
                this.injectUriHooker(routes);
            }
            info(LOGGER, "[DP] 3. - Vertx's ( Routes ) of " + target + " have been deployed successfully. Size = "
                    + routes.size());
        }
        info(LOGGER, "[DP] 3.<End>.( EVX_ROUTE ) Vertx's ( Routes ) have been deployed successfully. Folder = "
                + targetFolder);
    }

    /** Verticle的Deploy **/
    private void deployVerticle(final String folder) throws AbstractException {
        final TypeReference<List<PEVerticle>> typeRef = new TypeReference<List<PEVerticle>>() {
        };
        info(LOGGER, "[DP] 2.<Start>.Vertx's ( Verticles ) deployment start...");
        final String target = folder + "/vertx/verticle.json";
        final List<PEVerticle> verticles = JacksonKit.fromFile(typeRef, target);
        if (!verticles.isEmpty()) {
            accessor(PEVerticle.class).insert(verticles.toArray(new PEVerticle[] {}));
        }
        info(LOGGER, "[DP] 2.<End>.( EVX_VERTICLE ) Vertx's ( Verticles ) have been deployed successfully. Size = "
                + verticles.size() + ", File = " + target);
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
