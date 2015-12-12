package com.prayer.rest.basic.post;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.bus.AbstractRBTestCase;

/**
 * 
 * @author Lang
 *
 */
public class AccountTestCase extends AbstractRBTestCase {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountTestCase.class);
    /** **/
    private static final ConcurrentMap<String,String> REQ_RULE = new ConcurrentHashMap<>();
    /** **/
    private static final ConcurrentMap<String,String> FMT_RULE = new ConcurrentHashMap<>();
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    /** **/
    static{
        // Required Parameters
        REQ_RULE.put("post/001account-params-missing.json", "name = username");
        REQ_RULE.put("post/002account-params-missing.json", "name = password");
        REQ_RULE.put("post/003account-params-missing.json", "name = mobile");
        REQ_RULE.put("post/004account-params-missing.json", "name = email");
        // Format Parameters
        FMT_RULE.put("post/001account-params-format.json", "xx");
    }
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    // ~ Methods =============================================
    /** **/
    @Test
    public void testRequired() {
        for(final String key: REQ_RULE.keySet()){
            this.testPostRequired("/sec/account", key, REQ_RULE.get(key));
        }
    }
    @Test
    /** **/
    public void testFormat(){
        for(final String key: FMT_RULE.keySet()){
            this.testPostValidation("/sec/account", key, FMT_RULE.get(key));
        }
    }
    // ~ Private Methods =====================================

    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
