package com.prayer.schema.json.ruler;

import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Ruler;
import com.prayer.fantasm.exception.AbstractSchemaException;

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
    /** **/
    @Override
    public void apply(@NotNull final ObjectHabitus habitus) throws AbstractSchemaException {
        /** 1.1.验证Required属性 **/
        // Error-10001: __root__ ( Virtual ) -> __meta__
        // Error-10001: __root__ ( Virtual ) -> __keys__
        // Error-10001: __root__ ( Virtual ) -> __fields__
        RulerHelper.applyRequired(habitus, FileConfig.CFG_ROOT);
        /** 1.2.验证Root下边每个属性的Json类型 **/
        // Error-10002: __root__ -> __meta__ -> JsonObject
        // Error-10002: __root__ -> __keys__ -> JsonArray
        // Error-10002: __root__ -> __fields__ -> JsonArray
        RulerHelper.applyJType(habitus, FileConfig.CFG_ROOT);
        /** 1.3.验证Root下边不支持的属性 **/
        // Error-10017:
        // Required: __meta__, __keys__, __fields__
        // Optional: __indexes__
        RulerHelper.applyUnsupport(habitus, FileConfig.CFG_ROOT);
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
