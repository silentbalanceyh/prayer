package com.prayer.handler.message;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpServerResponse;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PreValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class RecordSender implements Handler<AsyncResult<Message<Object>>> {
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	/** **/
	@NotNull
	private transient final HttpServerResponse response;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	/**
	 * 创建ReplyHandler
	 * 
	 * @param response
	 * @return
	 */
	public static RecordSender create(final HttpServerResponse response) {
		return new RecordSender(response);
	}

	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	@PreValidateThis
	public void handle(@NotNull final AsyncResult<Message<Object>> event) {
		if (event.succeeded()) {
			final String data = (String) event.result().body();
			this.response.end(data);
		}
	}

	// ~ Methods =============================================
	// ~ Private Methods =====================================
	private RecordSender(final HttpServerResponse response) {
		this.response = response;
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
