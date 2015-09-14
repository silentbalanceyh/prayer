package com.prayer.handler.security;

import com.prayer.handler.security.impl.BasicAuthHandlerImpl;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.web.handler.AuthHandler;

/**
 * 自定义的BasicAuthHandler，替换Vertx中的BasicAuthHandler用
 * 
 * @author Lang
 *
 */
@VertxGen
public interface BasicAuthHandler extends AuthHandler {
	/** DEFAULT REALM **/
	String DEFAULT_REALM = "PRAYER-BUS";

	/**
	 * 默认的Basic的Handler创建
	 * 
	 * @param authProvider
	 * @return
	 */
	static AuthHandler create(final AuthProvider authProvider) {
		return new BasicAuthHandlerImpl(authProvider, DEFAULT_REALM);
	}

	/**
	 * 
	 * @param authProvider
	 * @param realm
	 * @return
	 */
	static AuthHandler create(final AuthProvider authProvider, final String realm) {
		return new BasicAuthHandlerImpl(authProvider, realm);
	}
}
