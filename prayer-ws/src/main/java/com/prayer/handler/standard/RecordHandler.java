package com.prayer.handler.standard;

import static com.prayer.util.Error.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

/**
 * 
 * @author Lang
 *
 */
public class RecordHandler implements Handler<RoutingContext>{
	// ~ Static Fields =======================================
	
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(RecordHandler.class);
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	@Override
	public void handle(final RoutingContext routingContext) {
		info(LOGGER," Processor : " + getClass().getName() + ", RecordHandler Empty Body");
		routingContext.next();
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
