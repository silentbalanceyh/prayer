package com.prayer.plugin.validator;

import static com.prayer.util.debug.Log.jvmError;
import static com.prayer.util.debug.Log.peError;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.database.ContentErrorException;
import com.prayer.facade.model.crucial.Validator;
import com.prayer.facade.model.crucial.Value;
import com.prayer.fantasm.exception.AbstractDatabaseException;

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
            jvmError(LOGGER,ex);
            final AbstractDatabaseException error = new ContentErrorException(getClass(), "Xml", value.literal()); // NOPMD
            peError(LOGGER,error);
            throw error;// NOPMD
        }
        return ret;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
