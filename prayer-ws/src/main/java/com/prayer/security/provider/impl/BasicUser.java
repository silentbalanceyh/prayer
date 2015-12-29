package com.prayer.security.provider.impl;

import com.prayer.security.provider.BasicProvider;
import com.prayer.util.cv.Resources;

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
    /** 用户的ID信息 **/
    private transient String userId;
    /** 用户的认证头 **/
    private transient String token;
    /** 用户认证信息 **/
    private transient JsonObject principal; // NOPMD
    /** 角色Role信息 **/
    private transient String role;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public BasicUser() {
        this(new JsonObject("{}"), null, null);
    }

    /** **/
    BasicUser(final JsonObject data, final AuthProvider provider, final String role) {
        super();
        this.userId = data.getString("id");
        this.username = data.getString("username");
        this.token = data.getString(BasicProvider.KEY_TOKEN);
        this.provider = provider;
        this.role = role;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================

    /** 获取授权的身份信息 **/
    @Override
    public JsonObject principal() {
        if (null == principal) {
            principal = new JsonObject().put("id", this.userId).put("username", this.username)
                    .put(BasicProvider.KEY_TOKEN, this.token).put("role", this.role);
        }
        return principal;
    }

    /** 设置Provider信息 **/
    @Override
    public void setAuthProvider(final AuthProvider authProvider) {
        if (this.provider instanceof BasicProviderImpl) {
            this.provider = (BasicProviderImpl) provider;
        } else {
            throw new IllegalArgumentException("Not a BasicProviderImpl");
        }
    }

    /** 权限检查 **/
    @Override
    public void doIsPermitted(final String permission, final Handler<AsyncResult<Boolean>> resultHandler) {
        
    }

    /** 将数据写入到Buffer中 **/
    @Override
    public void writeToBuffer(final Buffer buff) {
        super.writeToBuffer(buff);
        byte[] bytes = userId.getBytes(Resources.SYS_ENCODING);
        buff.appendInt(bytes.length);
        buff.appendBytes(bytes);

        bytes = username.getBytes(Resources.SYS_ENCODING);
        buff.appendInt(bytes.length);
        buff.appendBytes(bytes);

        bytes = token.getBytes(Resources.SYS_ENCODING);
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
        userId = new String(bytes, Resources.SYS_ENCODING);
        pos += len;

        len = buffer.getInt(pos);
        pos += 4;
        bytes = buffer.getBytes(pos, pos + len);
        username = new String(bytes, Resources.SYS_ENCODING);
        pos += len;

        len = buffer.getInt(pos);
        pos += 4;
        bytes = buffer.getBytes(pos, pos + len);
        token = new String(bytes, Resources.SYS_ENCODING);
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
