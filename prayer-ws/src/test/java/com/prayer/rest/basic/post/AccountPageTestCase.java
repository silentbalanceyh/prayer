package com.prayer.rest.basic.post;

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
public class AccountPageTestCase extends AbstractPageTestCase {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountPageTestCase.class);

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
    public String getPath() {
        return "/sec/page/account";
    }

    /** **/
    @Override
    public ConcurrentMap<String, String> getRequiredRules() {
        final ConcurrentMap<String, String> requiredRules = new ConcurrentHashMap<>();
        requiredRules.put("page/account/missing-params-001.json", "name = page");
        requiredRules.put("page/account/missing-params-002.json", "name = page->index");
        requiredRules.put("page/account/missing-params-003.json", "name = page->size");
        return requiredRules;
    }

    /** **/
    @Override
    public List<String> getValidateRules() {
        final List<String> validateRules = new ArrayList<>();
        // Page Checking : PagerValidator
        validateRules.add("page/account/range-params-001.json");
        validateRules.add("page/account/range-params-002.json");
        validateRules.add("page/account/range-params-003.json");
        validateRules.add("page/account/range-params-004.json");
        // Order Checking
        return validateRules;
    }

    /** **/
    @Override
    public List<String> getValidate30010() {
        final List<String> validateRules = new ArrayList<>();
        validateRules.add("page/account/spec-params-001.json");
        validateRules.add("page/account/spec-params-002.json");
        return validateRules;
    }

    /** **/
    @Override
    public List<String> getValidate30019() {
        final List<String> validateRules = new ArrayList<>();
        validateRules.add("page/account/array-params-001.json");
        validateRules.add("page/account/array-params-002.json");
        return validateRules;
    }

    // ~ Methods =============================================

    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
