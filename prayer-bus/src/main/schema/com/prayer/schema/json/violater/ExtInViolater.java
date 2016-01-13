package com.prayer.schema.json.violater;

import static com.prayer.util.reflection.Instance.instance;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.constant.Constants;
import com.prayer.constant.Symbol;
import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Rule;
import com.prayer.facade.schema.rule.Violater;
import com.prayer.schema.json.rule.ExtInRule;
import com.prayer.util.entity.stream.StreamList;
import com.prayer.util.string.StringKit;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.InstanceOfAny;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class ExtInViolater extends InViolater implements Violater {

    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param rule
     */
    public ExtInViolater(@NotNull @InstanceOfAny(ExtInRule.class) final Rule rule) {
        super(rule);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public AbstractSchemaException violate(@NotNull final ObjectHabitus habitus) {
        /** 从原始数据中拿到Data **/
        final JsonObject data = habitus.getRaw().getJsonObject(R_DATA);
        final JsonObject addtional = habitus.getRaw().getJsonObject(R_ADDT);
        /** 读取规范 **/
        final ConcurrentMap<String, JsonObject> expectes = this.prepareExpected();
        AbstractSchemaException error = null;
        for (final String expected : expectes.keySet()) {
            final String literal = data.getString(expected);
            final JsonObject specification = expectes.get(expected);
            final JsonArray values = specification.getJsonArray("values");
            if (!inValues(values, literal)) {
                final String errorCls = specification.getString("error");
                final List<String> arguments = StreamList.toList(specification.getJsonArray("arguments"));
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

    private ConcurrentMap<String, JsonObject> prepareExpected() {
        final JsonObject rules = this.getRule().getJsonObject(R_VALUE);
        final ConcurrentMap<String, JsonObject> retMap = new ConcurrentHashMap<>();
        for (final String field : rules.fieldNames()) {
            if (StringKit.isNonNil(field)) {
                final JsonObject specification = rules.getJsonObject(field);
                if (null != specification) {
                    retMap.put(field, specification);
                }
            }
        }
        return retMap;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
