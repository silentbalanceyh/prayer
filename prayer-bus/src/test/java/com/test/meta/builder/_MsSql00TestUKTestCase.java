package com.test.meta.builder;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.meta.builder.MsSqlBuilder;

/**
 * 
 * @author Lang
 *
 */
public class _MsSql00TestUKTestCase extends AbstractBuilderCPTestCase {
	// ~ Static Fields =======================================
	/** **/
	private static final String DB_CATEGORY = "MSSQL";
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(_MsSql00TestUKTestCase.class);

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	/** **/
	@Override
	protected String getDbCategory() {
		return DB_CATEGORY;
	}

	/** **/
	@Override
	protected Class<?> getBuilder() {
		return MsSqlBuilder.class;
	}

	// ~ Methods =============================================
	/** **/
	@Before
	public void setUp() {
		this.beforeExecute("_MsSql00TestUK.json", "tst.mod.uk1");
	}

	/** **/
	@Test
	public void test00UKCreate() {
		final boolean ret = this.createTable();
		assertTrue("[T] Created Table Successfully ! Result = " + ret, ret);
		// Post
		if (ret) {
			this.builder.purgeTable();
		}
	}

	/** **/
	@Test
	public void test00UKPurge() {
		final boolean ret = this.purgeTable();
		assertTrue("[T] Purge Table Successfully ! Result = " + ret, ret);
	}

	/** **/
	@After
	public void setDown() {
		this.afterExecute();
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
