package com.prayer.business.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.business.AbstractService;
import com.prayer.model.business.behavior.ActRequest;

/**
 * 
 * @author Lang
 *
 */
public class RecordInvalidTestCase extends AbstractService {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(RecordInvalidTestCase.class);

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

    // ~ Methods =============================================
    /** **/
    @Test
    public void testActRequest1() {
        final ActRequest request = this.prepareRequest("invalid-request1-identifier.json");
        assertNotNull(request.getError());
        assertEquals(-30012, request.getError().getErrorCode());
        final boolean ret = 0 < request.getError().getErrorMessage().indexOf("name = identifier");
        assertTrue(ret);
    }

    /** **/
    @Test
    public void testActRequest2() {
        final ActRequest request = this.prepareRequest("invalid-request2-method.json");
        assertNotNull(request.getError());
        assertEquals(-30012, request.getError().getErrorCode());
        final boolean ret = 0 < request.getError().getErrorMessage().indexOf("name = method");
        assertTrue(ret);
    }

    /** **/
    @Test
    public void testActRequest3() {
        final ActRequest request = this.prepareRequest("invalid-request3-method.json");
        assertNotNull(request.getError());
        assertEquals(-30013, request.getError().getErrorCode());
    }

    /** **/
    @Test
    public void testActRequest4() {
        final ActRequest request = this.prepareRequest("invalid-request4-data.json");
        assertNotNull(request.getError());
        assertEquals(-30012, request.getError().getErrorCode());
        final boolean ret = 0 < request.getError().getErrorMessage().indexOf("name = data");
        assertTrue(ret);
    }

    /** **/
    @Test
    public void testActRequest5() {
        final ActRequest request = this.prepareRequest("invalid-request5-query.json");
        assertNotNull(request.getError());
        assertEquals(-30013, request.getError().getErrorCode());
    }

    /** **/
    @Test
    public void testActRequest6() {
        final ActRequest request = this.prepareRequest("invalid-request5-query2.json");
        assertNotNull(request.getError());
        assertEquals(-30013, request.getError().getErrorCode());
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}