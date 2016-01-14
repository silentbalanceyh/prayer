package com.prayer.schema.json.ruler;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Ruler;
import com.prayer.facade.schema.verifier.Attributes;

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
public final class FKeyRuler implements Ruler {

    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 当前表的信息，用于外键是否需要判断当前引用表的信息 **/
    @NotNull
    @NotEmpty
    @NotBlank
    private transient final String table;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public FKeyRuler(@AssertFieldConstraints("table") final String table) {
        this.table = table;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void apply(@NotNull final ObjectHabitus habitus) throws AbstractSchemaException {
        /** (20.1) 6.1.1. 外键字段必须包含两个特殊属性refId, refTable **/
        // Required: refId, refTable
        RulerHelper.applyExisting(habitus, FileConfig.CFG_FFK);
        /** (20.2) 6.1.2. 检查refId和refTable的格式对不对 **/
        RulerHelper.applyPattern(habitus, FileConfig.CFG_FFK);
        /** (21.1/2) 6.1.3. 检查Foreign Key的类型是否正确 **/
        final JsonObject addtional = this.getAddtional(habitus);
        RulerHelper.applyIn(habitus, FileConfig.CFG_FFK, addtional);
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private JsonObject getAddtional(final ObjectHabitus habitus) {
        final JsonObject addtional = new JsonObject();
        addtional.put(Attributes.F_NAME, habitus.get(Attributes.F_NAME));
        addtional.put(Attributes.F_TYPE, habitus.get(Attributes.F_TYPE));
        addtional.put(Attributes.F_COL_TYPE, habitus.get(Attributes.F_COL_TYPE));
        return addtional;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
