package com.prayer.kernel.validator;

import static com.prayer.util.Error.info;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.AbstractDatabaseException;
import com.prayer.exception.metadata.ContentErrorException;
import com.prayer.kernel.Validator;
import com.prayer.kernel.Value;

import net.sf.oval.constraint.InstanceOf;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.constraint.Size;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
final class XmlValidator implements Validator { // NOPMD

    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(XmlValidator.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public boolean validate(@NotNull @InstanceOf(Value.class) final Value<?> value,
            @Size(min = 0, max = 0) final Object... params) throws AbstractDatabaseException {
        boolean ret = false;
        try {
            DocumentHelper.parseText(value.literal());
            ret = true;
        } catch (DocumentException ex) {
            info(LOGGER, "[E] Xml Data Format Error! Output = " + value, ex);
            throw new ContentErrorException(getClass(), "Xml", value.literal()); // NOPMD
        }
        return ret;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
