package com.prayer.kernel.query;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.AbstractTestTool;
import com.prayer.Assistant;
import com.prayer.kernel.Expression;
import com.prayer.kernel.Value;
import com.prayer.model.type.IntType;
import com.prayer.model.type.StringType;
import com.prayer.util.StringKitJoinTestCase;

import net.sf.oval.exception.ConstraintsViolatedException;

/**
 * 
 * @author Lang
 *
 */
public class Restrictions01EQTestCase extends AbstractTestTool {	// NOPMD
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(StringKitJoinTestCase.class);
	/** **/
	private static final Value<?> STR_VAL = new StringType("Value");
	/** **/
	private static final Value<?> INT_VAL = new IntType(22);
	/** **/
	private static final String COL_NAME = "COL";

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	/** **/
	protected Logger getLogger() {
		return LOGGER;
	}

	/** **/
	protected Class<?> getTarget() {
		return Restrictions.class;
	}

	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	/** **/
	@Test
	public void testC05001Constructor() {
		assertNotNull(message(TST_CONS, getTarget().getName()), Assistant.instance(getTarget()));
	}
	/** **/
	@Test(expected = ConstraintsViolatedException.class)
	public void testE05001Meq() {
		Restrictions.eq(null);
		failure(TST_OVAL);
	}

	/** **/
	@Test(expected = ConstraintsViolatedException.class)
	public void testE05002Meq() {
		Restrictions.eq("");
		failure(TST_OVAL);
	}

	/** **/
	@Test(expected = ConstraintsViolatedException.class)
	public void testE05003Meq() {
		Restrictions.eq("   ");
		failure(TST_OVAL);
	}

	/** **/
	@Test(expected = ConstraintsViolatedException.class)
	public void testE05004Meq() {
		Restrictions.eq(null, STR_VAL);
		failure(TST_OVAL);
	}

	/** **/
	@Test(expected = ConstraintsViolatedException.class)
	public void testE05005Meq() {
		Restrictions.eq("", STR_VAL);
		failure(TST_OVAL);
	}

	/** **/
	@Test(expected = ConstraintsViolatedException.class)
	public void testE05006Meq() {
		Restrictions.eq("   ", STR_VAL);
		failure(TST_OVAL);
	}

	/** **/
	@Test(expected = ConstraintsViolatedException.class)
	public void testE05007Meq() {
		Restrictions.eq(COL_NAME, null);
		failure(TST_OVAL);
	}
	/** **/
	@Test
	public void testT05001Meq(){
		final Expression expr = Restrictions.eq(COL_NAME);
		final String sqlSeg = expr.toSql();
		info(LOGGER,TST_INFO_SQL,sqlSeg);
		assertEquals(message(TST_EQUAL),COL_NAME + "=?",sqlSeg);
	}
	/** **/
	@Test
	public void testT05002Meq(){
		final Expression expr = Restrictions.eq(COL_NAME,STR_VAL);
		final String sqlSeg = expr.toSql();
		info(LOGGER,TST_INFO_SQL,sqlSeg);
		assertEquals(message(TST_EQUAL),COL_NAME + "='Value'",sqlSeg);
	}
	/** **/
	@Test
	public void testT05003Meq(){
		final Expression expr = Restrictions.eq(COL_NAME,INT_VAL);
		final String sqlSeg = expr.toSql();
		info(LOGGER,TST_INFO_SQL,sqlSeg);
		assertEquals(message(TST_EQUAL),COL_NAME + "=22",sqlSeg);
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
