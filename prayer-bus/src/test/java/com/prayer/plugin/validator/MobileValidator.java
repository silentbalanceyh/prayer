package com.prayer.plugin.validator;

import com.prayer.exception.AbstractDatabaseException;
import com.prayer.facade.kernel.Validator;
import com.prayer.facade.kernel.Value;
import com.prayer.model.type.DataType;

/**
 * 
 * @author Lang
 *
 */
public class MobileValidator implements Validator{    // NOPMD
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public boolean validate(final Value<?> value, final Object... params) throws AbstractDatabaseException {
        boolean ret = false;
        if(DataType.STRING == value.getDataType()){
            if(value.literal().trim().equals("15922611447")){
                ret = true;
            }
        }
        return ret;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
