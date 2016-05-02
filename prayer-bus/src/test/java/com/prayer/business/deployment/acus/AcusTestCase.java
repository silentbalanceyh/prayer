package com.prayer.business.deployment.acus;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.prayer.facade.business.instantor.deployment.acus.DeployAcus;

/**
 * 
 * @author Lang
 *
 */
public class AcusTestCase {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Test
    public void testInitAcus(){
        final DeployAcus acus = new InitAcus();
        assertNotNull(acus);
    }
    /** **/
    @Test
    public void testSchemaAcus(){
        final DeployAcus acus = new SchemaAcus();
        assertNotNull(acus);
    }
    /** **/
    @Test
    public void testScriptAcus(){
        final DeployAcus acus = new ScriptAcus();
        assertNotNull(acus);
    }
    /** **/
    @Test
    public void testUriAcus(){
        final DeployAcus acus = new UriAcus();
        assertNotNull(acus);
    }
    /** **/
    @Test
    public void testVertxAcus(){
        final DeployAcus acus = new VertxAcus();
        assertNotNull(acus);
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
