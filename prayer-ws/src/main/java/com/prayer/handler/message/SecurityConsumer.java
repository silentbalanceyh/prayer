package com.prayer.handler.message;

import static com.prayer.util.Converter.fromStr;
import static com.prayer.util.Instance.singleton;

import com.prayer.bus.std.RecordService;
import com.prayer.bus.std.impl.RecordSevImpl;
import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.model.bus.ServiceResult;

import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonArray;
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
public final class SecurityConsumer implements Handler<Message<Object>> {
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================

	/** **/
	@NotNull
	private transient final RecordService recordSev;

	// ~ Static Block ========================================
	/** **/
	public static SecurityConsumer create() {
		return new SecurityConsumer();
	}

	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	@PreValidateThis
	public void handle(@NotNull final Message<Object> event) {
		// 1.从EventBus中接受数据
		final JsonObject params = (JsonObject) event.body();
		// 2.获取方法信息
		final HttpMethod method = fromStr(HttpMethod.class, params.getString(Constants.PARAM_METHOD));
		// 3.根据方法访问不同的Record方法
		String content = null;
		switch (method) {
		case POST:
			content = this.post(params);
			break;
		case PUT: {
			final ServiceResult<JsonObject> result = this.recordSev.modify(params);
			content = result.getResult().encodePrettily();
		}
			break;
		case DELETE: {
			final ServiceResult<JsonObject> result = this.recordSev.remove(params);
			content = result.getResult().encodePrettily();
		}
			break;
		default: {
			final ServiceResult<JsonArray> result = this.recordSev.find(params);
			content = result.getResult().encodePrettily();
		}
			break;
		}
		event.reply(content);
	}

	// ~ Methods =============================================
	// ~ Private Methods =====================================

	private String post(final JsonObject params) {
		final ServiceResult<JsonObject> result = this.recordSev.save(params);
		String content = Constants.EMPTY_JOBJ;
		if (ResponseCode.SUCCESS == result.getResponseCode()) {
			final JsonObject ret = result.getResult();
			if (null != ret) {
				content = ret.encodePrettily();
			}
		}
		return content;
	}

	private SecurityConsumer() {
		this.recordSev = singleton(RecordSevImpl.class);
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
