package com.prayer.util.inceptor;

import com.prayer.facade.resource.Inceptor;
import com.prayer.facade.resource.Point;
import com.prayer.resource.InceptBus;

/**
 * 
 * @author Lang
 *
 */
public class InceptorTestCase {
    // ~ Static Fields =======================================

    /** Web Inceptor **/
    private static final Inceptor INCEPTOR = InceptBus.build(Point.Web.class);
    // ~ Protected Fields ====================================
    /** Template Mode **/
    protected static final String TPL_MODE = INCEPTOR.getString(Point.Web.Static.TEMPLATE_MODE);
    // ~ Static Fabricator
    /** Favicon Path **/
    protected static final String PATH_FAVICON = INCEPTOR.getString(Point.Web.Static.FAVICON);
    /** Resource Path **/
    protected static final String PATH_RES = INCEPTOR.getString(Point.Web.Static.RESOURCE);

    // ~ Dynamic Fabricator
    /** Dynamic Admin Path **/
    protected static final String PATH_DYADM = INCEPTOR.getString(Point.Web.Dynamic.ADMIN_ROUTE);
    /** Dynamic Path **/
    protected static final String PATH_DYNAMIC = INCEPTOR.getString(Point.Web.Dynamic.BASIC_ROUTE);

    // ~ Api Fabricator
    /** Api Path **/
    protected static final String PATH_API = INCEPTOR.getString(Point.Web.Api.PUBLIC);
    /** Secure Api Path **/
    protected static final String PATH_SEC_API = INCEPTOR.getString(Point.Web.Api.SECURE);
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    public void testInceptor(){
        System.out.println(PATH_SEC_API);
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
