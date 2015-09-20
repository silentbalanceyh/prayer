package com.prayer.handler.message;

import static com.prayer.util.Converter.fromStr;
import static com.prayer.util.Instance.singleton;

import com.prayer.bus.security.BasicAuthService;
import com.prayer.bus.security.impl.BasicAuthSevImpl;
import com.prayer.constant.Constants;
import com.prayer.model.bus.ServiceResult;

import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpMethod;
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
public final class BasicAuthConsumer implements Handler<Message<Object>> {
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================

	/** **/
	@NotNull
	private transient final BasicAuthService authSev;

	// ~ Static Block ========================================
	/** **/
	public static BasicAuthConsumer create() {
		return new BasicAuthConsumer();
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
		final HttpMethod method = fromStr(HttpMethod.class, params.getString(Constants.PARAM.METHOD));
		// 3.根据方法访问不同的Record方法
		String content = null;
		if(HttpMethod.GET == method){
			final ServiceResult<JsonObject> result = this.authSev.find(params);
			content = result.getResult().encode();
		}
		event.reply(content);
	}

	// ~ Methods =============================================
	// ~ Private Methods =====================================
	

	private BasicAuthConsumer() {
		this.authSev = singleton(BasicAuthSevImpl.class);
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
