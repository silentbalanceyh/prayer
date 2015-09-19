package com.prayer.handler.standard;

import static com.prayer.uca.assistant.WebLogger.info;
import static com.prayer.util.Instance.singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;
import com.prayer.security.provider.BasicAuth;
import com.prayer.security.provider.impl.BasicUser;
import com.prayer.util.StringKit;
import com.prayer.vx.configurator.SecurityConfigurator;

import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.core.shareddata.SharedData;
import io.vertx.ext.auth.User;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.Session;
import io.vertx.ext.web.handler.impl.UserHolder;
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
	/** User Holder **/
	private static final String SESSION_USER_HOLDER_KEY = "__vertx.userHolder"; // NOPMD

	// ~ Instance Fields =====================================
	/** **/
	@NotNull
	private transient final SecurityConfigurator securitor;

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
	/**
	 * 
	 */
	private SharedLoginHandler() {
		securitor = singleton(SecurityConfigurator.class);
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	public void handle(@NotNull final RoutingContext context) {
		// 1.处理Request看是否要填充user
		final HttpServerRequest request = context.request();
		final String userId = request.getParam(BasicAuth.KEY_USER_ID);
		if (StringKit.isNonNil(userId)) {
			request(context, userId);
		} else {
			// 2.如果UID不存在，则检查Session
			session(context);
		}
		// 3.Next
		context.next();
	}

	// ~ Methods =============================================
	// ~ Private Methods =====================================
	/**
	 * 参数中包含了UID的情况
	 * 
	 * @param context
	 */
	private void request(final RoutingContext context, final String userId) {
		final SharedData data = context.vertx().sharedData();
		final LocalMap<String, Buffer> sharedMap = data.getLocalMap(BasicAuth.KEY_POOL_USER);
		final Buffer buffer = sharedMap.get(userId);
		if (null != buffer) {
			final BasicUser user = new BasicUser();
			final int ret = user.readFromBuffer(Constants.ZERO, buffer);
			if (Constants.ZERO < ret) { // NOPMD
				info(LOGGER, user.toString());
				context.setUser(user);
				// 保存User到Session
				session(context);
			}
		}
	}

	private void session(final RoutingContext context) {
		final Session session = context.session();
		if (null != session) {
			User processed = null;
			final UserHolder holder = session.get(SESSION_USER_HOLDER_KEY);
			if (null == holder) {
				session.put(SESSION_USER_HOLDER_KEY, new UserHolder(context));
			} else {
				final RoutingContext prevCtx = holder.context;
				if (null != prevCtx) {		// NOPMD
					processed = prevCtx.user();
				} else if (null != holder.user) {
					processed = holder.user;
					processed.setAuthProvider(this.securitor.getProvider());
					holder.user = null;
				}
				holder.context = context;
			}
			if(null != processed){
				context.setUser(processed);
			}
		}
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
