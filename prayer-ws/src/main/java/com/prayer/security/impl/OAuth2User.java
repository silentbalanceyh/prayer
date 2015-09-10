package com.prayer.security.impl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AbstractUser;
import io.vertx.ext.auth.AuthProvider;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class OAuth2User extends AbstractUser {
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	/** Vert.X 实例引用 **/
	@NotNull
	private transient Vertx vertxRef;
	/** **/
	private transient JsonObject principal;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	@PostValidateThis
	public OAuth2User(@NotNull final Vertx vertxRef) {
		this.vertxRef = vertxRef;
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	@Override
	public JsonObject principal() {
		System.out.println("principal");
		return null;
	}

	@Override
	public void setAuthProvider(@NotNull final AuthProvider authProvider) {
		System.out.println("setAuthProvider");
		if (authProvider instanceof OAuth2ProviderImpl) {
			OAuth2ProviderImpl provider = (OAuth2ProviderImpl) authProvider;
		} else {
			throw new IllegalArgumentException("Not a OAuth2ProviderImpl");
		}
	}

	@Override
	protected void doIsPermitted(String permission, Handler<AsyncResult<Boolean>> resultHandler) {
		System.out.println("doIsPermitted");
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
