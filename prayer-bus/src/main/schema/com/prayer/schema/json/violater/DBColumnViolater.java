package com.prayer.schema.json.violater;

import static com.prayer.constant.Accessors.validator;
import static com.prayer.util.reflection.Instance.reservoir;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.constant.MemoryPool;
import com.prayer.constant.Resources;
import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Rule;
import com.prayer.facade.schema.rule.Violater;
import com.prayer.facade.schema.verifier.DataValidator;
import com.prayer.schema.json.rule.DBColumnRule;
import com.prayer.util.string.StringKit;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
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
public final class DBColumnViolater implements Violater {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    private transient final DataValidator validator;
    /** **/
    @NotNull
    private transient final Rule rule;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public DBColumnViolater(@NotNull @InstanceOfAny(DBColumnRule.class) final Rule rule) {
        this.rule = rule;
        this.validator = reservoir(MemoryPool.POOL_VALIDATOR, Resources.DB_CATEGORY, validator());
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    @Override
    public AbstractSchemaException violate(@NotNull final ObjectHabitus habitus) {
        /** **/
        final JsonArray columns = this.rule.getRule().getJsonArray(R_VALUE);
        /** **/
        AbstractSchemaException error = null;
        for (final Object value : columns) {
            if (null != value && JsonObject.class == value.getClass()) {
                final JsonObject colObj = (JsonObject) value;
                final String table = colObj.getString("table");
                final String column = colObj.getString("column");
                if (StringKit.isNonNil(table) && StringKit.isNonNil(column)) {
                    error = this.validator.verifyColumn(table, column);
                    /** 不为空时已经有异常抛出，则这个时候直接跳出循环 **/
                    if (null != error) {
                        break;
                    }
                }
            }
        }
        return error;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
