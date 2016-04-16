package com.prayer.business;

import static com.prayer.util.reflection.Instance.singleton;

import com.prayer.business.configuration.impl.ConfigBllor;
import com.prayer.business.deployment.impl.DeployBllor;
import com.prayer.facade.business.instantor.configuration.ConfigInstantor;
import com.prayer.facade.business.instantor.deployment.DeployInstantor;

/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractInstantor extends AbstractBusiness {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public String getFolder() {
        return "business/instantor/";
    }

    // ~ Methods =============================================
    /**
     * Direct：Instantor - Bllor
     * 
     * @return
     */
    protected DeployInstantor getDeployItor() {
        return singleton(DeployBllor.class);
    }

    /**
     * Direct：Instantor - Bllor
     * 
     * @return
     */
    protected ConfigInstantor getConfigItor() {
        return singleton(ConfigBllor.class);
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
