package com.prayer.schema.json.ruler;

import com.prayer.facade.schema.rule.ArrayHabitus;
import com.prayer.facade.schema.rule.ArrayRuler;
import com.prayer.fantasm.exception.AbstractSchemaException;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 数据库类型转换验证
 * 
 * @author Lang
 *
 */
@Guarded
public final class UpdatingRuler implements ArrayRuler {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    // Key：当前需要检查的字段名
    // Value：它允许的旧的类型表，即H2中存储的允许出现的类型信息
    @NotNull
    private transient JsonObject allowedTypes;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 为当前Ruler中需要使用的类型规则赋值
     * 
     * @param allowedTypes
     */
    @PostValidateThis
    public UpdatingRuler(@NotNull final JsonObject allowedTypes) {
        this.allowedTypes = allowedTypes;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    // 该方法用于验证habitus中的类型信息，主要集中于columnType的验证，allowedTypes中为Hash表
    // TargetType: ( Type1, Type2, Type3 )
    // allowedTypes直接从配置文件中读取，不同的数据库其内容映射会不同；
    // TargetType此处有两层含义：
    // 1) 目标类型（新读取的Schema的columnType）——来自Json文件
    // 2) 根据不同的数据库读取到的目标类型有所不同
    // Type1, Type2, Type3
    // 允许转换的类型（旧的Schema中的columnType）——来自H2 Database
    // 如果habitus中每个字段满足对应的Type1,Type2,Type3的包含条件则证明数据库的更新是合法的，否则SQL更新生成会出错
    // Example:
    // TargetType = VARCHAR
    // 表示转换成的新的Schema类型为VARCHAR，那么能够转换成VARCHAR的类型为Type1, Type2, Type2，和通常逻辑是反向操作
    // ArrayHabitus：里面存储的字段信息是H2 Database即旧的字段信息，而不是验证的新的字段信息
    @Override
    public void apply(@NotNull final ArrayHabitus habitus) throws AbstractSchemaException {
        /** 38.0. 先处理ArrayHabitus中的容器异常 **/
        habitus.ensure();
        /** 38.1. 验证DB Updating **/
        // Error 10038
        RulerHelper.applyDBUpdates(habitus, FileConfig.CFG_UPS, this.allowedTypes);
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
