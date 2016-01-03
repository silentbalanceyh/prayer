package com.prayer.demo.starter;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.impl.VertxFactoryImpl;
import io.vertx.core.spi.VertxFactory;

/**
 * 
 * @author Lang
 * 
 */
public class SingleStarter {
    // ~ Static Fields =======================================

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 
     * @param args
     */
    public static void main(final String... args) {
        final VertxOptions options = OptionsReader.readOpts("VXWEB-DEMO");
        final VertxFactory factory = new VertxFactoryImpl();
        final Vertx vertx = factory.vertx(options);
        final DeploymentOptions verticleOpts = OptionsReader.readOpts();
        vertx.deployVerticle("com.prayer.demo.web.RouterVerticle", verticleOpts);
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
