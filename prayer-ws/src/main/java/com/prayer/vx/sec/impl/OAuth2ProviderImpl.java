package com.prayer.vx.sec.impl;

import com.prayer.vx.sec.OAuth2Provider;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.User;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class OAuth2ProviderImpl implements OAuth2Provider{

	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	/** Vertx实例 **/
	@NotNull
	private transient Vertx vertxRef;
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	@PostValidateThis
	public OAuth2ProviderImpl(@NotNull final Vertx vertxRef){
		this.vertxRef = vertxRef;
	}
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	@Override
	public void authenticate(JsonObject requestInfo, Handler<AsyncResult<User>> arg1) {
		// TODO Auto-generated method stub
		System.out.println(requestInfo);
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
