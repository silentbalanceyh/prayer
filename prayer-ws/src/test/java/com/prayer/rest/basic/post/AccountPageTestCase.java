package com.prayer.rest.basic.post;

import static com.prayer.util.Converter.toStr;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public class AccountPageTestCase extends AbstractPostTestCase {
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
        // requiredRules.put("page/account/missing-params-001.json", "name =
        // page");
        // requiredRules.put("page/account/missing-params-002.json", "name =
        // page->index");
        // requiredRules.put("page/account/missing-params-003.json", "name =
        // page->size");
        requiredRules.put("page/account/missing-params-004.json", "");
        return requiredRules;
    }

    /** **/
    @Override
    public List<String> getValidateRules() {
        final List<String> validateRules = new ArrayList<>();

        return validateRules;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
