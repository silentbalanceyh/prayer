package com.prayer.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.AbstractTestTool;
import com.prayer.Assistant;

import net.sf.oval.exception.ConstraintsViolatedException;

/**
 * 
 * @author Lang
 *
 */
public class StringKitJoinTestCase extends AbstractTestTool {	// NOPMD
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(StringKitJoinTestCase.class);

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	protected Logger getLogger() {
		return LOGGER;
	}

	/** **/
	protected Class<?> getTarget() {
		return StringKit.class;
	}

	// ~ Methods =============================================
	/** **/
	@Test(expected = ConstraintsViolatedException.class)
	public void testE00001Mjoin() {
		StringKit.join(null, ',');
		failure(TST_OVAL);
	}

	/** **/
	@Test(expected = ConstraintsViolatedException.class)
	public void testE00002Mjoin() {
		StringKit.join(Assistant.set(0), ',');
		failure(TST_OVAL);
	}

	/** **/
	@Test(expected = ConstraintsViolatedException.class)
	public void testE00003Mjoin() {
		StringKit.join(null, ',', true);
		failure(TST_OVAL);
	}

	/** **/
	@Test(expected = ConstraintsViolatedException.class)
	public void testE00004Mjoin() {
		StringKit.join(Assistant.set(0), ',', false);
		failure(TST_OVAL);
	}

	/** **/
	@Test
	public void testC00001Constructor() {
		assertNotNull(message(TST_CONS, getTarget().getName()), Assistant.instance(StringKit.class));
	}

	/** **/
	@Test
	public void testT00001Mjoin() {
		final Set<String> dataSet = Assistant.set(1);
		final String actual = StringKit.join(dataSet, ',');
		assertEquals(message(TST_EQUAL), "Set0", actual);
	}
	/** **/
	@Test
	public void testT00002MJoin() {
		final Set<String> dataSet = Assistant.set(2);
		final String actual = StringKit.join(dataSet, ',');
		assertEquals(message(TST_EQUAL), Assistant.join(dataSet, ',', false), actual);
	}
	
	/** **/
	@Test
	public void testT00003Mjoin() {
		final Set<String> dataSet = Assistant.set(1);
		final String actual = StringKit.join(dataSet, ',', true);
		assertEquals(message(TST_EQUAL), "Set0", actual);
	}

	/** **/
	@Test
	public void testT00004MJoin() {
		final Set<String> dataSet = Assistant.set(2);
		final String actual = StringKit.join(dataSet, ',', true);
		assertEquals(message(TST_EQUAL), Assistant.join(dataSet, ',', true), actual);
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
