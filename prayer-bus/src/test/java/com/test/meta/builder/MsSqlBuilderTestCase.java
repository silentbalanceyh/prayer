package com.test.meta.builder;

import static com.prayer.util.Instance.singleton;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.prayer.constant.Resources;
import com.prayer.meta.builder.MsSqlBuilder;
import com.prayer.mod.meta.GenericSchema;

import jodd.util.StringUtil;

/**
 * 
 * @author Lang
 *
 */
public class MsSqlBuilderTestCase extends AbstractBuilderTestCase {
	// ~ Static Fields =======================================
	/** **/
	private static final String DB_CATEGORY = "MSSQL";
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
/*	*//** **//*
	@Before
	public void setUp() {
		if (StringUtil.equals(Resources.DB_CATEGORY, DB_CATEGORY) && null != this.builder) {
			final GenericSchema schema = this.getService().findModel("com.prayer.model.sec", "Role");
			if (null != schema) {
				this.builder = singleton(MsSqlBuilder.class, schema);
			}
		}
	}

	*//** **//*
	@Test
	public void testCreateTable() {
		if (StringUtil.equals(Resources.DB_CATEGORY, DB_CATEGORY) && null != this.builder) {
			if (!this.builder.existTable()) {
				final boolean ret = this.builder.createTable();
				assertTrue("[T] Created Table Successfully ! Result = " + ret, ret);
				// Purge created Table
				this.builder.purgeTable();
			}
		}
	}

	*//** **//*
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
		}
	}

	*//** **//*
	@After
	public void setDown() {
		if (StringUtil.equals(Resources.DB_CATEGORY, DB_CATEGORY) && null != this.builder) {
			// 测试完成过后保证系统中存在一张表
			// if (!this.builder.existTable()) {
				// this.builder.createTable();
			// }
		}
	}*/
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
