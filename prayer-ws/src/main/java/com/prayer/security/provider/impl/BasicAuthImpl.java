package com.prayer.security.provider.impl;

import static com.prayer.assistant.WebLogger.error;
import static com.prayer.assistant.WebLogger.info;
import static com.prayer.util.Instance.singleton;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.assistant.WebLogger;
import com.prayer.bus.security.BasicAuthService;
import com.prayer.bus.security.impl.BasicAuthSevImpl;
import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.model.bus.ServiceResult;
import com.prayer.security.provider.AuthConstants;
import com.prayer.security.provider.BasicAuth;
import com.prayer.util.StringKit;
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
public class BasicAuthImpl implements AuthProvider, BasicAuth, AuthConstants.BASIC {
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
        try {
            // 1.检查用户名和密码
            if (!this.interruptParam(authInfo, resultHandler)) {
                return; // NOPMD
            }
            // 2.读取配置参数
            final JsonObject params = this.wrapperParam(authInfo);
            // 3.从系统中读取用户信息
            final ServiceResult<JsonArray> ret = this.service.find(params);

            // 4.判断响应信息
            if (ResponseCode.SUCCESS == ret.getResponseCode() && null != ret.getResult()
                    && Constants.ONE == ret.getResult().size()) {
                final JsonObject retObj = ret.getResult().getJsonObject(0);
                final String inputPWD = authInfo.getString("password");
                final String storedPWD = retObj.getString(this.configurator.getSecurityOptions().getString(PWD));
                if (StringUtil.equals(inputPWD, storedPWD)) {
                    final String username = retObj
                            .getString(this.configurator.getSecurityOptions().getString(ACCOUNT_ID));
                    info(LOGGER, WebLogger.I_COMMON_INFO, retObj.encode());
                    {
                        // Fix 客户端跨域问题，二次登录问题，保存数据问题，重新填充Extension
                        final JsonObject extension = new JsonObject();
                        extension.put(KEY_USER_ID, retObj.getString("uniqueId"));
                        extension.put(LOGIN_URL, this.configurator.getSecurityOptions().getString(LOGIN_URL));
                        extension.put(Constants.PARAM.DATA, retObj);
                        authInfo.put(EXTENSION, extension);
                    }

                    resultHandler.handle(Future
                            .succeededFuture(new BasicUser(retObj.getString("uniqueId"), username, this, "role")));
                } else {
                    errorHandler(authInfo, resultHandler, WebLogger.AUE_AUTH_FAILURE, RET_I_USER_PWD);
                    return; // NOPMD
                }
            } else {
                errorHandler(authInfo, resultHandler, WebLogger.AUE_USER_INVALID, RET_M_INVALID);
                return;
            }
        }
        // TODO
        catch (Exception ex) { // NOPMD
            ex.printStackTrace(); // NOPMD
        }
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private boolean interruptParam(final JsonObject authInfo, final Handler<AsyncResult<User>> resultHandler) {
        final String username = authInfo.getString("username");
        if (StringKit.isNil(username)) {
            errorHandler(authInfo, resultHandler, WebLogger.AUE_USERNAME, RET_M_USER);
            return false; // NOPMD
        }
        final String password = authInfo.getString("password");
        if (StringKit.isNil(password)) {
            errorHandler(authInfo, resultHandler, WebLogger.AUE_PASSWORD, RET_M_PWD);
            return false; // NOPMD
        }
        return true;
    }

    private void errorHandler(final JsonObject authInfo, final Handler<AsyncResult<User>> resultHandler,
            final String loggerKey, final String authRet) {
        authInfo.put(Constants.RET.AUTH_ERROR, authRet);
        error(LOGGER, loggerKey);
        resultHandler.handle(Future.failedFuture(authRet));
    }

    private JsonObject wrapperParam(final JsonObject authInfo) {
        final JsonObject options = this.configurator.getSecurityOptions();
        final JsonObject wrapper = new JsonObject();
        wrapper.put(Constants.PARAM.ID, options.getString(SCHEMA_ID));
        wrapper.put(Constants.PARAM.SCRIPT, options.getString(SCRIPT_NAME));
        {
            final JsonObject extension = authInfo.getJsonObject(EXTENSION);
            wrapper.put(Constants.PARAM.METHOD, extension.getString(Constants.PARAM.METHOD));
            wrapper.put(Constants.PARAM.SESSION, extension.getString(Constants.PARAM.SESSION));
            wrapper.put(Constants.PARAM.FILTERS, new ArrayList<>());
            // 从AuthInfo中移除Extension
            authInfo.remove(EXTENSION);
        }
        wrapper.put(Constants.PARAM.DATA, authInfo);
        return wrapper;

    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
