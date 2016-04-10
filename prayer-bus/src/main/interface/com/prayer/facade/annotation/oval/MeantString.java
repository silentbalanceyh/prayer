package com.prayer.facade.annotation.oval;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.prayer.plugin.oval.annotation.MeantStringCheck;

import net.sf.oval.ConstraintTarget;
import net.sf.oval.configuration.annotation.Constraint;
import net.sf.oval.configuration.annotation.Constraints;

/**
 * 
 * @author Lang
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD })
@Constraint(checkWith = MeantStringCheck.class)
public @interface MeantString {
    
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
    @Constraints
    public @interface List
    {
        /**
         * The MeantString constraints.
         */
        MeantString[] value();
        
        String when() default "";
    }
    
    ConstraintTarget[] appliesTo() default ConstraintTarget.VALUES;
    
    String errorCode() default "com.prayer.facade.annotation.oval.MeantString";
    
    String message() default "com.prayer.facade.annotation.oval.MeantString.violated";
    
    String[] profiles() default {};
    
    int severity() default 0;
    
    String target() default "";
    
    String when() default "";
}
