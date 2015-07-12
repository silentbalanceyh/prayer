package com.test.util;

import static com.prayer.util.Instance.reservoir;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Properties;

import net.sf.oval.exception.ConstraintsViolatedException;

import org.junit.Test;

import com.prayer.util.PropertyKit;
import com.test.AbstractTestCase;

/**
 * 测试com.lyra.util.prop.PropLoader
 *
 * @author Lang
 * @see
 */
public class PropLoaderTestCase extends AbstractTestCase implements
		PropConstant {
	// ~ Constructors ========================================
	/**
	 * 
	 */
	public PropLoaderTestCase() {
		super(PropertyKit.class.getName());
		loader = reservoir(OBJ_POOLS, getClass().getName(), PropertyKit.class,
				TEST_FILE);
	}

	// ~ Methods =============================================
	/**
	 * 构造函数测试
	 */
	@Test
	public void testPropLoader1() {
		setMethod("Constructor!");
		final PropertyKit instance = new PropertyKit(getClass(),
				TEST_FILE);// singleton(PropertyKit.class,getClass(),TEST_FILE);

		assertNotNull(getPosition(), instance);
	}

	/**
	 * 构造函数的PostValidateThis测试
	 * 
	 * @return
	 */
	@Test
	public void testPropLoader2() {
		setMethod("Constructor!");
		final PropertyKit instance = new PropertyKit(getClass(),
				"x.properties");// singleton(PropertyKit.class,getClass(),"x.properties");
		assertNotNull(getPosition(), instance.getProp());
	}

	/**
	 * 
	 */
	@Test
	public void testGetProp1() {
		setMethod(M_GET_PROP1);
		final Properties prop = this.getLoader().getProp();
		assertNotNull(getPosition(), prop);
	}

	/**
	 * 
	 */
	@Test
	public void testGetProp2() {
		setMethod(M_GET_PROP2);
		final Properties prop = this.getLoader().getProp(TEST_FILE);
		final Properties prop1 = this.getLoader().getProp();
		assertEquals(getPosition(), prop, prop1);
	}

	/**
	 * 
	 */
	@Test(expected = ConstraintsViolatedException.class)
	public void testGetProp3() {
		setMethod(M_GET_PROP2);
		final Properties prop = this.getLoader().getProp(null);
		failure(prop);
	}

	/**
	 * 
	 */
	@Test(expected = ConstraintsViolatedException.class)
	public void testGetProp4() {
		setMethod(M_GET_PROP2);
		final Properties prop = this.getLoader().getProp("");
		failure(prop);
	}

	/**
	 * 
	 */
	@Test(expected = ConstraintsViolatedException.class)
	public void testGetProp5() {
		setMethod(M_GET_PROP2);
		final Properties prop = this.getLoader().getProp("   ");
		failure(prop);
	}
	// ~ Private Methods =====================================
	// ~ hashCode,equals,toString ============================
}
