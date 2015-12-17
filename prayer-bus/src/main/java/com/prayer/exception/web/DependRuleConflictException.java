package com.prayer.exception.web;

import com.prayer.base.exception.AbstractWebException;
import com.prayer.util.cv.SystemEnum.DependRule;

/**
 * 
 * @author Lang
 *
 */
public class DependRuleConflictException extends AbstractWebException {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = -722010831877869699L;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param expected
     * @param actual
     */
    public DependRuleConflictException(final Class<?> clazz, final DependRule expected, final DependRule actual) {
        super(clazz, -30025, expected.toString(), actual.toString());
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public int getErrorCode() {
        return -30025;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
