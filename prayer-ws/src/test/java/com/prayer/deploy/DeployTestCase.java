package com.prayer.deploy;

import static com.prayer.util.Instance.singleton;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.prayer.bus.DeployService;
import com.prayer.bus.impl.DeploySevImpl;
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

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
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
