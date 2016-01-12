package com.prayer.exception.schema;

import static com.prayer.util.Converter.toStr;

import java.util.Set;

import com.prayer.base.exception.AbstractSchemaException;

/**
 * 【Checked】Error-10017：不支持的属性异常
 * 
 * @author Lang
 * @see
 */
public class UnsupportAttrException extends AbstractSchemaException {

    // ~ Static Fields =======================================

    /**
     * 
     */
    private static final long serialVersionUID = 7414619032332865858L;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param clazz
     * @param sets
     */
    public UnsupportAttrException(final Class<?> clazz, final Set<String> sets) {
        super(clazz, -10017, toStr(sets));
    }

    /**
     * 
     * @param clazz
     * @param unsupported
     */
    public UnsupportAttrException(final Class<?> clazz, final String unsupported) {
        super(clazz, -10017, unsupported);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public int getErrorCode() {
        return -10017;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ hashCode,equals,toString ============================

}
