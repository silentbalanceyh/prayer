package com.prayer.handler.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;
/**
 * 
 * @author Lang
 *
 */
@Guarded
public class ConversionHandler implements Handler<RoutingContext> {
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(RouterHandler.class);
	// ~ Instance Fields =====================================
	
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	@PostValidateThis
	public ConversionHandler(){
		
	}
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================

	/** **/
	@Override
	public void handle(@NotNull final RoutingContext routingContext) {
		// TODO Auto-generated method stub
	}
	
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
