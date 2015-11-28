package com.prayer.deploy;

import static com.prayer.util.Instance.singleton;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.prayer.bus.impl.oob.DeploySevImpl;
import com.prayer.dao.impl.jdbc.MetadataConnImpl;
import com.prayer.dao.impl.record.RecordDaoImpl;
import com.prayer.facade.bus.DeployService;
import com.prayer.facade.dao.RecordDao;
import com.prayer.facade.dao.jdbc.MetadataConn;
import com.prayer.model.bus.ServiceResult;
import com.prayer.util.Encryptor;
import com.prayer.util.cv.Resources;

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
        System.out.println(Encryptor.encryptMD5("pl,okmijn123"));
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
