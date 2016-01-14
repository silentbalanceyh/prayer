package com.prayer.schema.json.violater;

import static com.prayer.util.reflection.Instance.instance;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.constant.Constants;
import com.prayer.constant.Symbol;
import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Rule;
import com.prayer.facade.schema.rule.Violater;
import com.prayer.schema.json.rule.DuplicatedRule;
import com.prayer.util.entity.stream.StreamList;

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
        final JsonObject expectes = this.rule.getRule().getJsonObject(R_VALUE);
        final JsonArray data = habitus.getRaw().getJsonArray(R_DATA);
        final JsonObject addtional = habitus.getRaw().getJsonObject(R_ADDT);
        /** **/
        AbstractSchemaException error = null;

        for (final String field : expectes.fieldNames()) {
            final JsonObject expected = expectes.getJsonObject(field);
            // 验证Duplicated的信息
            final int retIdx = verifyDuplicated(data, field);
            if (Constants.RANGE != retIdx) {
                final String errorCls = expected.getString("error");
                final List<String> arguments = StreamList.toList(expected.getJsonArray("arguments"));
                if (!arguments.isEmpty() && Constants.ZERO < arguments.size()
                        && arguments.get(Constants.IDX).equals("position")) {
                    final String fieldStr = data.getJsonObject(retIdx).getString(field);
                    final String formated = this.rule.position() + " -> [" + retIdx + "] " + field + " = " + fieldStr;
                    addtional.put("position", formated);
                }
                error = getError(errorCls, arguments, addtional);
            }
        }
        return error;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private AbstractSchemaException getError(final String errorCls, final List<String> arguments,
            final JsonObject addtional) {
        final String fullCls = "com.prayer.exception.schema" + Symbol.DOT + errorCls;
        final Object[] args = new Object[arguments.size() + 1];
        // 第一个参数为当前的类
        args[Constants.IDX] = getClass();
        for (int idx = 1; idx < args.length; idx++) {
            // 注意arguments的offset偏移量
            final String field = arguments.get(idx - 1);
            final Object value = addtional.getValue(field);
            args[idx] = value;
        }
        return instance(fullCls, args);
    }

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
