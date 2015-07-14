package com.test.meta.builder;

import static com.prayer.util.Error.info;
import static com.prayer.util.Instance.singleton;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Resources;
import com.prayer.meta.builder.MsSqlBuilder;
import com.prayer.mod.meta.GenericSchema;

import jodd.util.StringUtil;

/**
 * 
 * @author Lang
 *
 */
public class MsSqlBuilderTestCase extends AbstractBuilderTestCase {	// NOPMD
	// ~ Static Fields =======================================
	/** **/
	private static final String DB_CATEGORY = "MSSQL";
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(MsSqlBuilderTestCase.class);

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	/** **/
	@Before
	public void setUp() {
		if (StringUtil.equals(Resources.DB_CATEGORY, DB_CATEGORY)) {
			final GenericSchema schema = this.getService().findModel("com.prayer.model.sec", "Role");
			info(LOGGER, "[T] Schema data : " + schema);
			if (null != schema) {
				this.builder = singleton(MsSqlBuilder.class, schema);
			}
		} else {
			this.executeNotMatch();
		}
	}

	/** **/
	@Test
	public void testCreateTable() {
		if (StringUtil.equals(Resources.DB_CATEGORY, DB_CATEGORY) && null != this.builder) {
			boolean prepRet = false;
			if (this.builder.existTable()) {
				prepRet = this.builder.purgeTable();
			}
			if (prepRet && !this.builder.existTable()) {
				final boolean ret = this.builder.createTable();
				assertTrue("[T] Created Table Successfully ! Result = " + ret, ret);
				// Purge created Table
				this.builder.purgeTable();
			}
		} else {
			this.executeNotMatch();
		}
	}

	/** **/
	@Test
	public void testPurgeTable() {
		if (StringUtil.equals(Resources.DB_CATEGORY, DB_CATEGORY) && null != this.builder) {
			boolean prepRet = false;
			if (!this.builder.existTable()) {
				prepRet = this.builder.createTable();
			}
			if (prepRet && this.builder.existTable()) {
				final boolean ret = this.builder.purgeTable();
				assertTrue("[T] Purged Table Successfully ! Result = " + ret, ret);
			}
		} else {
			this.executeNotMatch();
		}
	}

	/** **/
	@After
	public void setDown() {
		if (StringUtil.equals(Resources.DB_CATEGORY, DB_CATEGORY) && null != this.builder) {
			// 测试完成过后保证系统中存在一张表
			if (!this.builder.existTable()) {
				this.builder.createTable();
			}
		} else {
			this.executeNotMatch();
		}
	}

	// ~ Private Methods =====================================
	private void executeNotMatch() {
		info(LOGGER, "[T] Database not match ! Expected: " + DB_CATEGORY + ", Actual: " + Resources.DB_CATEGORY
				+ " Or Builder is Null: " + (this.builder == null));
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
