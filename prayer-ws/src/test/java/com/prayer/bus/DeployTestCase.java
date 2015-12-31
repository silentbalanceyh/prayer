package com.prayer.bus;

import static com.prayer.util.Instance.singleton;
import static com.prayer.util.debug.Log.info;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.bus.impl.oob.DataSevImpl;
import com.prayer.bus.impl.oob.DeploySevImpl;
import com.prayer.constant.Resources;
import com.prayer.constant.log.InfoKey;
import com.prayer.dao.impl.jdbc.MetadataConnImpl;
import com.prayer.facade.bus.DataService;
import com.prayer.facade.bus.DeployService;
import com.prayer.facade.dao.jdbc.MetadataConn;
import com.prayer.model.bus.ServiceResult;

/**
 * 
 * @author Lang
 *
 */
public class DeployTestCase {
    // ~ Static Fields =======================================

    /** 日志记录器 **/
    private static final Logger LOGGER = LoggerFactory.getLogger(DeployTestCase.class);
    // ~ Instance Fields =====================================
    /** **/
    private transient final DeployService service = singleton(DeploySevImpl.class);
    /** **/
    private transient final DataService dataSev = singleton(DataSevImpl.class);
    /** **/
    private transient final MetadataConn metaConn = singleton(MetadataConnImpl.class);

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** **/
    @Before
    public void setUp() {
        final String scriptFile = Resources.META_INIT_SQL;
        info(LOGGER, InfoKey.INF_META_INIT, scriptFile);
        this.metaConn.initMeta(Resources.class.getResourceAsStream(scriptFile));
    }

    /**
     * 
     */
    @Test
    public void testDeploy() throws Exception {
        ServiceResult<Boolean> ret = this.service.deployMetadata(Resources.OOB_FOLDER);
        assertTrue("[TD] Deploying failure ! ", ret.getResult());
        // 添加默认账号信息的测试用例
        ret = this.dataSev.purgeData("sec.account");
        ret = this.dataSev.deployData(Resources.OOB_FOLDER, "sec.account");
        // 添加默认角色信息
        ret = this.dataSev.purgeData("sec.role");
        ret = this.dataSev.deployData(Resources.OOB_FOLDER, "sec.role");
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
