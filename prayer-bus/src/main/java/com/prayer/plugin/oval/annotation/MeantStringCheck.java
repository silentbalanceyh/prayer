package com.prayer.plugin.oval.annotation;

import com.prayer.facade.annotation.oval.MeantString;

import net.sf.oval.ConstraintTarget;
import net.sf.oval.Validator;
import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;
import net.sf.oval.context.OValContext;
import net.sf.oval.exception.OValException;

/**
 * NotBlank, NotEmpty, NotNull ( Merged )
 * 
 * @author Lang
 *
 */
public class MeantStringCheck extends AbstractAnnotationCheck<MeantString> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @Override
    protected ConstraintTarget[] getAppliesToDefault() {
        return new ConstraintTarget[] { ConstraintTarget.VALUES };
    }

    /**
     * 
     */
    @Override
    public boolean isSatisfied(final Object validatedObj, final Object valueToValidated, final OValContext context,
            final Validator validator) throws OValException {
        /** 1.NotNull Check **/
        boolean checked = valueToValidated != null;
        System.out.println(checked);
        /** 2.NotEmpty Check **/
        if (checked) {
            checked = valueToValidated.toString().length() > 0;
        }
        System.out.println(checked);
        /** 3.NotBlank Check **/
        if (checked) {
            final String value = valueToValidated.toString();
            final int len = value.length();
            for (int idx = 0; idx < len; idx++) {
                final char charAt = value.charAt(idx);
                if (Character.isSpaceChar(charAt) || Character.isWhitespace(charAt)) {
                    checked = false;
                    break;
                }
            }
        }
        return checked;
    }
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
