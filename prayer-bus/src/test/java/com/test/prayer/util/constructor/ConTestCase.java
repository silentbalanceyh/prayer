package com.test.prayer.util.constructor;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.internal.Classes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.res.cv.Accessors;
import com.prayer.res.cv.Resources;

/**
 * 构造函数的生成
 *
 * @author Lang
 * @see
 */
public class ConTestCase extends AbstractConTestCase{ // NOPMD
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ConTestCase.class);
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	/** **/
	@Test
	public void testCon1(){
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("[TD] Test constructor1 -> " + Classes.class.getName());
		}
		final Classes ref = instance(Classes.class.getName());
		assertNotNull("[E] testCon1",ref);
	}
	
	/** **/
	@Test
	public void testCon3(){
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("[TD] Test constructor3 -> " + Accessors.class.getName());
		}
		final Accessors ref = instance(Accessors.class.getName());
		assertNotNull("[E] testCon3",ref);
	}
	
	/** **/
	@Test
	public void testCon5(){
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("[TD] Test constructor5 -> " + Resources.class.getName());
		}
		final Resources ref = instance(Resources.class.getName());
		assertNotNull("[E] testCon5",ref);
	}
	// ~ Private Methods =====================================
	// ~ hashCode,equals,toString ============================
}
