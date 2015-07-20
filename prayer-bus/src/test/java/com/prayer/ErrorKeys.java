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
	/** **/
	String TST_INFO_SQL = "TST.INFO.SQL";
	/** Database Exception异常 **/
	String TST_PR = "TST.PR";
}
