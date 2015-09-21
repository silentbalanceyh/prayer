package com.prayer.security.provider.impl;

import com.prayer.constant.Resources;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AbstractUser;
import io.vertx.ext.auth.AuthProvider;

/**
 * 
 * @author Lang
 *
 */
public class BasicUser extends AbstractUser {

    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** User中需要使用的Provider对象 **/
    private transient AuthProvider provider;
    /** 用户名信息 **/
    private transient String username;
    /** 用户认证信息 **/
    private transient JsonObject principal; // NOPMD
    /** 角色Role信息 **/
    private transient String role;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public BasicUser() {
        this(null, null, null);
    }

    /** **/
    BasicUser(final String username, final AuthProvider provider, final String role) {
        super();
        this.username = username;
        this.provider = provider;
        this.role = role;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================

    /** 获取授权的身份信息 **/
    @Override
    public JsonObject principal() {
        if (null == principal) {
            principal = new JsonObject().put("username", this.username);
        }
        return principal;
    }

    /** 设置Provider信息 **/
    @Override
    public void setAuthProvider(final AuthProvider authProvider) {
        if (this.provider instanceof BasicAuthImpl) {
            this.provider = (BasicAuthImpl) provider;
        } else {
            throw new IllegalArgumentException("Not a BasicAuthImpl");
        }
    }

    /** 权限检查 **/
    @Override
    public void doIsPermitted(final String permission, final Handler<AsyncResult<Boolean>> resultHandler) {
        // TODO 等待权限系统设计完成
    }

    /** 将数据写入到Buffer中 **/
    @Override
    public void writeToBuffer(final Buffer buff) {
        super.writeToBuffer(buff);
        byte[] bytes = username.getBytes(Resources.SYS_ENCODING);
        buff.appendInt(bytes.length);
        buff.appendBytes(bytes);

        bytes = role.getBytes(Resources.SYS_ENCODING);
        buff.appendInt(bytes.length);
        buff.appendBytes(bytes);
    }

    /** 从Buffer中读取数据 **/
    @Override
    public int readFromBuffer(int pos, final Buffer buffer) { // NOPMD
        pos = super.readFromBuffer(pos, buffer);
        int len = buffer.getInt(pos);
        pos += 4;
        byte[] bytes = buffer.getBytes(pos, pos + len);
        username = new String(bytes, Resources.SYS_ENCODING);
        pos += len;

        len = buffer.getInt(pos);
        pos += 4;
        bytes = buffer.getBytes(pos, pos + len);
        role = new String(bytes, Resources.SYS_ENCODING);
        pos += len;
        return pos;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
