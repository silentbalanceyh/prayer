package com.prayer.secure.opts;

import com.prayer.constant.SystemEnum.SecureMode;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public class SecureOptions {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** Security认证中对应的Realm信息 **/
    private transient String realm;
    /** 用户ID标识符 **/
    private transient JsonObject user;
    /** Schema id **/
    private transient String identifier;
    /** 第三方Options **/
    private transient TpOptions options;
    /** 安全Mode **/
    private transient SecureMode mode;
    /** 安全Options **/
    private transient JsonObject config;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    /**
     * @return the identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * @param identifier
     *            the identifier to set
     */
    public void setIdentifier(final String identifier) {
        this.identifier = identifier;
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
     * 
     * @return
     */
    public JsonObject getUser() {
        return user;
    }

    /**
     * 
     * @param user
     */
    public void setUser(final JsonObject user) {
        this.user = user;
    }

    /**
     * @return the options
     */
    public TpOptions getOptions() {
        return options;
    }

    /**
     * @param options
     *            the options to set
     */
    public void setOptions(final TpOptions options) {
        this.options = options;
    }

    /**
     * @return the mode
     */
    public SecureMode getMode() {
        return mode;
    }

    /**
     * @param mode
     *            the mode to set
     */
    public void setMode(final SecureMode mode) {
        this.mode = mode;
    }

    /**
     * @return the config
     */
    public JsonObject getConfig() {
        return config;
    }

    /**
     * @param config
     *            the config to set
     */
    public void setConfig(final JsonObject config) {
        this.config = config;
    }
    // ~ hashCode,equals,toString ============================

}
