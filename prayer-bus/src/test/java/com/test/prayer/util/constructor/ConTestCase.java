package com.test.prayer.util.constructor;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.internal.Classes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.res.Constants;
import com.prayer.res.DbAccessor;
import com.prayer.res.Resources;

/**
 * 构造函数的生成
 *
 * @author Lang
 * @see
 */
public class ConTestCase extends AbstractConTestCase{
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
	public void testCon2(){
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("[TD] Test constructor2 -> " + Constants.class.getName());
		}
		final Constants ref = instance(Constants.class.getName());
		assertNotNull("[E] testCon2",ref);
	}
	
	/** **/
	@Test
	public void testCon3(){
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("[TD] Test constructor3 -> " + DbAccessor.class.getName());
		}
		final DbAccessor ref = instance(DbAccessor.class.getName());
		assertNotNull("[E] testCon3",ref);
	}
	
	/** **/
	@Test
	public void testCon4(){
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("[TD] Test constructor4 -> com.prayer.res.PropKeys");
		}
		final Object ref = instance("com.prayer.res.PropKeys");
		assertNotNull("[E] testCon4",ref);
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
