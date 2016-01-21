package com.prayer.schema.json.ruler;

import com.prayer.constant.SystemEnum.Mapping;
import com.prayer.facade.schema.rule.ArrayHabitus;
import com.prayer.facade.schema.rule.ArrayRuler;
import com.prayer.fantasm.exception.AbstractSchemaException;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class SubsRuler implements ArrayRuler {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** Mapping的情况 **/
    @NotNull
    private transient final Mapping mapping;
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public SubsRuler(@NotNull final Mapping mapping){
        this.mapping = mapping;
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void apply(@NotNull final ArrayHabitus habitus) throws AbstractSchemaException {
        /** 5.0.优先处理ArrayHabitus中的异常：10002/10006 **/
        habitus.ensure();
        /** (19) 5.1.如果Mapping为COMBINATED的时候需要使用 **/
        if(Mapping.COMBINATED == this.mapping){
            RulerHelper.applyLeast(habitus, FileConfig.CFG_SUB, new JsonObject());
        }
    }
    
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
