package com.prayer.schema.old.json;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;
import net.sf.oval.guard.PreValidateThis;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.prayer.base.exception.AbstractSchemaException;

/**
 * 
 * @author Lang
 *
 */
@Guarded
@Deprecated
final class SubRelEnsurer implements InternalEnsurer {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    private transient AbstractSchemaException error;
    /** **/
    @NotNull
    private transient final ArrayNode fieldsNode;     // NOPMD
    /** **/
    @NotNull
    private transient final JArrayValidator validator;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param fieldsNode
     */
    @PostValidateThis
    public SubRelEnsurer(@NotNull final ArrayNode fieldsNode) {
        this.fieldsNode = fieldsNode;
        this.error = null;     // NOPMD
        this.validator = new JArrayValidator(this.fieldsNode, Attributes.R_FIELDS);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    @PreValidateThis
    public void validate() throws AbstractSchemaException {
        // 1.验证关系是否正确
        validateRelMissing();
        interrupt();

    }

    /**
     * 根据结果判断返回值
     * 
     * @param result
     */
    @Override
    public void interrupt() throws AbstractSchemaException {
        if (null != this.error) {
            throw this.error;
        }
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    /**
     * 
     * @return
     */
    private boolean validateRelMissing() {
        // 19.验证是否包含了subtable属性，如果mapping = COMBINATED必须存在这个属性，且为true
        this.error = this.validator.verifyRelMissing(Attributes.F_SUB_TABLE, Boolean.TRUE, 1);
        if (null != this.error) {
            return false; 
        }
        return null == this.error;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
