package com.prayer.security.provider.impl;

import static com.prayer.util.Instance.singleton;
import static com.prayer.util.Log.error;
import static com.prayer.util.Log.jvmError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.bus.impl.std.BasicAuthSevImpl;
import com.prayer.facade.bus.BasicAuthService;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.web.JsonKey;
import com.prayer.model.web.StatusCode;
import com.prayer.security.provider.AuthConstants.BASIC;
import com.prayer.security.provider.BasicAuth;
import com.prayer.util.StringKit;
import com.prayer.util.cv.Constants;
import com.prayer.util.cv.SystemEnum.ResponseCode;
import com.prayer.vx.configurator.SecurityConfigurator;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
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

    /** **/
    private static final String AUE_USERNAME = "Passed parameter 'authInfo' must contain username in 'username' field";
    /** **/
    private static final String AUE_PASSWORD = "Passed parameter 'authInfo' must contain password in 'password' field";
    /** **/
    private static final String AUE_USER_INVALID = "User does not exist in database, please provide valid user.";
    /** **/
    private static final String AUE_AUTH_FAILURE = "Authentication failure, the password does not match.";
    // ~ Instance Fields =====================================
    /** 配置程序 **/
    @NotNull
    private transient final SecurityConfigurator configurator;
    /** 安全认证业务接口 **/
    @NotNull
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
    public void authenticate(@NotNull final JsonObject data, @NotNull final Handler<AsyncResult<User>> handler) {
        try {
            // 1.检查用户名和密码
            if (!this.interruptParam(data, handler)) {
                return; // NOPMD
            }
            // 2.读取配置参数
            this.completeParam(data);
            // 3.从系统中读取用户信息
            final ServiceResult<JsonArray> ret = this.service.find(data.getJsonObject(JsonKey.PARAMS.NAME));

            // 4.判断响应信息
            if (ResponseCode.SUCCESS == ret.getResponseCode() && null != ret.getResult()
                    && Constants.ONE == ret.getResult().size()) {
                // 5.从结果中拿到User数据
                final JsonObject retObj = ret.getResult().getJsonObject(Constants.ZERO);
                final JsonObject basicOpts = this.configurator.getSecurityOptions();
                final String inputPWD = data.getJsonObject(JsonKey.TOKEN.NAME).getString(JsonKey.TOKEN.PASSWORD);
                final String storedPWD = retObj.getString(basicOpts.getString(BASIC.PWD));
                // 6.密码比对
                if (StringUtil.equals(inputPWD, storedPWD)) {
                    final String username = retObj.getString(basicOpts.getString(BASIC.ACCOUNT_ID));
                    final String roles = retObj.getString(basicOpts.getString(BASIC.ROLE_USER_CODE));
                    data.getJsonObject(JsonKey.RESPONSE.NAME).put(Constants.PARAM.DATA, retObj);
                    data.getJsonObject(JsonKey.REQUEST.NAME).put(JsonKey.REQUEST.LOGIN_URL,
                            basicOpts.getString(BASIC.LOGIN_URL));
                    // 用户ID注入到Token
                    data.getJsonObject(JsonKey.TOKEN.NAME).put(JsonKey.TOKEN.USERNAME, username);
                    data.getJsonObject(JsonKey.TOKEN.NAME).put(JsonKey.TOKEN.ID, retObj.getString(Constants.PID));
                    data.getJsonObject(JsonKey.TOKEN.NAME).put(JsonKey.TOKEN.ROLE, roles);
                    // 构建用户信息
                    handler.handle(Future.succeededFuture(this.buildUserData(this, data)));
                } else {
                    error(LOGGER, AUE_AUTH_FAILURE);
                    errorHandler(data, handler, RET_I_USER_PWD);
                    return; // NOPMD
                }
            } else {
                error(LOGGER, AUE_USER_INVALID);
                errorHandler(data, handler, RET_M_INVALID);
                return;
            }
        } catch (Exception ex) { // NOPMD
            jvmError(LOGGER, ex);
        }
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private BasicUser buildUserData(final AuthProvider provider, final JsonObject data) {
        final JsonObject user = new JsonObject();
        user.put(JsonKey.TOKEN.ID, data.getJsonObject(JsonKey.TOKEN.NAME).getString(JsonKey.TOKEN.ID));
        user.put(JsonKey.TOKEN.USERNAME, data.getJsonObject(JsonKey.TOKEN.NAME).getString(JsonKey.TOKEN.USERNAME));
        user.put(JsonKey.TOKEN.NAME, data.getJsonObject(JsonKey.REQUEST.NAME).getString(JsonKey.REQUEST.AUTHORIZATION));
        return new BasicUser(user, provider, data.getJsonObject(JsonKey.TOKEN.NAME).getString(JsonKey.TOKEN.ROLE));
    }

    private boolean interruptParam(final JsonObject data, final Handler<AsyncResult<User>> handler) {
        final String username = data.getJsonObject(JsonKey.TOKEN.NAME).getString(JsonKey.TOKEN.USERNAME);
        if (StringKit.isNil(username)) {
            error(LOGGER, AUE_USERNAME);
            errorHandler(data, handler, RET_M_USER);
            // errorHandler(data, resultHandler, WebLogger.AUE_USERNAME,
            // RET_M_USER);
            return false; // NOPMD
        }
        final String password = data.getJsonObject(JsonKey.TOKEN.NAME).getString(JsonKey.TOKEN.PASSWORD);
        if (StringKit.isNil(password)) {
            error(LOGGER, AUE_PASSWORD);
            errorHandler(data, handler, RET_M_PWD);
            // errorHandler(data, resultHandler, WebLogger.AUE_PASSWORD,
            // RET_M_PWD);
            return false; // NOPMD
        }
        return true;
    }

    private void errorHandler(final JsonObject data, final Handler<AsyncResult<User>> handler, final String key) {
        data.getJsonObject(JsonKey.RESPONSE.NAME).put(JsonKey.RESPONSE.RETURNCODE, ResponseCode.FAILURE);
        data.getJsonObject(JsonKey.RESPONSE.NAME).put(JsonKey.RESPONSE.STATUS, StatusCode.UNAUTHORIZED.status());
        data.getJsonObject(JsonKey.RESPONSE.NAME).put(JsonKey.RESPONSE.KEY, key);
        handler.handle(Future.failedFuture(data.encode()));
    }

    private void completeParam(final JsonObject data) {
        final JsonObject options = this.configurator.getSecurityOptions();
        data.getJsonObject(JsonKey.PARAMS.NAME).put(JsonKey.PARAMS.IDENTIFIER, options.getString(BASIC.SCHEMA_ID));
        data.getJsonObject(JsonKey.PARAMS.NAME).put(JsonKey.PARAMS.SCRIPT, options.getString(BASIC.SCRIPT_NAME));
        // 删除掉不必要的参数
        final JsonObject service = data.getJsonObject(JsonKey.TOKEN.NAME).copy();
        service.remove(JsonKey.TOKEN.SCHEMA);
        service.remove(JsonKey.TOKEN.PASSWORD);
        data.getJsonObject(JsonKey.PARAMS.NAME).put(JsonKey.PARAMS.DATA, service);
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
