package com.prayer.configuration;

import static com.prayer.util.reflection.Instance.singleton;

import com.prayer.business.deployment.impl.DeployBllor;
import com.prayer.constant.Resources;
import com.prayer.facade.business.deployment.DeployInstantor;
import com.prayer.model.business.ServiceResult;

/**
 * 
 * @author Lang
 *
 */
class EnvInitializer {
    // ~ Static Fields =======================================
    /** 默认的SQL文件名 **/
    private static final String INIT_SQL = "initialize";
    // ~ Instance Fields =====================================
    /** 发布用的Service **/
    private transient final DeployInstantor deployer = singleton(DeployBllor.class);

    /** 获取当前引用 **/
    public static EnvInitializer reference() {
        return singleton(EnvInitializer.class);
    }
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================

    public void prepareEnv() {
        // 1.删除原始数据，可能会失败
        this.deployer.purge();
        ServiceResult<Boolean> ret = this.deployer.initialize(INIT_SQL);
        if (ret.success()) {
            // 2.发布所有数据
            ret = this.deployer.manoeuvre(Resources.OOB_DATA_FOLDER);
        }
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
