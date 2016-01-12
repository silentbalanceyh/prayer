package com.prayer.schema.json.ruler;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Rule;
import com.prayer.facade.schema.rule.Ruler;
import com.prayer.schema.json.RuleBuilder;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * Schema的根节点的规则器
 * 
 * @author Lang
 *
 */
@Guarded
public final class RootRuler implements Ruler {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public void apply(@NotNull final ObjectHabitus habitus) throws AbstractSchemaException {
        /** 1.1.验证Required属性 **/
        this.applyRequired(habitus);
        /** 1.2.验证Root下边每个属性的Json类型 **/
        this.applyJType(habitus);
        /** 1.3.验证Root下边不支持的属性 **/
        this.applyUnsupport(habitus);
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    /** 1.1 **/
    // Error-10001: __root__ ( Virtual ) -> __meta__
    // Error-10001: __root__ ( Virtual ) -> __keys__
    // Error-10001: __root__ ( Virtual ) -> __fields__
    private void applyRequired(final ObjectHabitus habitus) throws AbstractSchemaException {
        final Rule rule = RuleBuilder.required(FileConfig.CFG_ROOT);
        RulerHelper.sharedApply(habitus, rule);
    }

    /** 1.2 **/
    // Error-10002: __root__ -> __meta__ -> JsonObject
    // Error-10002: __root__ -> __keys__ -> JsonArray
    // Error-10002: __root__ -> __fields__ -> JsonArray
    private void applyJType(final ObjectHabitus habitus) throws AbstractSchemaException {
        final Rule rule = RuleBuilder.jtype(FileConfig.CFG_ROOT);
        RulerHelper.sharedApply(habitus, rule);
    }
    /** 1.3 **/
    // Error-10017: Only Support: __meta__, __keys__, __fields__, __indexes__
    private void applyUnsupport(final ObjectHabitus habitus) throws AbstractSchemaException{
        final Rule rule = RuleBuilder.unsupport(FileConfig.CFG_ROOT);
        RulerHelper.sharedApply(habitus, rule);
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
