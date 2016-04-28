package com.prayer.script.js;

import static com.prayer.util.reflection.Instance.singleton;

import javax.script.ScriptException;

import org.junit.Test;

import com.prayer.facade.script.Workshop;

/**
 * 
 * @author Lang
 *
 */
public class WorkshopTestCase {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Test
    public void testInit() throws ScriptException {
        final Workshop workshop = singleton(JSWorkshop.class);
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
