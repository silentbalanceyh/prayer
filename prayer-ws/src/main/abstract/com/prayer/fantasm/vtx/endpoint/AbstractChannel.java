package com.prayer.fantasm.vtx.endpoint;

import static com.prayer.util.reflection.Instance.singleton;

import com.prayer.business.endpoint.DataMessager;
import com.prayer.business.endpoint.MetaMessager;
import com.prayer.facade.business.endpoint.DataStubor;
import com.prayer.facade.business.endpoint.MetaStubor;
import com.prayer.facade.engine.fun.Invoker;
import com.prayer.facade.vtx.endpoint.Channel;
import com.prayer.model.business.behavior.ActResponse;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractChannel implements Channel {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    private transient final DataStubor dataService = singleton(DataMessager.class);
    /** **/
    private transient final MetaStubor metaService = singleton(MetaMessager.class);
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    /** **/
    public abstract Invoker getAction();
    // ~ Override Methods ====================================
    /** **/
    @Override
    public ActResponse invoke(final JsonObject params){
        return getAction().invoke(params);
    }
    // ~ Methods =============================================
    /** **/
    protected DataStubor getDataKit(){
        return dataService;
    }
    /** **/
    protected MetaStubor getMetaKit(){
        return metaService;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
