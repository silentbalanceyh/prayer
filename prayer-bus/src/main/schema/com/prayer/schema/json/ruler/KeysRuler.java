package com.prayer.schema.json.ruler;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.facade.schema.rule.ArrayHabitus;
import com.prayer.facade.schema.rule.ArrayRuler;
import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Ruler;
import com.prayer.schema.json.JObjectHabitus;

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
public final class KeysRuler implements ArrayRuler {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    @NotNull
    private transient final Ruler ruler;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public KeysRuler() {
        this.ruler = new KeyRuler();
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    @Override
    public void apply(@NotNull final ArrayHabitus habitus) throws AbstractSchemaException {
        /** 8.0.优先处理habitus中的容器异常：10002/10006 **/
        habitus.ensure();
        /** 8.1.处理Keys的单独属性 **/
        verifyKeySpecification(habitus);
        /** 8.2.处理Duplicated属性 **/
        RulerHelper.applyDuplicated(habitus, FileConfig.CFG_KEY, new JsonObject());
        /** 8.3.处理PK唯一 **/
        RulerHelper.applyMost(habitus, FileConfig.CFG_KPKO, new JsonObject());
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private void verifyKeySpecification(final ArrayHabitus habitus) throws AbstractSchemaException {
        for (final Object value : habitus.data()) {
            if (null != value) {
                /** 1.构造基础数据对象 **/
                final JsonObject data = (JsonObject) value;
                final ObjectHabitus key = new JObjectHabitus(data);
                /** 2.调用Key的Ruler **/
                this.ruler.apply(key);
            }
        }
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}