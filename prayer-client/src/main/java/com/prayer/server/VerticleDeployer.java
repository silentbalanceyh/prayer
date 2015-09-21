package com.prayer.server;

import com.prayer.client.ClientVerticle;

import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class VerticleDeployer {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /**
     * 
     */
    @NotNull
    private transient final Vertx vertxRef;
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param vertxRef
     */
    @PostValidateThis
    public VerticleDeployer(@NotNull final Vertx vertxRef){
        this.vertxRef = vertxRef;
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    public void deployVerticles(){
        final Verticle verticle = new ClientVerticle();
        this.vertxRef.deployVerticle(verticle);
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
