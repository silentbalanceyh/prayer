package com.prayer.uca.consumer;

import static com.prayer.assistant.WebLogger.info;
import static com.prayer.util.Converter.fromStr;
import static com.prayer.util.Instance.singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.assistant.Extractor;
import com.prayer.assistant.WebLogger;
import com.prayer.bus.std.RecordService;
import com.prayer.bus.std.impl.RecordSevImpl;
import com.prayer.constant.Constants;
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
public final class QueryConsumer implements Handler<Message<Object>> {
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(QueryConsumer.class);
	// ~ Instance Fields =====================================
	/** **/
	@NotNull
	private transient final RecordService recordSev;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	public QueryConsumer() {
		this.recordSev = singleton(RecordSevImpl.class);
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	@PreValidateThis
	public void handle(@NotNull final Message<Object> event) {
		info(LOGGER,WebLogger.I_COMMON_INFO,"Consumer --> " + getClass().toString());
		// 1.从EventBus中接受数据
		final JsonObject params = (JsonObject) event.body();
		// 2.获取方法信息
		final HttpMethod method = fromStr(HttpMethod.class, params.getString(Constants.PARAM.METHOD));
		// 3.根据不同的方法的Record
		String content = null;
		switch (method) {
		// 只有POST方法才能触发这种分页的信息
		case POST: {
			content = this.page(params);
		}
			break;
		default: {
			content = Constants.EMPTY_JOBJ;
		}
			break;
		}
		event.reply(content);
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================

	private String page(final JsonObject params) {
		final ServiceResult<JsonArray> result = this.recordSev.page(params);
		return Extractor.getContent(result);
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
