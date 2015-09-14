package com.prayer.security.provider;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.ext.auth.AuthProvider;

/**
 * 
 * @author Lang
 *
 */
@VertxGen
public interface BasicAuth extends AuthProvider{
	/** 默认Realm属性 **/
	String DFT_REALM = "realm";
	/** 默认用户Schema的ID **/
	String DFT_SCHEMA_ID = "user.schema.id";
	/** Account 字段 **/
	String DFT_USER_ACCOUNT = "user.account";
	/** Email 字段 -> 支持Email登录时需要 **/
	String DFT_USER_EMAIL = "user.email";
	/** Mobile 字段 -> 支持手机登录时需要 **/
	String DFT_USER_MOBILE = "user.mobile";
	/** Password 字段 -> 密码字段，必须 **/
	String DFT_USER_PWD = "user.password";
}
