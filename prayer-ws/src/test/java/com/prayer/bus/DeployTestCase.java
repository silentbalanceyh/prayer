package com.prayer.bus;

import static com.prayer.util.Instance.singleton;
import static com.prayer.util.Log.info;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.bus.impl.oob.DeploySevImpl;
import com.prayer.bus.impl.std.RecordSevImpl;
import com.prayer.dao.impl.jdbc.JdbcConnImpl;
import com.prayer.dao.impl.jdbc.MetadataConnImpl;
import com.prayer.facade.bus.DeployService;
import com.prayer.facade.bus.RecordService;
import com.prayer.facade.dao.jdbc.JdbcContext;
import com.prayer.facade.dao.jdbc.MetadataConn;
import com.prayer.model.bus.ServiceResult;
import com.prayer.util.Encryptor;
import com.prayer.util.IOKit;
import com.prayer.util.cv.Constants;
import com.prayer.util.cv.Resources;
import com.prayer.util.cv.log.InfoKey;

import io.vertx.core.json.JsonObject;

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
    private transient final RecordService recordSev = singleton(RecordSevImpl.class);

    /** **/
    private transient final MetadataConn metaConn = singleton(MetadataConnImpl.class);
    /** **/
    private transient final JdbcContext dataContext = singleton(JdbcConnImpl.class);

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
        final ServiceResult<Boolean> ret = this.service.deployPrayerData(Resources.META_OD_FOLDER);
        assertTrue("[TD] Deploying failure ! ", ret.getResult());

        // 添加默认账号信息的测试用例
        this.dataContext.executeBatch("DELETE FROM SEC_ACCOUNT;");
        final Long count = this.dataContext.count("SELECT COUNT(*) FROM SEC_ACCOUNT WHERE S_USERNAME='lang.yu'");
        if (Constants.ZERO == count) {
            final String content = IOKit.getContent("/deploy/oob/data/account.json");
            final JsonObject params = new JsonObject(content);
            final String password = Encryptor.encryptMD5(params.getJsonObject("data").getString("password"));
            params.getJsonObject("data").put("password", password);
            info(LOGGER, params.encode());
            recordSev.save(params);
        }
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
