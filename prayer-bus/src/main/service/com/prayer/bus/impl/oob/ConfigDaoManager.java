package com.prayer.bus.impl.oob;

import com.prayer.facade.dao.metadata.AddressDao;
import com.prayer.facade.dao.metadata.RouteDao;
import com.prayer.facade.dao.metadata.RuleDao;
import com.prayer.facade.dao.metadata.ScriptDao;
import com.prayer.facade.dao.metadata.UriDao;
import com.prayer.facade.dao.metadata.VerticleDao;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
class ConfigDaoManager {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 访问H2的EVX_VERTICLE接口 **/
    @NotNull
    private transient VerticleDao verticleDao;
    /** 访问H2的EVX_ROUTE接口 **/
    @NotNull
    private transient RouteDao routeDao;
    /** 访问H2的EVX_URI接口 **/
    @NotNull
    private transient UriDao uriDao;
    /** 访问H2的EVX_PVRULE接口 **/
    @NotNull
    private transient RuleDao ruleDao;
    /** 访问H2的EVX_ADDRESS接口 **/
    @NotNull
    private transient AddressDao addressDao;
    /** **/
    @NotNull
    private transient ScriptDao scriptDao;
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public ConfigDaoManager() {
//        this.verticleDao = singleton(VerticleDaoImpl.class);
//        this.routeDao = singleton(RouteDaoImpl.class);
//        this.uriDao = singleton(UriDaoImpl.class);
//        this.ruleDao = singleton(RuleDaoImpl.class);
//        this.addressDao = singleton(AddressDaoImpl.class);
//        this.scriptDao = singleton(ScriptDaoImpl.class);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    /**
     * @return the verticleDao
     */
    public VerticleDao getVerticleDao() {
        return verticleDao;
    }
    /**
     * @return the routeDao
     */
    public RouteDao getRouteDao() {
        return routeDao;
    }
    /**
     * @return the uriDao
     */
    public UriDao getUriDao() {
        return uriDao;
    }
    /**
     * @return the ruleDao
     */
    public RuleDao getRuleDao() {
        return ruleDao;
    }
    /**
     * @return the addressDao
     */
    public AddressDao getAddressDao() {
        return addressDao;
    }
    /**
     * @return the scriptDao
     */
    public ScriptDao getScriptDao() {
        return scriptDao;
    }
    // ~ hashCode,equals,toString ============================
}
