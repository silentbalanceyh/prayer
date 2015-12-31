package com.prayer.configurator;

import static com.prayer.util.Converter.fromStr;
import static com.prayer.util.Instance.instance;
import static com.prayer.util.debug.Log.peError;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.exception.AbstractWebException;
import com.prayer.constant.Resources;
import com.prayer.constant.Symbol;
import com.prayer.constant.SystemEnum.SecurityMode;
import com.prayer.security.AuthConstants;
import com.prayer.util.io.PropertyKit;
import com.prayer.util.web.Interruptor;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.web.handler.CorsHandler;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class SecurityConfigurator {
    // ~ Static Fields =======================================
    /** Provider Implementation Key **/
    private static final String PROVIDER = "provider.impl";
    // ~ Instance Fields =====================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfigurator.class);
    /** Server Config File **/
    private transient final PropertyKit LOADER = new PropertyKit(SecurityConfigurator.class, Resources.SEC_CFG_FILE);
    /** **/
    @NotNull
    private transient final SecurityMode mode;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public SecurityConfigurator() {
        this.mode = fromStr(SecurityMode.class, this.LOADER.getString("server.security.mode"));
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================

    /**
     * 获取Provider引用
     * 
     * @return
     */
    public AuthProvider getProvider() {
        AuthProvider provider = null;
        try {
            final String providerCls = this.getSecurityOptions().getString(PROVIDER);
            Interruptor.interruptClass(getClass(), providerCls, "AuthProvider");
            Interruptor.interruptImplements(getClass(), providerCls, AuthProvider.class);
            provider = instance(providerCls);
        } catch (AbstractWebException ex) {
            peError(LOGGER, ex);
        }
        return provider;
    }

    /**
     * 
     * @return
     */
    public CorsHandler getCorsHandler() {
        final CorsHandler handler = CorsHandler.create(this.LOADER.getString("server.security.cors.origin"));
        handler.allowCredentials(this.LOADER.getBoolean("server.security.cors.credentials"));
        // Headers
        final String[] headers = this.LOADER.getArray("server.security.cors.headers");
        final Set<String> headerSet = new HashSet<>(Arrays.asList(headers));
        handler.allowedHeaders(headerSet);
        // Methods
        final Set<HttpMethod> methodSet = new HashSet<>();
        final String[] methods = this.LOADER.getArray("server.security.cors.methods");
        for (final String method : methods) {
            methodSet.add(fromStr(HttpMethod.class, method));
        }
        handler.allowedMethods(methodSet);
        return handler;
    }

    /**
     * Security Mode
     * 
     * @return
     */
    public SecurityMode getMode() {
        return this.mode;
    }

    /**
     * 获取Security的配置信息
     * 
     * @return
     */
    public JsonObject getSecurityOptions() {
        JsonObject options = new JsonObject();
        switch (this.mode) {
        case BASIC:
            options = getBasicOptions();
            break;
        case DIGEST:
            options = getDigestOptions();
            break;
        case OAUTH2:
            options = getOAuth2Options();
            break;
        default:
            break;
        }
        return options;
    }

    // ~ Private Methods =====================================

    /** Basic Options **/
    private JsonObject getBasicOptions() {
        final JsonObject options = new JsonObject();
        final String prefix = SecurityMode.BASIC.toString();
        // 固定属性
        options.put(PROVIDER, this.LOADER.getString(prefix + Symbol.DOT + PROVIDER));
        options.put(AuthConstants.BASIC.REALM, this.LOADER.getString(prefix + Symbol.DOT + AuthConstants.BASIC.REALM));
        options.put(AuthConstants.BASIC.SCRIPT_NAME,
                this.LOADER.getString(prefix + Symbol.DOT + AuthConstants.BASIC.SCRIPT_NAME));
        options.put(AuthConstants.BASIC.ACCOUNT_ID,
                this.LOADER.getString(prefix + Symbol.DOT + AuthConstants.BASIC.ACCOUNT_ID));
        options.put(AuthConstants.BASIC.EMAIL, this.LOADER.getString(prefix + Symbol.DOT + AuthConstants.BASIC.EMAIL));
        options.put(AuthConstants.BASIC.MOBILE,
                this.LOADER.getString(prefix + Symbol.DOT + AuthConstants.BASIC.MOBILE));
        options.put(AuthConstants.BASIC.PWD, this.LOADER.getString(prefix + Symbol.DOT + AuthConstants.BASIC.PWD));
        // 可定义的动态属性
        options.put(AuthConstants.BASIC.SCHEMA_ID,
                this.LOADER.getString(prefix + Symbol.DOT + AuthConstants.BASIC.SCHEMA_ID));
        options.put(AuthConstants.BASIC.LOGIN_URL,
                this.LOADER.getString(prefix + Symbol.DOT + AuthConstants.BASIC.LOGIN_URL));
        // 角色相关属性
        options.put(AuthConstants.BASIC.ROLE_USER_CODE,
                this.LOADER.getString(prefix + Symbol.DOT + AuthConstants.BASIC.ROLE_USER_CODE));
        return options;
    }

    /** Digest Options **/
    private JsonObject getDigestOptions() {
        return null;
    }

    /** OAuth2 Options **/
    private JsonObject getOAuth2Options() {
        return null;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
