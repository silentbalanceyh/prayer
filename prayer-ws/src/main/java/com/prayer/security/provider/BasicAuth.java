package com.prayer.security.provider;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.ext.auth.AuthProvider;

/**
 * 
 * @author Lang
 *
 */
@VertxGen
public interface BasicAuth extends AuthProvider{	// NOPMD
	/** 默认Realm属性 **/
	String DFT_REALM = "realm";
	/** 默认用户Schema的ID **/
	String DFT_SCHEMA_ID = "user.schema.id";
	/** 业务逻辑层脚本名称 **/
	String DFT_SCRIPT_NAME = "script.name";
	/** 扩展属性 **/
	String DFT_EXTENSION = "extension";
	
	/** 账号字段信息 **/
	String DFT_ACCOUNT_ID = "user.account";
	/** 账号Email信息 **/
	String DFT_EMAIL = "user.email";
	/** 账号手机信息 **/
	String DFT_MOBILE = "user.mobile";
	/** 账号的密码字段信息 **/
	String DFT_PWD = "user.password";
	// ----------------------------响应结果信息 --------------------------------
	/** username参数丢失 **/
	String RET_M_USER = "USERNAME.MISSING";
	/** password参数丢失 **/
	String RET_M_PWD = "PASSWORD.MISSING";
	/** user不存在 **/
	String RET_M_INVALID = "USER.NOT.FOUND";
	/** 用户名和密码不匹配 **/
	String RET_I_USER_PWD = "AUTH.FAILURE";
	/** Shared User **/
	String KEY_POOL_USER = "SHARED.USER";
	/** User ID **/
	String KEY_USER_ID = "UID";
}
