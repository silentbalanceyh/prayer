package com.prayer.kernel.query;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.AbstractTestTool;
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
public class Restrictions06GETestCase extends AbstractTestTool { // NOPMD
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
	@Test(expected = ConstraintsViolatedException.class)
	public void testE05036Mge() {
		Restrictions.ge(null);
		failure(TST_OVAL);
	}

	/** **/
	@Test(expected = ConstraintsViolatedException.class)
	public void testE05037Mge() {
		Restrictions.ge("");
		failure(TST_OVAL);
	}

	/** **/
	@Test(expected = ConstraintsViolatedException.class)
	public void testE05038Mge() {
		Restrictions.ge("   ");
		failure(TST_OVAL);
	}

	/** **/
	@Test(expected = ConstraintsViolatedException.class)
	public void testE05039Mge() {
		Restrictions.ge(null, STR_VAL);
		failure(TST_OVAL);
	}

	/** **/
	@Test(expected = ConstraintsViolatedException.class)
	public void testE05040Mge() {
		Restrictions.ge("", STR_VAL);
		failure(TST_OVAL);
	}

	/** **/
	@Test(expected = ConstraintsViolatedException.class)
	public void testE05041Mge() {
		Restrictions.ge("   ", STR_VAL);
		failure(TST_OVAL);
	}

	/** **/
	@Test(expected = ConstraintsViolatedException.class)
	public void testE05042Mge() {
		Restrictions.ge(COL_NAME, null);
		failure(TST_OVAL);
	}

	/** **/
	@Test
	public void testT05016Mge() {
		final Expression expr = Restrictions.ge(COL_NAME);
		final String sqlSeg = expr.toSql();
		info(LOGGER, TST_INFO_SQL, sqlSeg);
		assertEquals(message(TST_EQUAL), COL_NAME + ">=?", sqlSeg);
	}

	/** **/
	@Test
	public void testT05017Mge() {
		final Expression expr = Restrictions.ge(COL_NAME, STR_VAL);
		final String sqlSeg = expr.toSql();
		info(LOGGER, TST_INFO_SQL, sqlSeg);
		assertEquals(message(TST_EQUAL), COL_NAME + ">='Value'", sqlSeg);
	}

	/** **/
	@Test
	public void testT05018Mge() {
		final Expression expr = Restrictions.ge(COL_NAME, INT_VAL);
		final String sqlSeg = expr.toSql();
		info(LOGGER, TST_INFO_SQL, sqlSeg);
		assertEquals(message(TST_EQUAL), COL_NAME + ">=22", sqlSeg);
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
