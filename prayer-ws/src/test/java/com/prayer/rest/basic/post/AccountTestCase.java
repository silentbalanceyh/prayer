package com.prayer.rest.basic.post;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.bus.AbstractTemplateTestCase;

/**
 * 
 * @author Lang
 *
 */
public class AccountTestCase extends AbstractTemplateTestCase {
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
    public ConcurrentMap<String,String> getRequiredMap(){
        final ConcurrentMap<String,String> requiredRules = new ConcurrentHashMap<>();
        requiredRules.put("post/account/missing-params-001.json", "name = username");
        requiredRules.put("post/account/missing-params-002.json", "name = password");
        requiredRules.put("post/account/missing-params-003.json", "name = mobile");
        requiredRules.put("post/account/missing-params-004.json", "name = email");
        return requiredRules;
    }
    /** **/
    @Override
    public List<String> getValidateList(){
        final List<String> validateRules = new ArrayList<>();
        validateRules.add("post/account/format-params-001.json");
        validateRules.add("post/account/format-params-002.json");
        validateRules.add("post/account/format-params-003.json");
        validateRules.add("post/account/format-params-004.json");
        validateRules.add("post/account/existing-params-001.json");
        validateRules.add("post/account/existing-params-002.json");
        validateRules.add("post/account/existing-params-003.json");
        return validateRules;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
