package com.prayer.bus;

import static com.prayer.util.Instance.singleton;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.prayer.bus.impl.oob.DeploySevImpl;
import com.prayer.dao.impl.jdbc.JdbcConnImpl;
import com.prayer.dao.impl.jdbc.MetadataConnImpl;
import com.prayer.dao.impl.std.record.RecordDaoImpl;
import com.prayer.facade.bus.DeployService;
import com.prayer.facade.dao.RecordDao;
import com.prayer.facade.dao.jdbc.JdbcContext;
import com.prayer.facade.dao.jdbc.MetadataConn;
import com.prayer.facade.kernel.Record;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.kernel.GenericRecord;
import com.prayer.util.Encryptor;
import com.prayer.util.cv.Constants;
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
    private transient final RecordDao recordDao = singleton(RecordDaoImpl.class);

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
        final String scriptFile = Resources.DB_SQL_DIR + MetadataConn.H2_SQL;
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
            final Record record = new GenericRecord("sec.account");
            record.set("username", "lang.yu");
            record.set("email", "lang.yu@hpe.com");
            record.set("mobile", "15922611447");
            record.set("password", Encryptor.encryptMD5("pl,okmijn123"));
            this.recordDao.insert(record);
        }
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
