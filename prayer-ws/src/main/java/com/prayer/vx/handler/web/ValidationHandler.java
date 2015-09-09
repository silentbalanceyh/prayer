package com.prayer.vx.handler.web;

import com.prayer.constant.Constants;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import net.sf.oval.guard.Guarded;

/**
 * 【Step 2】参数的第二部检查 -> Validator/Convertor
 * @author Lang
 *
 */
@Guarded
public class ValidationHandler implements Handler<RoutingContext>{

	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	public void handle(final RoutingContext routingContext) {
		final String uriId = routingContext.get(Constants.VX_CTX_URI);
		System.out.println("Validation: " + uriId);
		
		routingContext.next();
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
