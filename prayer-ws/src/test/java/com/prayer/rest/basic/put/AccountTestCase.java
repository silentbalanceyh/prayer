package com.prayer.rest.basic.put;

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
public class AccountTestCase extends AbstractPutTestCase{
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
    public Logger getLogger() {
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
        final ConcurrentMap<String, String> requiredRules = new ConcurrentHashMap<>();
        requiredRules.put("put/account/missing-params-001", "name = id");
        return requiredRules;
    }
    /** **/
    @Override
    public List<String> getValidateRules(){
        final List<String> validateRules = new ArrayList<>();
        validateRules.add("put/account/non-existing-params-001");
        validateRules.add("put/account/format-params-001");
        validateRules.add("put/account/format-params-002");
        validateRules.add("put/account/format-params-003");
        validateRules.add("put/account/format-params-004");
        return validateRules;
    }
    /** **/
    @Override
    public List<String> getDependRules(){
        final List<String> dependRules = new ArrayList<>();
        dependRules.add("put/account/dependant-params-001");
        dependRules.add("put/account/dependant-params-002");
        dependRules.add("put/account/dependant-params-003");
        return dependRules;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
