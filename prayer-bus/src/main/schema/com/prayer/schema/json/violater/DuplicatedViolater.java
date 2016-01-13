package com.prayer.schema.json.violater;

import java.util.HashSet;
import java.util.Set;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.constant.Constants;
import com.prayer.exception.schema.DuplicatedAttrException;
import com.prayer.exception.schema.DuplicatedColumnException;
import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Rule;
import com.prayer.facade.schema.rule.Violater;
import com.prayer.schema.json.rule.DuplicatedRule;

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
public final class DuplicatedViolater implements Violater {
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
    public DuplicatedViolater(@NotNull @InstanceOfAny(DuplicatedRule.class) final Rule rule) {
        this.rule = rule;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public AbstractSchemaException violate(@NotNull final ObjectHabitus habitus) {
        /** **/
        final JsonArray expectes = this.rule.getRule().getJsonArray(R_VALUE);
        final JsonArray data = habitus.getRaw().getJsonArray(R_DATA);
        /** **/
        AbstractSchemaException error = null;
        final int size = expectes.size();
        for (int idx = 0; idx < size; idx++) {
            final JsonObject expected = expectes.getJsonObject(idx);
            final String field = expected.getString("field");
            // 验证Duplicated的信息
            final int retIdx = verifyDuplicated(data, field);
            if (Constants.RANGE != retIdx) {
                final int errorCode = expected.getInteger("target");
                // 获取出现Duplicated的位置信息
                final String position = this.rule.position() + " -> [" + retIdx + "] " + field + " = "
                        + data.getJsonObject(retIdx).getString(field);
                // 根据target处理最终返回的Error
                if (errorCode == -10007) {
                    error = new DuplicatedAttrException(getClass(), position);
                } else if (errorCode == -10008) {
                    error = new DuplicatedColumnException(getClass(), position);
                }
            }
        }
        return error;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private int verifyDuplicated(final JsonArray data, final String field) {
        int ret = -1;
        final Set<String> values = new HashSet<>();
        final int size = data.size();
        for (int idx = 0; idx < size; idx++) {
            final JsonObject item = data.getJsonObject(idx);
            final String value = item.getString(field);
            if (values.contains(value)) {
                ret = idx;
                break;
            } else {
                values.add(value);
            }
        }
        return ret;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
