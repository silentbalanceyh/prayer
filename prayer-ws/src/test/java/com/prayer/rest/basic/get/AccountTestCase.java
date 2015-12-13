package com.prayer.rest.basic.get;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Lang
 *
 */
public class AccountTestCase extends AbstractGetTestCase {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountTestCase.class);
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public Logger getLogger(){
        return LOGGER;
    }
    /** **/
    @Override
    public List<String> getValidateRules(){
        final List<String> validateRules = new ArrayList<>();
        validateRules.add("get/account/not-existing-params-001.json");
        return validateRules;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
