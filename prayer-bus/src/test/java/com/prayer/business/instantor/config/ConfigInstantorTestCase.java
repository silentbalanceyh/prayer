package com.prayer.business.instantor.config;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.business.AbstractInstantor;
import com.prayer.facade.business.instantor.configuration.ConfigInstantor;
import com.prayer.facade.model.entity.Entity;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.meta.vertx.PERoute;
import com.prayer.model.meta.vertx.PEUri;
import com.prayer.model.meta.vertx.PEVerticle;

import io.vertx.core.http.HttpMethod;

/**
 * 
 * @author Lang
 *
 */
public class ConfigInstantorTestCase extends AbstractInstantor {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigInstantorTestCase.class);
    // ~ Instance Fields =====================================

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    // ~ Methods =============================================
    /** **/
    @Test
    public void testUris() throws AbstractException {
        /** 1.准备数据 **/
        final List<Entity> entities = this.preparedListData("instantor1-config-uri.json", PEUri.class);
        /** 2.调用Config接口读取数据 **/
        final ConfigInstantor instantor = this.getConfigItor();
        /** 3.调用uris接口 **/
        final ConcurrentMap<HttpMethod, PEUri> uris = instantor.uris("/api/sec/role");
        assertEquals(3, uris.size());
        /** 4.删除数据 **/
        this.purgeListData(entities, PEUri.class);
    }

    /** **/
    @Test
    public void testRoutes() throws AbstractException {
        /** 1.准备数据 **/
        final List<Entity> entities = this.preparedListData("instantor1-config-route.json", PERoute.class);
        /** 2.调用Config接口读取数据 **/
        final ConfigInstantor instantor = this.getConfigItor();
        /** 3.调用routes接口 **/
        final List<PERoute> routes = instantor.routes("/api");
        assertEquals(14, routes.size());
        /** 4.删除数据 **/
        this.purgeListData(entities, PERoute.class);
    }

    /** **/
    @Test
    public void testVerticles() throws AbstractException {
        /** 1.准备数据 **/
        final List<Entity> entities = this.preparedListData("instantor1-config-verticle.json", PEVerticle.class);
        /** 2.调用Config接口读取数据 **/
        final ConfigInstantor instantor = this.getConfigItor();
        /** 3.调用verticles接口 **/
        final List<PEVerticle> verticles = instantor.verticles("__DEFAULT__");
        final List<PEVerticle> verticles2 = instantor.verticles("__DEFAULT2__");
        assertEquals(7, verticles.size());
        assertEquals(2, verticles2.size());
        /** 4.删除数据 **/
        this.purgeListData(entities, PEVerticle.class);
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
