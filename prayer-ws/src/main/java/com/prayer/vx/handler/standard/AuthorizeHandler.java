package com.prayer.vx.handler.standard;

import static com.prayer.util.Instance.singleton;

import com.prayer.bus.ConfigService;
import com.prayer.bus.impl.ConfigSevImpl;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.h2.vx.UriModel;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.PostValidateThis;
import net.sf.oval.guard.PreValidateThis;

/**
 * <code>/oauth/authorize</code>
 * 
 * @author Lang
 *
 */
public class AuthorizeHandler implements Handler<RoutingContext> {

	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	/** **/
	@NotNull
	private transient ConfigService service;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	@PostValidateThis
	public AuthorizeHandler() {
		this.service = singleton(ConfigSevImpl.class);
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	@PreValidateThis
	public void handle(final RoutingContext routingContext) {
		final ServiceResult<UriModel> result = this.service.findUri(routingContext.request().path());

		System.out.println("Auth: " + routingContext.request().path());
		// // This handler will be called for every request
		HttpServerResponse response = routingContext.response();
		response.putHeader("content-type", "text/plain");
		// Write to the response and end it
		response.setChunked(true);
		response.write("Route1");
		response.end();
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
