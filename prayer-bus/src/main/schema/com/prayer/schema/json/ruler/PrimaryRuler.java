package com.prayer.schema.json.ruler;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.constant.SystemEnum.MetaPolicy;
import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Ruler;
import com.prayer.facade.schema.verifier.Attributes;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class PrimaryRuler implements Ruler {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    private transient final MetaPolicy policy;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public PrimaryRuler(@NotNull final MetaPolicy policy) {
        this.policy = policy;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void apply(@NotNull final ObjectHabitus habitus) throws AbstractSchemaException {
        /** 4.3.1.主键类型 **/
        // GUID: StringType
        // Increment: LongType, IntType
        // ASSIGNED, COLLECTION: StringType, LongType, IntType
        applyDispatcher(habitus);
    }

    private void applyDispatcher(final ObjectHabitus habitus) throws AbstractSchemaException {
        final JsonObject addtional = this.getAddtional(habitus);
        if (MetaPolicy.GUID == this.policy) {
            /** (18.2.2.1) 4.3.1.1 GUID **/
            RulerHelper.applyIn(habitus, FileConfig.CFG_FPG, addtional);
        } else if (MetaPolicy.INCREMENT == this.policy) {
            /** (18.2.2.2) 4.3.1.2 INCREMENT **/
            RulerHelper.applyIn(habitus, FileConfig.CFG_FPI, addtional);
        } else if (MetaPolicy.ASSIGNED == this.policy) {
            /** (18.2.2.3 && 18.1.2) 4.3.1.3 ASSIGNED **/
            RulerHelper.applyIn(habitus, FileConfig.CFG_FPA, addtional);
        } else {
            /** (18.2.2.3 && 18.1.2) 4.3.1.3 COLLECTION **/
            RulerHelper.applyIn(habitus, FileConfig.CFG_FPC, addtional);
        }
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private JsonObject getAddtional(final ObjectHabitus habitus) {
        final JsonObject addtional = new JsonObject();
        addtional.put(Attributes.M_POLICY, this.policy.toString());
        addtional.put(Attributes.F_TYPE, habitus.get(Attributes.F_TYPE));
        addtional.put(Attributes.F_NAME, habitus.get(Attributes.F_NAME));
        return addtional;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
