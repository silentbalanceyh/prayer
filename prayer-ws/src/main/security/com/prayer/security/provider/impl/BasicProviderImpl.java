package com.prayer.security.provider.impl;

import static com.prayer.util.debug.Log.jvmError;
import static com.prayer.util.reflection.Instance.singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.business.impl.std.BasicAuthSevImpl;
import com.prayer.configurator.SecurityConfigurator;
import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.facade.business.BasicAuthService;
import com.prayer.model.business.ServiceResult;
import com.prayer.model.web.JsonKey;
import com.prayer.security.AuthConstants.BASIC;
import com.prayer.security.provider.BasicProvider;

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
public class BasicProviderImpl implements AuthProvider, BasicProvider {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(BasicProviderImpl.class);
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
    public BasicProviderImpl() {
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
            if (!AuthFailInterruptor.interruptParam(data, handler)) {
                return; // NOPMD
            }
            // 2.读取配置参数
            this.prepareParams(data);
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
                // 6.输入密码和存储密码对比，成功的时候执行该操作
                if (StringUtil.equals(inputPWD, storedPWD)) {
                    final String username = retObj.getString(basicOpts.getString(BASIC.ACCOUNT_ID));
                    data.getJsonObject(JsonKey.RESPONSE.NAME).put(Constants.PARAM.DATA, retObj);
                    data.getJsonObject(JsonKey.REQUEST.NAME).put(JsonKey.REQUEST.LOGIN_URL,
                            basicOpts.getString(BASIC.LOGIN_URL));
                    // 用户ID注入到Token
                    data.getJsonObject(JsonKey.TOKEN.NAME).put(JsonKey.TOKEN.USERNAME, username);
                    data.getJsonObject(JsonKey.TOKEN.NAME).put(JsonKey.TOKEN.ID, retObj.getString(Constants.PID));
                    // TODO: Roles Null Pointer
                    data.getJsonObject(JsonKey.TOKEN.NAME).put(JsonKey.TOKEN.ROLE, "Roles");
                    // 构建用户信息
                    handler.handle(Future.succeededFuture(this.buildUserData(this, data)));
                } else {
                    AuthFailInterruptor.handleErrors(data, handler, RET_I_USER_PWD);
                    return; // NOPMD
                }
            } else {
                AuthFailInterruptor.handleErrors(data, handler, RET_M_INVALID);
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

    private void prepareParams(final JsonObject data) {
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
