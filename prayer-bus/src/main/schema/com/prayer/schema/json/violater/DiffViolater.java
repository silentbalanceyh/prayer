package com.prayer.schema.json.violater;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.base.schema.AbstractViolater;
import com.prayer.constant.Constants;
import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Rule;
import com.prayer.facade.schema.rule.Violater;
import com.prayer.schema.json.rule.DiffRule;

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
public final class DiffViolater extends AbstractViolater implements Violater {
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
    public DiffViolater(@NotNull @InstanceOfAny(DiffRule.class) final Rule rule) {
        this.rule = rule;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public AbstractSchemaException violate(@NotNull final ObjectHabitus habitus) {
        /** **/
        final ConcurrentMap<String, Object> values = habitus.values();
        final List<String[]> rules = this.getRules();
        /** **/
        AbstractSchemaException error = null;
        for (final String[] rule : rules) {
            final String[] keys = VHelper.calculate(values, rule, VCondition::eq);
            if (null != keys) {
                final Object[] arguments = new Object[] { this.rule.position() + " ==> " + keys[Constants.IDX],
                        keys[Constants.ONE] };
                error = this.error(this.rule, arguments, null);
                break;
            }
        }
        return error;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private List<String[]> getRules() {
        // 每一个属性就是一条对应的Rule信息
        final ConcurrentMap<String, String> expectes = this.preparedMap(rule, this::extract);
        List<String[]> rules = new ArrayList<>();
        for (final String field : expectes.keySet()) {
            final String[] rule = new String[Constants.TWO];
            rule[Constants.IDX] = field;
            rule[Constants.ONE] = expectes.get(field);
            rules.add(rule);
        }
        return rules;
    }

    private String extract(final Object value) {
        String ret = null;
        if (null != value) {
            ret = value.toString();
        }
        return ret;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
