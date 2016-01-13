package com.prayer.schema.json.violater;

import static com.prayer.util.reflection.Instance.instance;

import java.util.List;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.constant.Constants;
import com.prayer.constant.Symbol;
import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Rule;
import com.prayer.facade.schema.rule.Violater;
import com.prayer.schema.json.rule.MostRule;
import com.prayer.util.entity.stream.StreamList;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.InstanceOfAny;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 最多出现次数
 * 
 * @author Lang
 *
 */
@Guarded
public final class MostViolater implements Violater {
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
    public MostViolater(@NotNull @InstanceOfAny(MostRule.class) final Rule rule) {
        this.rule = rule;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public AbstractSchemaException violate(@NotNull final ObjectHabitus habitus) {
        /** **/
        final JsonArray data = habitus.getRaw().getJsonArray(R_DATA);
        final JsonObject addtional = habitus.getRaw().getJsonObject(R_ADDT);
        final JsonArray expectes = this.rule.getRule().getJsonArray(R_VALUE);
        /** **/
        AbstractSchemaException error = null;
        final int size = expectes.size();
        for (int pos = 0; pos < size; pos++) {
            final JsonObject expected = expectes.getJsonObject(pos);
            if (!verifyOccurs(data, expected)) {
                final String errorCls = expected.getString("error");
                final List<String> arguments = StreamList.toList(expected.getJsonArray("arguments"));
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

    private boolean verifyOccurs(final JsonArray data, final JsonObject expected) {
        /** 获取配置数据 **/
        final int leastOccurs = expected.getInteger("occurs");
        final JsonObject filters = expected.getJsonObject("filter");
        boolean ret = true;
        /** 开始比较 **/
        final int size = data.size();
        int actualOccurs = 0;
        for (int pos = 0; pos < size; pos++) {
            final JsonObject item = data.getJsonObject(pos);
            /** 遍历整个Filter **/
            boolean isMatch = true;
            for (final String field : filters.fieldNames()) {
                final Object exp = filters.getValue(field);
                final Object actual = item.getValue(field);
                if (null == exp || null == actual || !exp.equals(actual)) {
                    isMatch = false;
                    break;
                }
            }
            /** 当前数据项通过了Filter的设置 **/
            if (isMatch) {
                actualOccurs++;
            }
            /** 只要actualOccurs超过了least Occurs则直接返回true **/
            if (leastOccurs <= actualOccurs) {
                ret = false;
                break;
            }
        }
        return ret;
    }
    // ~ hashCode,equals,toString ============================

}
