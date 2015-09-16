package com.prayer.security.provider.impl;

import static com.prayer.uca.assistant.WebLogger.info;
import static com.prayer.util.Instance.singleton;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.bus.security.BasicAuthService;
import com.prayer.bus.security.impl.BasicAuthSevImpl;
import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.model.bus.ServiceResult;
import com.prayer.security.provider.BasicAuth;
import com.prayer.uca.assistant.WebLogger;
import com.prayer.util.StringKit;
import com.prayer.vx.configurator.SecurityConfigurator;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.auth.User;
import jodd.util.StringUtil;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 * 
 */
@Guarded
public class BasicAuthImpl implements AuthProvider, BasicAuth {
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(BasicAuthImpl.class);
	// ~ Instance Fields =====================================
	/** 配置程序 **/
	private transient final SecurityConfigurator configurator;
	/** 安全认证业务接口 **/
	private transient final BasicAuthService service;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	public BasicAuthImpl() {
		this.configurator = singleton(SecurityConfigurator.class);
		this.service = singleton(BasicAuthSevImpl.class);
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/**
	 * 
	 */
	@Override
	public void authenticate(@NotNull final JsonObject authInfo,
			@NotNull final Handler<AsyncResult<User>> resultHandler) {
		// 1.检查用户名和密码
		this.interruptParam(authInfo, resultHandler);
		// 2.读取配置参数
		final JsonObject params = this.wrapperParam(authInfo);
		// 3.从系统中读取用户信息
		final ServiceResult<JsonObject> ret = this.service.find(params);
		// 4.判断响应信息
		if (ResponseCode.SUCCESS == ret.getResponseCode()) {
			final JsonObject retObj = ret.getResult();
			final String inputPWD = authInfo.getString("password");
			final String storedPWD = retObj.getString(this.configurator.getSecurityOptions().getString(DFT_PWD));
			if (StringUtil.equals(inputPWD, storedPWD)) {
				final String username = retObj
						.getString(this.configurator.getSecurityOptions().getString(DFT_ACCOUNT_ID));
				resultHandler.handle(Future.succeededFuture(new BasicUser(username, this, "role")));
			} else {
				resultHandler.handle(Future.failedFuture(RET_I_USER_PWD));
				return; // NOPMD
			}
		} else {
			info(LOGGER, WebLogger.AUE_USER_INVALID);
			resultHandler.handle(Future.failedFuture(RET_M_INVALID));
			return;
		}
	}

	// ~ Methods =============================================
	// ~ Private Methods =====================================
	private void interruptParam(final JsonObject authInfo, final Handler<AsyncResult<User>> resultHandler) {
		final String username = authInfo.getString("username");
		if (StringKit.isNil(username)) {
			resultHandler.handle(Future.failedFuture(RET_M_USER));
			info(LOGGER, WebLogger.AUE_USERNAME);
			return; // NOPMD
		}
		final String password = authInfo.getString("password");
		if (StringKit.isNil(password)) {
			resultHandler.handle(Future.failedFuture(RET_M_PWD));
			info(LOGGER, WebLogger.AUE_PASSWORD);
			return;
		}
	}

	private JsonObject wrapperParam(final JsonObject authInfo) {
		final JsonObject options = this.configurator.getSecurityOptions();
		final JsonObject wrapper = new JsonObject();
		wrapper.put(Constants.PARAM_ID, options.getString(DFT_SCHEMA_ID));
		wrapper.put(Constants.PARAM_SCRIPT, options.getString(DFT_SCRIPT_NAME));
		{
			final JsonObject extension = authInfo.getJsonObject(DFT_EXTENSION);
			wrapper.put(Constants.PARAM_METHOD, extension.getString(Constants.PARAM_METHOD));
			wrapper.put(Constants.PARAM_SESSION, extension.getString(Constants.PARAM_SESSION));
			wrapper.put(Constants.PARAM_FILTERS, new ArrayList<>());
			// 从AuthInfo中移除Extension
			authInfo.remove(DFT_EXTENSION);
		}
		wrapper.put(Constants.PARAM_DATA, authInfo);
		return wrapper;

	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
