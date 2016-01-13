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
import com.prayer.schema.json.rule.DBConstraintRule;
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
public final class DBConstraintViolater implements Violater {
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
    public DBConstraintViolater(@NotNull @InstanceOfAny(DBConstraintRule.class) final Rule rule) {
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
                // 读取配置信息
                final String tAttr = colObj.getString("table");
                final String cAttr = colObj.getString("column");
                if (StringKit.isNonNil(tAttr) && StringKit.isNonNil(cAttr)) {
                    // 读取真实表名和列名
                    final String table = habitus.get(tAttr);
                    final String column = habitus.get(cAttr);
                    if (StringKit.isNonNil(table) && StringKit.isNonNil(column)) {
                        error = this.validator.verifyConstraint(table, column);
                        /** 不为空时已经有异常抛出，则这个时候直接跳出循环 **/
                        if (null != error) {
                            break;
                        }
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
