package com.prayer.schema.json.ruler;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.facade.schema.rule.ArrayHabitus;
import com.prayer.facade.schema.rule.ArrayRuler;
import com.prayer.facade.schema.rule.Ruler;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class ContainerRuler implements ArrayRuler {

    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 因为Container中的每一个元素只能为JsonObject，所以每个JsonObject必须使用同样的Ruler来处理 **/
    @NotNull
    private transient final Ruler ruler;
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public ContainerRuler(@NotNull final Ruler ruler) {
        this.ruler = ruler;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void apply(@NotNull final ArrayHabitus habitus) throws AbstractSchemaException {
        /** 3.0.优先处理ArrayHabitus中的容器异常：10002/10006 **/
        if(null != habitus.getError()){
            throw habitus.getError();
        }
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
