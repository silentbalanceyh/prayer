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
import com.prayer.model.meta.vertx.PEVerticle;

/**
 * 
 * @author Lang
 *
 */
public class ConfigAcquirerTestCase extends AbstractInstantor {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigAcquirerTestCase.class);

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
    public void testVerticles() throws AbstractException {
        /** 1.准备数据 **/
        final List<Entity> entities = this.preparedListData("instantor2-config-verticle.json", PEVerticle.class);
        /** 2.调用Config接口读取数据 **/
        final ConfigInstantor instantor = this.getConfigItor();
        /** 3.调用verticles接口 **/
        final ConcurrentMap<String, PEVerticle> verticles = instantor.verticles();
        assertEquals(9, verticles.size());
        /** 4.删除数据 **/
        this.purgeListData(entities, PEVerticle.class);
    }

    /** **/
    @Test
    public void testRoutes() throws AbstractException {
        /** 1.准备数据 **/
        final List<Entity> entities = this.preparedListData("instantor2-config-route.json", PERoute.class);
        /** 2.调用Config接口读取数据 **/
        final ConfigInstantor instantor = this.getConfigItor();
        /** 3.调用routes接口 **/
        final ConcurrentMap<String, List<PERoute>> routes = instantor.routes();
        assertEquals(2, routes.size());
        assertEquals(11, routes.get("/api").size());
        assertEquals(3, routes.get("/mobileapi").size());
        /** 4.删除数据 **/
        this.purgeListData(entities, PERoute.class);
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
