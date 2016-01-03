package com.prayer.demo.starter;

import com.hazelcast.config.Config;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.impl.VertxFactoryImpl;
import io.vertx.core.spi.VertxFactory;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;

public class ClusterStarter {
    public static void main(final String... args) {
        final VertxOptions options = OptionsReader.readOpts("VXWEB-DEMO");
        final VertxFactory factory = new VertxFactoryImpl();
        final ClusterManager mgr = new HazelcastClusterManager(new Config());
        options.setClusterManager(mgr);
        factory.clusteredVertx(options, res -> {
            if (res.succeeded()) {
                final Vertx vertx = res.result();
                final DeploymentOptions verticleOpts = OptionsReader.readOpts();
                vertx.deployVerticle("com.prayer.demo.verticle.RouterVerticle", verticleOpts);
            }
        });
    }
}
