package com.prayer.security.provider.impl;

import com.prayer.security.provider.BasicAuth;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.auth.User;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 * 
 */
@Guarded
public class BasicAuthImpl implements AuthProvider, BasicAuth {
	// ~ Static Fields =======================================

	// ~ Instance Fields =====================================

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/**
	 * 
	 */
	@Override
	public void authenticate(JsonObject authInfo, Handler<AsyncResult<User>> resultHandler) {
		// TODO Auto-generated method stub
		System.out.println(authInfo.encodePrettily());
		final String username = authInfo.getString("username");
		resultHandler.handle(Future.succeededFuture(new BasicUser(username, this, "Admin")));
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
