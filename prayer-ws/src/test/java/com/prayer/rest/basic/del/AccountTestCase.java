package com.prayer.rest.basic.del;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Lang
 *
 */
public class AccountTestCase extends AbstractDeleteTestCase{
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
    public String getPath(){
        return "/sec/account";
    }
    /** **/
    @Override
    public ConcurrentMap<String,String> getRequiredRules(){
        final ConcurrentMap<String,String> requiredRules = new ConcurrentHashMap<>();
        // Required is not needed for DELETE
        return requiredRules;
    }
    /** **/
    @Override
    public List<String> getValidateRules(){
        final List<String> validateRules = new ArrayList<>();
        validateRules.add("delete/account/not-existing-params-001.json");
        return validateRules;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
