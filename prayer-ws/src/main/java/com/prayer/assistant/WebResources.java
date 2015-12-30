package com.prayer.assistant;

import com.prayer.util.PropertyKit;
import com.prayer.util.cv.Resources;

/**
 * 
 * @author Lang
 *
 */
public final class WebResources {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final PropertyKit LOADER = new PropertyKit(WebResources.class, Resources.WEB_CFG_FILE);
    /**
     * 【登录入口】Logout配置信息
     */
    public static final String WEB_ACT_LOGOUT;
    /**
     * 【登录主页】Login Page登录页面
     */
    public static final String WEB_ACT_LOGIN_PAGE;
    /**
     * 【动态配置】Dynamic Route
     */
    public static final String WEB_DYC_ROUTE;
    /**
     * 【动态配置】Dynamic Limitation
     */
    public static final String WEB_DYC_ROUTE_ADMIN;
    /**
     * 【静态配置】Static Route
     */
    public static final String WEB_STC_ROUTE;
    /**
     * 【静态配置】Static Favicon
     */
    public static final String WEB_STC_FAVICON;
    /**
     * 使用的模板引擎信息：Template Mode
     */
    public static final String WEB_TMP_MODE;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    /** **/
    static {

        WEB_ACT_LOGOUT = LOADER.getString("web.action.logout");

        WEB_ACT_LOGIN_PAGE = LOADER.getString("web.action.login.page");

        WEB_DYC_ROUTE = LOADER.getString("web.dynamic.route");

        WEB_DYC_ROUTE_ADMIN = LOADER.getString("web.dynamic.route.admin");

        WEB_STC_ROUTE = LOADER.getString("web.static.route");

        WEB_STC_FAVICON = LOADER.getString("web.static.favicon");

        WEB_TMP_MODE = LOADER.getString("web.template.mode");
    }

    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private WebResources() {
    }

    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
