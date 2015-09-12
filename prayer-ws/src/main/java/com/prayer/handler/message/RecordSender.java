package com.prayer.handler.message;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PreValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class RecordSender implements Handler<AsyncResult<Message<Object>>>{
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	/** **/
	@NotNull
	private transient HttpServerResponse response;
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	/**
	 * 创建ReplyHandler
	 * @param response
	 * @return
	 */
	public static RecordSender create(final HttpServerResponse response){
		return new RecordSender(response);
	}
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	@PreValidateThis
	public void handle(@NotNull final AsyncResult<Message<Object>> event) {
		if(event.succeeded()){
			final JsonObject webRet = (JsonObject)event.result().body();
			this.response.end(webRet.encodePrettily());
		}
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	private RecordSender(final HttpServerResponse response){
		this.response = response;
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
