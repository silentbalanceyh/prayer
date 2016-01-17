package com.prayer.schema.json.violater;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.base.schema.AbstractViolater;
import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Rule;
import com.prayer.facade.schema.rule.Violater;
import com.prayer.schema.json.rule.UniqueRule;

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
public final class UniqueViolater extends AbstractViolater implements Violater{
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
    public UniqueViolater(@NotNull @InstanceOfAny(UniqueRule.class) final Rule rule){
        this.rule = rule;
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
    /** **/
    @Override
    public AbstractSchemaException violate(@NotNull final ObjectHabitus habitus) {
        /** 提取各种数据 **/
        final JsonArray data = habitus.data();
        final JsonObject filter = habitus.filter();
        final JsonObject addtional = habitus.addtional();
        System.out.println(habitus.hashCode());
        System.out.println(data);
        System.out.println(filter);
        System.out.println(addtional);
        return null;
    }

}
