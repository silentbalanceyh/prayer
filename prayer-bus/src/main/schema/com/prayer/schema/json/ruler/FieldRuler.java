package com.prayer.schema.json.ruler;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Ruler;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * Field节点的规则器
 * 
 * @author Lang
 *
 */
@Guarded
public final class FieldRuler implements Ruler {

    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @Override
    public void apply(@NotNull final ObjectHabitus habitus) throws AbstractSchemaException {
        /** 3.1.1 验证Required属性 **/
        // Error-10001: __fields__ -> name
        // Error-10001: __fields__ -> type
        // Error-10001: __fields__ -> columnName
        // Error-10001: __fields__ -> columnType
        RulerHelper.applyRequired(habitus, FileConfig.CFG_FIELD);
        /** 3.1.2 验证Required属性的Pattern **/
        // Error-10003
        // name -> [A-Za-z]{1}[A-Za-z0-9]+
        // type -> (BooleanType|IntType|LongType|DecimalType|DateType|StringType|JsonType|XmlType|ScriptType|BinaryType)
        // columnName -> [A-Z]{1,3}\\_[A-Z]{1}[A-Z\\_0-9]*
        // columnType -> BOOLEAN|INT|LONG|DECIMAL|DATE|STRING|JSON|XML|SCRIPT|BINARY)[0-9]*
        RulerHelper.applyPattern(habitus, FileConfig.CFG_FIELD);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
