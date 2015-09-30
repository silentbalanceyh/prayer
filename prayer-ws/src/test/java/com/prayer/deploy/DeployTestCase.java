package com.prayer.deploy;

import static com.prayer.util.Instance.singleton;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.prayer.bus.deploy.oob.DeploySevImpl;
import com.prayer.bus.std.DeployService;
import com.prayer.constant.Resources;
import com.prayer.dao.record.RecordDao;
import com.prayer.dao.record.impl.RecordDaoImpl;
import com.prayer.db.conn.MetadataConn;
import com.prayer.db.conn.impl.MetadataConnImpl;
import com.prayer.model.bus.ServiceResult;

/**
 * 
 * @author Lang
 *
 */
public class DeployTestCase {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    private transient final DeployService service = singleton(DeploySevImpl.class);
    /** **/
    @SuppressWarnings("unused")
    private transient final RecordDao recordDao = singleton(RecordDaoImpl.class);

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
        final String scriptFile = Resources.DB_SQL_DIR + MetadataConn.H2_SQL;
        this.metaConn.initMeta(Resources.class.getResourceAsStream(scriptFile));
    }

    /**
     * 
     */
    @Test
    public void testDeploy() throws Exception {
        final ServiceResult<Boolean> ret = this.service.deployPrayerData();
        assertTrue("[TD] Deploying failure ! ", ret.getResult());
        /*// 添加默认账号信息的测试用例
        final Record record = new GenericRecord("sec.account");
        record.set("username", "lang.yu");
        record.set("email", "silentbalanceyh@126.com");
        record.set("mobile", "15900000000");
        record.set("password", Encryptor.encryptMD5("pl,okm123"));
        this.recordDao.insert(record);*/
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
