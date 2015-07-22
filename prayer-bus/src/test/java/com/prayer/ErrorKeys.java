package com.prayer;
/**
 * Error Key的存储列表
 * @author Lang
 *
 */
public interface ErrorKeys {	// NOPMD
	/** 构造函数错误信息 **/
	String TST_CONS = "TST.CONS";
	/** 启用防御式编程的错误信息 **/
	String TST_OVAL = "TST.OVAL";
	/** 两个内容不等异常 **/
	String TST_EQUAL = "TST.EQUAL";
	/** 返回结果为Boolean异常 **/
	String TST_TF = "TST.TF";
	/** 返回结果必须为Null异常 **/
	String TST_NULL = "TST.NULL";
	/** **/
	String TST_INFO_SQL = "TST.INFO.SQL";
	/** Database Exception异常 **/
	String TST_PR = "TST.PR";
	/** 数据准备过程异常 **/
	String TST_PREP = "TST.PREP";
}
