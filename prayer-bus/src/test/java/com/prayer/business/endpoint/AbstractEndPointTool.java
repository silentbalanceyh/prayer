package com.prayer.business.endpoint;

import static com.prayer.util.reflection.Instance.singleton;

import com.prayer.business.deployment.impl.SchemaBllor;
import com.prayer.facade.business.endpoint.DataStubor;
import com.prayer.facade.business.endpoint.MetaStubor;
import com.prayer.facade.business.instantor.deployment.SchemaInstantor;

/**
 * 
 * @author Lang
 *
 */
public class AbstractEndPointTool {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** Data Record的远程接口 **/
    private transient DataStubor ds = singleton(DataMessager.class);
    /** Meta Record的远程接口 **/
    private transient MetaStubor ms = singleton(MetaMessager.class);
    /** Schema的Instantor层接口 **/
    private transient SchemaInstantor instantor = singleton(SchemaBllor.class);
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** Data Record **/
    protected DataStubor getDataStubor() {
        return this.ds;
    }

    /** Meta Record **/
    protected MetaStubor getMetaStubor() {
        return this.ms;
    }
    
    /** Schema Bllor用于导入表 **/
    protected void preparedData(final String file){
        
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
