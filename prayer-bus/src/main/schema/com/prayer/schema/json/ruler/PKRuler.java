package com.prayer.schema.json.ruler;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.constant.SystemEnum.MetaPolicy;
import com.prayer.facade.schema.rule.ArrayHabitus;
import com.prayer.facade.schema.rule.ArrayRuler;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.AssertFieldConstraints;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class PKRuler implements ArrayRuler {

    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 操作的表名，用于异常信息 **/
    @NotNull
    @NotEmpty
    @NotBlank
    private transient final String table;
    /** Policy信息，用于判断 **/
    @NotNull
    private transient final MetaPolicy policy;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param table
     * @param policy
     */
    @PostValidateThis
    public PKRuler(@AssertFieldConstraints("table") final String table,
            @AssertFieldConstraints("policy") final MetaPolicy policy) {
        this.table = table;
        this.policy = policy;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void apply(@NotNull final ArrayHabitus habitus) throws AbstractSchemaException {
        /** 4.0.优先处理ArrayHabitus中的容器异常：10002/10006 **/
        if (null != habitus.getError()) {
            throw habitus.getError();
        }
        /** 4.1.检查Array中是否包含了 primarykey = true **/
        final JsonObject addtional = new JsonObject();
        addtional.put("table", this.table);
        RulerHelper.applyLeast(habitus, FileConfig.CFG_FPK, addtional);
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
