package com.prayer.deploy;

import static com.prayer.util.Instance.singleton;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.prayer.bus.deploy.oob.DeploySevImpl;
import com.prayer.bus.std.DeployService;
import com.prayer.constant.Resources;
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
	private transient DeployService service = singleton(DeploySevImpl.class);
	
	/** **/
	private transient final MetadataConn metaConn = singleton(MetadataConnImpl.class);

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	@Before
	public void setUp(){
		final String scriptFile = Resources.DB_SQL_DIR + MetadataConn.H2_SQL;
		this.metaConn.initMeta(Resources.class.getResourceAsStream(scriptFile));
	}
	/**
	 * 
	 */
	@Test
	public void testDeploy() {
		ServiceResult<Boolean> ret = this.service.deployPrayerData();
		assertTrue("[TD] Deploying failure ! ", ret.getResult());
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
