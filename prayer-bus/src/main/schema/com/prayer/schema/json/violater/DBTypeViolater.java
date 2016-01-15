package com.prayer.schema.json.violater;

import java.util.List;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.base.schema.AbstractViolater;
import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Rule;
import com.prayer.facade.schema.rule.Violater;
import com.prayer.schema.json.rule.DBTypeRule;

import net.sf.oval.constraint.InstanceOfAny;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class DBTypeViolater extends AbstractViolater implements Violater {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    private transient final Rule rule;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public DBTypeViolater(@NotNull @InstanceOfAny(DBTypeRule.class) final Rule rule) {
        this.rule = rule;
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================

    @Override
    public AbstractSchemaException violate(@NotNull final ObjectHabitus habitus) {
        /** **/
        final List<VDatabase> databases = this.preparedDatabase(this.rule, habitus);
        /** **/
        AbstractSchemaException error = null;
        for (final VDatabase database : databases) {
            error = this.validator().verifyColumnType(database.getTable(), database.getColumn(), database.getType());
            /** 不为空时已经有异常抛出，则这个时候直接跳出循环 **/
            if (null != error) {
                break;
            }
        }
        return error;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
