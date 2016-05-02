package com.prayer.secure.model;

import java.io.Serializable;

import com.prayer.exception.web._401AuthHeaderMissingException;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.constant.Symbol;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.util.string.StringKit;

import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerRequest;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class Token implements Serializable {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = -1960576904596934431L;
    // ~ Instance Fields =====================================
    /** 头部信息 **/
    private transient String schema;
    /** 用户名 **/
    private transient String username;
    /** 密码信息 **/
    private transient String password;
    /** 域信息 **/
    private transient String realm;
    /** 令牌信息 **/
    private transient String token;
    /** 错误信息 **/
    private transient AbstractException error;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /** **/
    public static Token create(@NotNull final HttpServerRequest request) {
        return new Token(request);
    }
    // ~ Constructors ========================================

    private Token(final HttpServerRequest request) {
        /** 1.根据Authorization头初始化Token的信息 **/
        final MultiMap map = request.headers();
        if (map.contains(HttpHeaders.AUTHORIZATION)) {
            final String auth = map.get(HttpHeaders.AUTHORIZATION);
            if (StringKit.isNil(auth)) {
                this.error = new _401AuthHeaderMissingException(getClass(), request.path());
            } else {
                final String[] authArr = auth.split(String.valueOf(Symbol.SPACE));
                if (Constants.TWO == authArr.length) {
                    this.schema = authArr[Constants.IDX];
                    this.token = authArr[Constants.ONE];
                }
            }
        } else {
            this.error = new _401AuthHeaderMissingException(getClass(), request.path());
        }
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 当前Token是否获取到，不验证Valid
     * @return
     */
    public boolean obtained(){
        return null == this.error;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    /**
     * 
     * @return
     */
    public AbstractException getError(){
        return this.error;
    }
    /**
     * @return the schema
     */
    public String getSchema() {
        return schema;
    }

    /**
     * @param schema
     *            the schema to set
     */
    public void setSchema(final String schema) {
        this.schema = schema;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * @return the realm
     */
    public String getRealm() {
        return realm;
    }

    /**
     * @param realm
     *            the realm to set
     */
    public void setRealm(final String realm) {
        this.realm = realm;
    }

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token
     *            the token to set
     */
    public void setToken(final String token) {
        this.token = token;
    }

    // ~ hashCode,equals,toString ============================
    /** **/
    @Override
    public String toString() {
        return "Token [schema=" + schema + ", username=" + username + ", password=" + password + ", realm=" + realm
                + ", token=" + token + "]";
    }
}
