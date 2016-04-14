package com.prayer.schema.json.violater;

import com.prayer.database.util.sql.SqlTypes;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Rule;
import com.prayer.facade.schema.rule.Violater;
import com.prayer.fantasm.exception.AbstractSchemaException;
import com.prayer.fantasm.schema.AbstractViolater;
import com.prayer.schema.json.rule.DBUpdatingRule;

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
public final class DBUpdatingViolater extends AbstractViolater implements Violater {
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
    public DBUpdatingViolater(@NotNull @InstanceOfAny(DBUpdatingRule.class) final Rule rule) {
        this.rule = rule;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public AbstractSchemaException violate(@NotNull final ObjectHabitus habitus) {
        AbstractSchemaException error = null;

        final JsonObject checked = rule.getRule();
        if (null != checked && checked.getBoolean("check")) {
            /** 1.抽取对应的规则 **/
            final JsonObject addtional = habitus.addtional();
            /** 2.遍历需要检查的Old Field中的数据 **/
            final JsonArray fields = habitus.data();
            final int size = fields.size();
            for (int idx = 0; idx < size; idx++) {
                /** 3.遍历每一个字段定义信息 **/
                final JsonObject field = fields.getJsonObject(idx);
                if (null != field) {
                    /** 4.获取元数据信息 **/
                    final JsonObject metadata = addtional.getJsonObject(field.getString("name"));
                    final JsonArray expected = metadata.getJsonArray("types");
                    final String value = SqlTypes.get(field.getString("columnType"));
                    final boolean ret = VCondition.nin(value, expected);
                    if (ret) {
                        /** 5.单个检查流程 **/
                        final Object[] args = new Object[3];
                        args[Constants.IDX] = field.getString("name");
                        args[Constants.ONE] = value;
                        args[Constants.TWO] = metadata.getString("target");
                        /** 6.初始化错误信息 **/
                        error = this.error(rule, args, new JsonObject());
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
