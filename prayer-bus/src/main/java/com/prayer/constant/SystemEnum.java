package com.prayer.constant;
/**
 * 系统使用的基础枚举类
 *
 * @author Lang
 * @see
 */
public final class SystemEnum { 	// NOPMD
	/** Meta的S_CATEGORY属性 **/
	public static enum MetaCategory{
		ENTITY,		
		RELATION	
	}
	/** Meta的S_MAPPING属性 **/
	public static enum MetaMapping{
		DIRECT,
		COMBINATED,
		PARTIAL
	}
	/** Meta的S_POLICY属性 **/
	public static enum MetaPolicy{
		GUID,
		INCREMENT,
		ASSIGNED,
		COLLECTION
	}
	/** Key的S_CATEGORY属性 **/
	public static enum KeyCategory{
		PrimaryKey,
		ForeignKey,
		UniqueKey
	}
	/** Field的C_DATETIME属性 **/
	public static enum FieldDatetime{
		STRING,
		TIMER
	}
	/** 从Business的相应标记 **/
	public static enum ResponseCode{
		SUCCESS,	// 成功返回
		FAILURE,	// 非系统运行异常失败返回
		ERROR		// 系统运行异常失败返回
	}
	/** 标记状态列 **/
	public static enum StatusFlag{
		UPDATE,		// 需要更新的列
		ADD,		// 需要添加的列
		DELETE		// 需要删除的列
	}
	/** Http方法 **/
	public static enum HttpMethod{
		GET,
		POST,
		PUT,
		DELETE
	}
	// ~ Constructors ========================================
	private SystemEnum(){}
}
