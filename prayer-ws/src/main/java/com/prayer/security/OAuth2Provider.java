package com.prayer.security;

import com.prayer.security.impl.OAuth2ProviderImpl;

import io.vertx.core.Vertx;
import io.vertx.ext.auth.AuthProvider;
/**
 * OAuth协议的自定义认证接口
 * @author Lang
 *
 */
public interface OAuth2Provider extends AuthProvider{
	/** **/
	String DEFAULT_HTTP_HEADER = "Bearer";
	/**
	 * 创建一个AuthProvider
	 * @param vertxRef
	 * @return
	 */
	static OAuth2Provider create(final Vertx vertxRef){
		return new OAuth2ProviderImpl(vertxRef);
	}
}
