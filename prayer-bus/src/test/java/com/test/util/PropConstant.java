package com.test.util;
/**
 * 测试com.lyra.util.prop中的类对应方法
 *
 * @author Lang
 * @see
 */
interface PropConstant {
	/**
	 * 测试的资源文件路径
	 */
	String TEST_FILE = "/proploader.properties";
	/**
	 * getLong(String)
	 */
	String M_GET_LONG = "getLong(String)";
	/**
	 * getInt(String)
	 */
	String M_GET_INT = "getInt(String)";
	/**
	 * getString(String)
	 */
	String M_GET_STRING = "getString(String)";
	/**
	 * getBoolean(String)
	 */
	String M_GET_BOOLEAN = "getBoolean(String)";
	/**
	 * getProp()
	 */
	String M_GET_PROP1 = "getProp()";
	/**
	 * getProp(String)
	 */
	String M_GET_PROP2 = "getProp(String)";
	/**
	 * 
	 * @param method
	 */
	void setMethod(String method);
}
