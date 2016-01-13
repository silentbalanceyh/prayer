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
public final class PKeysRuler implements ArrayRuler {

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
    public PKeysRuler(@AssertFieldConstraints("table") final String table,
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
        verifyPKMissing(habitus);
        /** 4.2.执行Dispatcher功能 **/
        applyDispatcher(habitus);
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private void applyDispatcher(final ArrayHabitus habitus) throws AbstractSchemaException {
        /** 4.2.0.分流操作，顶层检查Policy **/
        if (MetaPolicy.COLLECTION == this.policy) {
            /** (18.1.1) 4.2.1.当policy == COLLECTION时 **/
            verifyPKCollection(habitus);
        } else {
            /** (18.2.1) 4.2.2.当policy != COLLECTION时 **/
            verifyPKNonCollection(habitus);
        }
    }

    private void verifyPKNonCollection(final ArrayHabitus habitus) throws AbstractSchemaException {
        RulerHelper.applyMost(habitus, FileConfig.CFG_FPK_NCOL, getAddtional());
    }

    private void verifyPKCollection(final ArrayHabitus habitus) throws AbstractSchemaException {
        RulerHelper.applyLeast(habitus, FileConfig.CFG_FPK_COL, getAddtional());
    }

    private void verifyPKMissing(final ArrayHabitus habitus) throws AbstractSchemaException {
        RulerHelper.applyLeast(habitus, FileConfig.CFG_FPK, getAddtional());
    }

    private JsonObject getAddtional() {
        final JsonObject addtional = new JsonObject();
        addtional.put("table", this.table);
        addtional.put("policy", this.policy.toString());
        return addtional;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
