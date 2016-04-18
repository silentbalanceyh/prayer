package com.prayer.model.web.configuration;

import com.prayer.constant.Resources;

import io.vertx.core.json.JsonObject;

/**
 * 元数据配置项模型
 * 
 * @author Lang
 *
 */
// 1.SQL：使用H2
// 2.NOSQL：使用Radis
public class HubOptions {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 1.元数据Server的端口号 **/
    private transient Integer port;
    /** 2.元数据Server所在的Host **/
    private transient String host;
    /** 3.元数据Server的模式：SQL/NOSQL两种 **/
    private transient final String mode;
    /** 4.元数据Server访问用的用户名 **/
    private transient String username;
    /** 5.元数据Server的密码 **/
    private transient String password;
    /** 6.元数据Server的配置信息 **/
    private transient JsonObject config;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     */
    public HubOptions() {
        /** 1.元数据的模式读取 **/
        this.mode = Resources.META_MODE;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    /**
     * @return the port
     */
    public Integer getPort() {
        return port;
    }

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @return the mode
     */
    public String getMode() {
        return mode;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return the config
     */
    public JsonObject getConfig() {
        return config;
    }

    /**
     * @param port
     *            the port to set
     */
    public void setPort(final Integer port) {
        this.port = port;
    }

    /**
     * @param host
     *            the host to set
     */
    public void setHost(final String host) {
        this.host = host;
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(final String password) {
        this.password = password;
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
