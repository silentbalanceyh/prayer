package com.prayer.schema.json.ruler;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Ruler;
import com.prayer.model.type.DataType;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class TypeRuler implements Ruler{
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 当前这个Ruler验证的类型 **/
    @NotNull
    private transient final DataType type;
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** 构造函数，根据不同类型构造 **/
    @PostValidateThis
    public TypeRuler(@NotNull final DataType type){
        this.type = type;
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void apply(@NotNull final ObjectHabitus habitus) throws AbstractSchemaException {
        System.out.println(type.name());
    }
    
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
