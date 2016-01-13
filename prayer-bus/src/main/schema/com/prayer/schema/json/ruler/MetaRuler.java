package com.prayer.schema.json.ruler;

import static com.prayer.util.Converter.fromStr;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.constant.DBConstants;
import com.prayer.constant.Resources;
import com.prayer.constant.SystemEnum.Category;
import com.prayer.constant.SystemEnum.Mapping;
import com.prayer.constant.SystemEnum.MetaPolicy;
import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Ruler;
import com.prayer.facade.schema.verifier.Attributes;
import com.prayer.util.string.StringKit;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * Meta节点的规则器
 * 
 * @author Lang
 *
 */
@Guarded
public final class MetaRuler implements Ruler {

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
        /** 2.1.验证Required属性 **/
        // Error-10001: __meta__ -> name
        // Error-10001: __meta__ -> namespace
        // Error-10001: __meta__ -> category
        // Error-10001: __meta__ -> table
        // Error-10001: __meta__ -> identifier
        // Error-10001: __meta__ -> mapping
        // Error-10001: __meta__ -> policy
        RulerHelper.applyRequired(habitus, FileConfig.CFG_META);
        /** 2.2.验证不支持的属性 **/
        // Error-10017:
        // Required: name, namespace, category, table, identifier, mapping,
        // policy
        // Optional: subtable, subkey, seqname, seqstep, seqinit, status
        RulerHelper.applyUnsupport(habitus, FileConfig.CFG_META);
        /** 2.3.验证Pattern值的信息 **/
        // Error-10003
        // name -> [A-Z]{1}[A-Za-z0-9]+,
        // namespace -> [a-z]+(\\.[a-z]+)*,
        // category -> (ENTITY|RELATION){1},
        // identifier -> [a-z]{2,4}(\\.[a-z0-9]{2,})+,
        // mapping -> (DIRECT|COMBINATED|PARTIAL){1},
        // policy -> (GUID|INCREMENT|ASSIGNED|COLLECTION){1},
        // status -> (SYSTEM|USER|DISABLED){1},
        // table -> [A-Z]{2,4}\\_[A-Z\\_0-9]*,
        // subtable -> [A-Z]{2,4}\\_[A-Z\\_0-9]*,
        // seqname -> SEQ_[A-Z\\_0-9]*,
        // seqinit -> [0-9]+,
        // seqstep -> [0-9]+
        RulerHelper.applyPattern(habitus, FileConfig.CFG_META);
        /** 2.4.根据Category分流操作 **/
        applyDispatcher(habitus);
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private void applyDispatcher(final ObjectHabitus habitus) throws AbstractSchemaException {
        final Category category = fromStr(Category.class, habitus.get(Attributes.M_CATEGORY));
        final Mapping mapping = fromStr(Mapping.class, habitus.get(Attributes.M_MAPPING));
        // Category, Mapping
        switch (category) {
        case ENTITY: {
            // Mapping
            switch (mapping) {
            case PARTIAL: {
                verifyCEntityPartial(habitus);
            }
                break;
            case DIRECT: {
                verifyCEntityDirect(habitus);
            }
                break;
            case COMBINATED: {
                verifyCEntityCombinated(habitus);
            }
                break;
            }
        }
            break;
        case RELATION: {
            verifyCRelation(habitus);
        }
            break;
        }
        // 仅检查INCREMENT
        final MetaPolicy policy = fromStr(MetaPolicy.class, habitus.get(Attributes.M_POLICY));
        if(MetaPolicy.INCREMENT == policy){
            // policy == INCREMENT
            verifyIncrement(habitus);
        }
    }
    
    /** policy == INCREMENT **/
    private void verifyIncrement(final ObjectHabitus habitus) throws AbstractSchemaException{
        /** (8) 2.4.5 SeqName for Oracle/PgSQL **/
        if(StringKit.equals(Resources.DB_CATEGORY, DBConstants.CATEGORY_ORACLE)
                || StringKit.equals(Resources.DB_CATEGORY, DBConstants.CATEGORY_PGSQL)){
            /** (9.2.1) 2.4.5.1. 使用Sequence的情况，必须校验seqname是否存在 **/
            // Required : seqname
            RulerHelper.applyExisting(habitus, FileConfig.CFG_M_PIOG);
            /** 【SKIP】(9.2.2) seqname格式，在2.3中已验证 **/
        }
        /** (10) 如果Increment验证seqinit，seqstep是否同时存在 **/
        // Required : seqinit, seqstep
        RulerHelper.applyExisting(habitus, FileConfig.CFG_M_PI);
        /** 【SKIP】(11) 在2.3中已验证 **/
    }

    /** category = ENTITY && mapping = COMBINATED **/
    private void verifyCEntityCombinated(final ObjectHabitus habitus) throws AbstractSchemaException {
        /** (7.4.1) 2.4.4.1 **/
        // Required : subkey, subtable
        RulerHelper.applyExisting(habitus, FileConfig.CFG_M_EC);
        /** 【SKIP】(7.4.2) 2.4.4.2 Patterns，在2.3中会校验 **/
        /** (7.4.3) 2.4.4.3 Not Same **/
        // Not Same : table, subtable
        RulerHelper.applyDiff(habitus, FileConfig.CFG_M_EC);
        /** (7.4.4) 2.4.4.4 Table Does Not Exist **/
        // Db Table : subtable
        RulerHelper.applyDBTable(habitus, FileConfig.CFG_M_EC);
        /** (7.4.5) 2.4.4.5 Column Does Not Exist **/
        // Db Column : subkey
        RulerHelper.applyDBColumn(habitus, FileConfig.CFG_M_EC);
        /** (7.4.6) 2.4.4.6 Constraint OK ? **/
        // Db Column : subkey
        RulerHelper.applyDBConstraint(habitus, FileConfig.CFG_M_EC);
    }

    /** category = ENTITY && mapping = DIRECT **/
    private void verifyCEntityDirect(final ObjectHabitus habitus) throws AbstractSchemaException {
        /** (7.3.1) 2.4.3.1 **/
        // Forbidden : subkey, subtable
        RulerHelper.applyExclude(habitus, FileConfig.CFG_M_ED);
    }

    /** category = ENTITY && mapping = PARTIAL **/
    private void verifyCEntityPartial(final ObjectHabitus habitus) throws AbstractSchemaException {
        /** (7.2.1 && 7.2.2) 2.4.2.1 **/
        // Forbidden : subkey, subtable, seqname, seqinit, seqstep
        RulerHelper.applyExclude(habitus, FileConfig.CFG_M_EP);
        /** (7.2.3) 2.4.2.2 policy in ASSIGNED **/
        RulerHelper.applyIn(habitus, FileConfig.CFG_M_EP);
    }

    /** category = RELATION **/
    private void verifyCRelation(final ObjectHabitus habitus) throws AbstractSchemaException {
        /** (7.1.1) 2.4.1.1 subkey, subtable forbidden **/
        RulerHelper.applyExclude(habitus, FileConfig.CFG_M_REL);
        /** (7.1.2) 2.4.1.2 mapping in DIRECT **/
        RulerHelper.applyIn(habitus, FileConfig.CFG_M_REL);
        /** (7.1.3) 2.4.1.3 policy not in ASSIGNED **/
        RulerHelper.applyNotIn(habitus, FileConfig.CFG_M_REL);
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
