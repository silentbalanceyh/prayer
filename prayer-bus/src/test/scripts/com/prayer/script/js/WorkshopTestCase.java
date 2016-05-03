package com.prayer.script.js;

import static com.prayer.util.reflection.Instance.singleton;
import static org.junit.Assert.assertNotNull;

import javax.script.ScriptException;

import org.junit.Test;

import com.prayer.business.instantor.secure.basic.BasicAuthBllor;
import com.prayer.facade.business.instantor.secure.SecureInstantor;
import com.prayer.facade.model.record.Record;
import com.prayer.facade.script.Workshop;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.resource.Resources;

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
        assertNotNull(workshop);
    }
    /** **/
    @Test
    public void testInstantor() throws AbstractException{
        final SecureInstantor instantor = singleton(BasicAuthBllor.class);
        Record record = instantor.identByName(Resources.Security.REALM,"lang.yu", "E559D4DA17DD1C17BE86FCF49E60E322");
        System.out.println(record);
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
