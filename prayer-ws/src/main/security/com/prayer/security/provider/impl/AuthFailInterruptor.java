package com.prayer.security.provider.impl;

import static com.prayer.util.debug.Log.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.model.web.JsonKey;
import com.prayer.model.web.StatusCode;
import com.prayer.security.provider.BasicProvider;
import com.prayer.util.StringKit;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.User;

/**
 * 
 * @author Lang
 *
 */
final class AuthFailInterruptor {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthFailInterruptor.class);
    /** **/
    private static final String AUE_USERNAME = "Passed parameter 'authInfo' must contain username in 'username' field";
    /** **/
    private static final String AUE_PASSWORD = "Passed parameter 'authInfo' must contain password in 'password' field";
    /** **/
    private static final String AUE_USER_INVALID = "User does not exist in database, please provide valid user.";
    /** **/
    private static final String AUE_AUTH_FAILURE = "Authentication failure, the password does not match.";

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 
     * @param data
     * @param handler
     * @return
     */
    public static boolean interruptParam(final JsonObject data, final Handler<AsyncResult<User>> handler) {
        final String username = data.getJsonObject(JsonKey.TOKEN.NAME).getString(JsonKey.TOKEN.USERNAME);
        if (StringKit.isNil(username)) {
            error(LOGGER, AUE_USERNAME);
            handleErrors(data, handler, BasicProvider.RET_M_USER);
            // errorHandler(data, resultHandler, WebLogger.AUE_USERNAME,
            // RET_M_USER);
            return false; // NOPMD
        }
        final String password = data.getJsonObject(JsonKey.TOKEN.NAME).getString(JsonKey.TOKEN.PASSWORD);
        if (StringKit.isNil(password)) {
            error(LOGGER, AUE_PASSWORD);
            handleErrors(data, handler, BasicProvider.RET_M_PWD);
            // errorHandler(data, resultHandler, WebLogger.AUE_PASSWORD,
            // RET_M_PWD);
            return false; // NOPMD
        }
        return true;
    }

    /**
     * 
     * @param data
     * @param handler
     * @param key
     */
    public static void handleErrors(final JsonObject data, final Handler<AsyncResult<User>> handler, final String key) {
        // Debug Only
        if (key.equals(BasicProvider.RET_M_INVALID)) {
            error(LOGGER, AUE_USER_INVALID);
        } else if (key.equals(BasicProvider.RET_I_USER_PWD)) {
            error(LOGGER, AUE_AUTH_FAILURE);
        }
        data.getJsonObject(JsonKey.RESPONSE.NAME).put(JsonKey.RESPONSE.RETURNCODE, ResponseCode.FAILURE);
        data.getJsonObject(JsonKey.RESPONSE.NAME).put(JsonKey.RESPONSE.STATUS, StatusCode.UNAUTHORIZED.status());
        data.getJsonObject(JsonKey.RESPONSE.NAME).put(JsonKey.RESPONSE.KEY, key);
        handler.handle(Future.failedFuture(data.encode()));
    }
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
