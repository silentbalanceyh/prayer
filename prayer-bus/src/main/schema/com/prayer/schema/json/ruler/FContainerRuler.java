package com.prayer.schema.json.ruler;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.facade.schema.rule.ArrayHabitus;
import com.prayer.facade.schema.rule.ArrayRuler;
import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Ruler;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class FContainerRuler implements ArrayRuler {

    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 因为Container中的每一个元素只能为JsonObject，所以每个JsonObject必须使用同样的Ruler来处理 **/
    @NotNull
    private transient final Ruler ruler;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public FContainerRuler(@NotNull final Ruler ruler) {
        this.ruler = ruler;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void apply(@NotNull final ArrayHabitus habitus) throws AbstractSchemaException {
        /** 3.0.优先处理ArrayHabitus中的容器异常：10002/10006 **/
        if (null != habitus.getError()) {
            throw habitus.getError();
        }
        /** 3.1.处理容器中的每一个元素，无交叉验证，可并行 **/
        final int size = habitus.objects().size();
        for (int idx = 0; idx < size; idx++) {
            // TODO: 这里不可以使用Foreach，因为需要从系统中读取Idx并生成对应的Debug日志
            final ObjectHabitus item = habitus.get(idx);
            this.ruler.apply(item);
        }
        /** 3.2.检查Duplicated的配置 **/
        // Error 10007/10008
        // name
        // columnName
        RulerHelper.applyDuplicated(habitus, FileConfig.CFG_FIELD);
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
