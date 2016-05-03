package com.prayer.secure.basic;

import com.prayer.facade.engine.cv.SecKeys;
import com.prayer.resource.Resources;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AbstractUser;
import io.vertx.ext.auth.AuthProvider;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class BasicUser extends AbstractUser {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** User中需要使用的Provider对象引用 **/
    @SuppressWarnings("unused")
    private transient AuthProvider provider;
    /** 用户名信息 **/
    private transient String username;
    /** 用户Id信息 **/
    private transient String id;
    /** 用户的Token信息 **/
    private transient String token;
    /** 用户授权信息 **/
    private transient JsonObject principal;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public BasicUser() {
        this(null, new JsonObject());
    }

    /** **/
    public BasicUser(final AuthProvider provider, @NotNull final JsonObject data) {
        this.provider = provider;
        this.id = data.getString(SecKeys.User.Basic.ID);
        this.username = data.getString(SecKeys.Shared.USERNAME);
        this.token = data.getString(SecKeys.User.Basic.TOKEN);
        /** Principal授权信息 **/
        this.principal = new JsonObject();
        this.principal.mergeIn(data);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** 获取授权信息 **/
    @Override
    public JsonObject principal() {
        return this.principal;
    }

    /** 获取配置信息 **/
    @Override
    public void setAuthProvider(final AuthProvider provider) {
        this.provider = provider;
    }
    /** 权限检查 **/
    @Override
    public void doIsPermitted(final String permission, final Handler<AsyncResult<Boolean>> resultHandler) {
        
    }
    // ~ Vertx Cluster接口 ===================================
    /** **/
    @Override
    public void writeToBuffer(final Buffer buffer){
        /** 1.调用父类方法 **/
        super.writeToBuffer(buffer);
        /** 2.写入用户id **/
        byte[] bytes = id.getBytes(Resources.ENCODING);
        buffer.appendInt(bytes.length);
        buffer.appendBytes(bytes);
        /** 3.写入用户名 **/
        bytes = username.getBytes(Resources.ENCODING);
        buffer.appendInt(bytes.length);
        buffer.appendBytes(bytes);
        /** 4.写入Token **/
        bytes = token.getBytes(Resources.ENCODING);
        buffer.appendInt(bytes.length);
        buffer.appendBytes(bytes);
    }
    /** **/
    @Override
    public int readFromBuffer(int pos, final Buffer buffer){
        /** 1.从父类读取 **/
        pos = super.readFromBuffer(pos, buffer);
        /** 2.读取id **/
        int len = buffer.getInt(pos);
        pos += 4;
        byte[] bytes = buffer.getBytes(pos, pos + len);
        id = new String(bytes, Resources.ENCODING);
        pos += len;
        /** 3.读取用户名 **/
        len = buffer.getInt(pos);
        pos += 4;
        bytes = buffer.getBytes(pos, pos + len);
        username = new String(bytes,Resources.ENCODING);
        pos += len;
        /** 4.读取Token **/
        len = buffer.getInt(pos);
        pos += 4;
        bytes = buffer.getBytes(pos, pos + len);
        token = new String(bytes,Resources.ENCODING);
        pos += len;
        /** 5.返回 **/
        return pos;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
    /** **/
    @Override
    public String toString() {
        return "BasicUser [username=" + username + ", id=" + id + ", token=" + token + ", principal=" + principal + "]";
    }
}
