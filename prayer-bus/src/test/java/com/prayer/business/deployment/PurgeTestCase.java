package com.prayer.business.deployment;

import static com.prayer.util.debug.Log.info;
import static com.prayer.util.reflection.Instance.singleton;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Resources;
import com.prayer.deployment.impl.DeployBllor;
import com.prayer.facade.deployment.DeployService;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.business.ServiceResult;

/**
 * 
 * @author Lang
 *
 */
public class PurgeTestCase {
    // ~ Static Fields =======================================
    /** 日志记录器 **/
    private static final Logger LOGGER = LoggerFactory.getLogger(PurgeTestCase.class);
    /** 默认的SQL文件名 **/
    private static final String INIT_SQL = "initialize";
    // ~ Instance Fields =====================================
    /** 发布用的Service **/
    private transient final DeployService service = singleton(DeployBllor.class);

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** **/
    @Test
    public void testPurge() throws AbstractException {
        ServiceResult<Boolean> ret = this.service.initialize(INIT_SQL);
        if (ret.success()) {
            info(LOGGER, "[T] Metadata ( Structure Part ) deployed successfully !");
            ret = this.service.manoeuvre(Resources.OOB_DATA_FOLDER);
            if (ret.success()) {
                this.service.purge();
                if (!ret.success()) {
                    throw ret.getServiceError();
                }
            } else {
                throw ret.getServiceError();
            }
        }
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
