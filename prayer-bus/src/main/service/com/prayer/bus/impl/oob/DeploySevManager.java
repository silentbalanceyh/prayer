package com.prayer.bus.impl.oob;

import static com.prayer.util.reflection.Instance.singleton;

import com.prayer.bus.impl.deploy.AddressDPSevImpl;
import com.prayer.bus.impl.deploy.RouteDPSevImpl;
import com.prayer.bus.impl.deploy.RuleDPSevImpl;
import com.prayer.bus.impl.deploy.ScriptDPSevImpl;
import com.prayer.bus.impl.deploy.UriDPSevImpl;
import com.prayer.bus.impl.deploy.VerticleDPSevImpl;
import com.prayer.bus.impl.schema.SchemaSevImpl;
import com.prayer.facade.bus.SchemaService;
import com.prayer.facade.bus.deploy.AddressDPService;
import com.prayer.facade.bus.deploy.RouteDPService;
import com.prayer.facade.bus.deploy.RuleDPService;
import com.prayer.facade.bus.deploy.ScriptDPService;
import com.prayer.facade.bus.deploy.UriDPService;
import com.prayer.facade.bus.deploy.VerticleDPService;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
class DeploySevManager {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 访问H2的EVX_VERTICLE接口 **/
    @NotNull
    private transient final VerticleDPService verticleService;
    /** 访问H2的EVX_ROUTE接口 **/
    @NotNull
    private transient final RouteDPService routeService;
    /** 访问H2的EVX_URI接口 **/
    @NotNull
    private transient final UriDPService uriService;
    /** 访问H2的EVX_RULE接口 **/
    @NotNull
    private transient final RuleDPService ruleService;
    /** **/
    @NotNull
    private transient final SchemaService schemaService;
    /** **/
    @NotNull
    private transient final AddressDPService addressService;
    /** **/
    @NotNull
    private transient final ScriptDPService scriptService;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public DeploySevManager() {
        this.verticleService = singleton(VerticleDPSevImpl.class);
        this.routeService = singleton(RouteDPSevImpl.class);
        this.uriService = singleton(UriDPSevImpl.class);
        this.ruleService = singleton(RuleDPSevImpl.class);
        this.addressService = singleton(AddressDPSevImpl.class);
        this.scriptService = singleton(ScriptDPSevImpl.class);
        this.schemaService = singleton(SchemaSevImpl.class);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    /**
     * @return the verticleService
     */
    public VerticleDPService getVerticleService() {
        return verticleService;
    }

    /**
     * @return the routeService
     */
    public RouteDPService getRouteService() {
        return routeService;
    }

    /**
     * @return the uriService
     */
    public UriDPService getUriService() {
        return uriService;
    }

    /**
     * @return the validatorService
     */
    public RuleDPService getRuleService() {
        return ruleService;
    }

    /**
     * @return the schemaService
     */
    public SchemaService getSchemaService() {
        return schemaService;
    }

    /**
     * 
     * @return
     */
    public AddressDPService getAddressService() {
        return addressService;
    }

    /**
     * 
     * @return
     */
    public ScriptDPService getScriptService() {
        return scriptService;
    }
    // ~ hashCode,equals,toString ============================
}
