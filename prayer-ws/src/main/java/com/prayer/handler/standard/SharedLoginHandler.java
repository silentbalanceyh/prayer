package com.prayer.handler.standard;

import static com.prayer.uca.assistant.WebLogger.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;
import com.prayer.security.provider.BasicAuth;
import com.prayer.security.provider.impl.BasicUser;
import com.prayer.util.StringKit;

import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.core.shareddata.SharedData;
import io.vertx.ext.web.RoutingContext;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 同步远程和客户端之间的Session用的Handler
 * 
 * @author Lang
 *
 */
@Guarded
public final class SharedLoginHandler implements Handler<RoutingContext> {
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(RecordHandler.class);
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	/**
	 * 静态实例化方法
	 * 
	 * @return
	 */
	public static SharedLoginHandler create() {
		return new SharedLoginHandler();
	}

	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	public void handle(@NotNull final RoutingContext context) {
		// 1.处理Request看是否要填充user
		final HttpServerRequest request = context.request();
		final String userId = request.getParam(BasicAuth.KEY_USER_ID);
		if (StringKit.isNonNil(userId)) {
			initLogin(context, userId);
		}
		// 2.Next
		context.next();
	}

	// ~ Methods =============================================
	// ~ Private Methods =====================================
	/**
	 * 参数中包含了UID的情况
	 * 
	 * @param context
	 */
	private void initLogin(final RoutingContext context, final String userId) {
		final SharedData data = context.vertx().sharedData();
		final LocalMap<String, Buffer> sharedMap = data.getLocalMap(BasicAuth.KEY_POOL_USER);
		final Buffer buffer = sharedMap.get(userId);
		if (null != buffer) {
			final BasicUser user = new BasicUser();
			final int ret = user.readFromBuffer(Constants.ZERO, buffer);
			if (Constants.ZERO < ret) { // NOPMD
				info(LOGGER, user.toString());
				context.setUser(user);
			}
		}
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
