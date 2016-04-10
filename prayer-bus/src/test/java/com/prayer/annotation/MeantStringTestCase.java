package com.prayer.annotation;

import org.junit.Test;

import com.prayer.facade.annotation.oval.MeantString;

import net.sf.oval.exception.ConstraintsViolatedException;
import net.sf.oval.guard.Guarded;

/** **/
@Guarded
public class MeantStringTestCase {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /** **/
    public static void print(@MeantString final String message) {
        System.out.println(message);
    }
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================

    @Test(expected = ConstraintsViolatedException.class)
    public void testNull() {
        MeantStringTestCase.print(null);
    }
    
    @Test(expected = ConstraintsViolatedException.class)
    public void testBlank() {
        MeantStringTestCase.print("   ");
    }
    
    @Test(expected = ConstraintsViolatedException.class)
    public void testEmpty() {
        MeantStringTestCase.print("");
    }
    
    @Test
    public void testValid() {
        MeantStringTestCase.print("Hello.World");
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
