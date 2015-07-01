package com.test.prayer.meta.schema;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.AbstractSchemaException;
import com.prayer.exception.schema.PatternNotMatchException;

/**
 * 
 * @author Lang
 * @see
 */
public class _3MetaPattern1TestCase extends AbstractSchemaTestCase { // NOPMD
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory
			.getLogger(_3MetaPattern1TestCase.class);
	/** **/
	private static final String ERR_NAME_MSG = "[E10003] Meta -> name ==> (Failure) Pattern does not match!";
	/** **/
	private static final String ERR_NS_MSG = "[E10003] Meta -> namespace ==> (Failure) Pattern does not match!";

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/**
	 * 
	 */
	@Override
	public Logger getLogger() {
		return LOGGER;
	}

	// ~ Methods =============================================
	/**
	 * name以小写开始
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = PatternNotMatchException.class)
	public void testMeta1Name10003() throws AbstractSchemaException {
		testImport("meta-name10003-1.json",
				ERR_NAME_MSG);
	}

	/**
	 * name以符号开始
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = PatternNotMatchException.class)
	public void testMeta2Name10003() throws AbstractSchemaException {
		testImport("meta-name10003-2.json",
				ERR_NAME_MSG);
	}

	/**
	 * name以数字开始
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = PatternNotMatchException.class)
	public void testMeta3Name10003() throws AbstractSchemaException {
		testImport("meta-name10003-3.json",
				ERR_NAME_MSG);
	}

	/**
	 * name长度异常
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = PatternNotMatchException.class)
	public void testMeta4Name10003() throws AbstractSchemaException {
		testImport("meta-name10003-4.json",
				ERR_NAME_MSG);
	}

	/**
	 * namespace以大写开头
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = PatternNotMatchException.class)
	public void testMeta1Namespace10003() throws AbstractSchemaException {
		testImport("meta-namespace10003-1.json",
				ERR_NS_MSG);
	}

	/**
	 * namespace包含其他特殊字符集
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = PatternNotMatchException.class)
	public void testMeta2Namespace10003() throws AbstractSchemaException {
		testImport("meta-namespace10003-2.json",
				ERR_NS_MSG);
	}

	/**
	 * namespace以非数字字符开头
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = PatternNotMatchException.class)
	public void testMeta3Namespace10003() throws AbstractSchemaException {
		testImport("meta-namespace10003-3.json",
				ERR_NS_MSG);
	}

	/**
	 * category不是两个合法值之一
	 * 
	 * @throws AbstractSchemaException
	 */
	@Test(expected = PatternNotMatchException.class)
	public void testMeta1Category10003() throws AbstractSchemaException {
		testImport("meta-category10003-1.json",
				"[E10003] Meta -> category ==> (Failure) Pattern does not match!");
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
