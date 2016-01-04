package com.prayer.security.provider;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.ext.auth.AuthProvider;

/**
 * 
 * @author Lang
 *
 */
@VertxGen
public interface BasicProvider extends AuthProvider { // NOPMD

    // ----------------------------响应结果信息 --------------------------------
    /** username参数丢失 **/
    String RET_M_USER = "${USERNAME.MISSING}";
    /** password参数丢失 **/
    String RET_M_PWD = "${PASSWORD.MISSING}";
    /** user不存在 **/
    String RET_M_INVALID = "${USER.NOT.FOUND}";
    /** 用户名和密码不匹配 **/
    String RET_I_USER_PWD = "${AUTH.FAILURE}";
    /** Shared User **/
    String KEY_POOL_USER = "SHARED.USER";
    /** User ID **/
    String KEY_USER_ID = "UID";
    /** TOKEN **/
    String KEY_TOKEN = "token";
}
