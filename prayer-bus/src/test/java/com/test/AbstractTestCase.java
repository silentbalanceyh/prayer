package com.test;

import static com.prayer.util.Instance.instance;
import static org.junit.Assert.fail;

import java.util.Random;

import com.prayer.prop.PropertyLoader;

/**
 * @author Lang
 * @package com.prayer.test
 * @name AbstractTestCase
 * @class com.prayer.test.AbstractTestCase
 * @see
 */
public class AbstractTestCase {
	// ~ Instance Fields =====================================
	/**
	 * 测试的类名 *
	 */
	private transient final String targetName;
	/**
	 * 测试的接口 *
	 */
	private transient String method;
	/**
	 * 资源加载器
	 * **/
	protected transient PropertyLoader loader;

	// ~ Constructors ========================================

	/**
	 * 抽象构造函数 *
	 */
	protected AbstractTestCase(final String targetName) {
		this.targetName = targetName;
		this.method = "EMPTY";
	}

	// ~ Methods =============================================

	/**
	 * 设置测试方法，对应接口设成Public
	 *
	 * @param method
	 */
	public void setMethod(final String method) {
		this.method = method;
	}

	/**
	 * 获取测试位置
	 *
	 * @return
	 */
	protected String getPosition() {
		return "[TEST] Class -> " + targetName + ", Method -> " + method;
	}

	/**
	 * 获取随机数：[0,end]
	 *
	 * @param end
	 * @return
	 */
	protected int randomInt(final int end) {
		final Random random = new Random();
		return random.nextInt(end) % (end + 1);
	}

	/**
	 * 初始化一个实例
	 * 
	 * @param className
	 * @return
	 */
	protected <T> T newInstance(final String className) {
		return instance(className);
	}

	/**
	 * JUnit打印错误信息
	 *
	 * @param object
	 */
	protected void failure(final Object object) {
		fail(getPosition() + ", Object: " + object);
	}

	/**
	 * 获取资源加载器
	 * 
	 * @return
	 */
	protected PropertyLoader getLoader() {
		return loader;
	}

}
