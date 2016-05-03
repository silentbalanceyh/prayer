package com.prayer.secure.basic;

import static com.prayer.util.reflection.Instance.singleton;

import java.io.Serializable;

import com.prayer.constant.SystemEnum.Credential;
import com.prayer.constant.SystemEnum.SecureMode;
import com.prayer.exception.web._401AuthHeaderInvalidException;
import com.prayer.exception.web._401AuthHeaderMissingException;
import com.prayer.exception.web._401CredentialInvalidException;
import com.prayer.exception.web._401PasswordPlainTextException;
import com.prayer.exception.web._401SchemaWrongException;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.constant.Symbol;
import com.prayer.facade.engine.cv.SecKeys;
import com.prayer.facade.util.EDCoder;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.resource.Resources;
import com.prayer.secure.util.IdResolver;
import com.prayer.util.coder.Base64Coder;
import com.prayer.util.string.StringKit;

import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class BasicToken implements Serializable {
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
    public static BasicToken create(@NotNull final HttpServerRequest request) {
        return new BasicToken(request);
    }
    // ~ Constructors ========================================

    private BasicToken(final HttpServerRequest request) {
        /** 1.根据Authorization头初始化Token的信息 **/
        final MultiMap map = request.headers();
        if (map.contains(HttpHeaders.AUTHORIZATION)) {
            final String auth = map.get(HttpHeaders.AUTHORIZATION);
            if (StringKit.isNil(auth)) {
                this.error = new _401AuthHeaderMissingException(getClass(), request.path());
            } else {
                /** 解析Header/Token **/
                this.parseHeader(auth);
            }
        } else {
            this.error = new _401AuthHeaderMissingException(getClass(), request.path());
        }
    }

    private void parseHeader(final String authHeader) {
        final String[] authArr = authHeader.split(String.valueOf(Symbol.SPACE));
        if (Constants.TWO == authArr.length) {
            this.schema = authArr[Constants.IDX];
            /** 解析Token **/
            if (StringKit.isNil(schema) || !schema.equalsIgnoreCase(SecureMode.BASIC.name())) {
                this.error = new _401SchemaWrongException(getClass(), this.schema, SecureMode.BASIC.name());
            } else {
                this.parseToken(authArr[Constants.ONE]);
            }
        } else {
            this.error = new _401AuthHeaderInvalidException(getClass(), SecureMode.BASIC);
        }
    }

    private void parseToken(final String token) {
        final EDCoder coder = singleton(Base64Coder.class);
        final String[] credential = coder.decode(token).split(String.valueOf(Symbol.COLON));
        if (Constants.TWO == credential.length) {
            if (StringKit.isNil(credential[Constants.IDX]) || StringKit.isNil(credential[Constants.ONE])) {
                this.error = new _401CredentialInvalidException(getClass(), token);
            } else if (32 != credential[Constants.ONE].length()) {
                this.error = new _401PasswordPlainTextException(getClass(), credential[Constants.ONE]);
            } else {
                /** 抽取数据 **/
                this.username = credential[Constants.IDX];
                this.password = credential[Constants.ONE];
                this.token = token;
                this.realm = Resources.Security.REALM;
            }
        } else {
            this.error = new _401CredentialInvalidException(getClass(), token);
        }
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 当前Token是否获取到，不验证Valid
     * 
     * @return
     */
    public boolean obtained() {
        return null == this.error;
    }

    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    /**
     * 
     * @return
     */
    public AbstractException getError() {
        return this.error;
    }

    /**
     * 
     * @return
     */
    public JsonObject getCredential() {
        final JsonObject credential = new JsonObject();
        /** 1.填充Realm和Password **/
        credential.put(SecKeys.User.Basic.PASSWORD, this.password);
        credential.put(SecKeys.REALM, this.realm);
        /** 2.计算Username **/
        final Credential type = IdResolver.resolve(this.username);
        credential.put(SecKeys.Shared.TYPE, type.name());
        /** 3.根据登录结果放不同的登录Id **/
        if (Credential.MOBILE == type) {
            credential.put(SecKeys.Shared.MOBILE, this.username);
        } else if (Credential.EMAIL == type) {
            credential.put(SecKeys.Shared.EMAIL, this.username);
        } else {
            credential.put(SecKeys.Shared.USERNAME, this.username);
        }
        return credential;
    }

    // ~ hashCode,equals,toString ============================
    /** **/
    @Override
    public String toString() {
        return "BasicToken [schema=" + schema + ", username=" + username + ", password=" + password + ", realm=" + realm
                + ", token=" + token + "]";
    }
}
