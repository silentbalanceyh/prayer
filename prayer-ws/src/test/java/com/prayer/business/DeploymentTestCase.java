package com.prayer.business;

import static com.prayer.util.debug.Log.info;
import static com.prayer.util.reflection.Instance.singleton;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.business.deployment.impl.DeployBllor;
import com.prayer.constant.Resources;
import com.prayer.exception.system.RecurrenceReferenceException;
import com.prayer.facade.business.instantor.deployment.DeployInstantor;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.business.behavior.ServiceResult;

/**
 * 
 * @author Lang
 *
 */
public class DeploymentTestCase {
    // ~ Static Fields =======================================
    /** 日志记录器 **/
    private static final Logger LOGGER = LoggerFactory.getLogger(DeploymentTestCase.class);
    /** 默认的SQL文件名 **/
    private static final String INIT_SQL = "initialize";
    // ~ Instance Fields =====================================
    /** 发布用的Service **/
    private transient final DeployInstantor service = singleton(DeployBllor.class);

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** **/
    @Before
    public void setUp() {
        /** 执行SQL **/
        final ServiceResult<Boolean> ret = this.service.initialize(INIT_SQL);
        if (ret.success()) {
            info(LOGGER, "[T] Metadata ( Structure Part ) deployed successfully !");
        }
    }

    /** **/
    @Test
    public void testDeploy() throws AbstractException {
        ServiceResult<Boolean> ret = this.service.manoeuvre(Resources.OOB_DATA_FOLDER);
        if (!ret.success()) {
            throw ret.getError();
        }
    }

    /** **/
    @Test(expected = RecurrenceReferenceException.class)
    public void testDeployExp() throws AbstractException {
        ServiceResult<Boolean> ret = this.service.manoeuvre("deploy/test");
        if (!ret.success()) {
            throw ret.getError();
        }
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
