package com.prayer.schema.json.ruler;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.facade.schema.rule.ArrayHabitus;
import com.prayer.facade.schema.rule.ArrayRuler;
import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Ruler;
import com.prayer.facade.schema.verifier.Attributes;
import com.prayer.fantasm.exception.AbstractSchemaException;
import com.prayer.model.type.DataType;
import com.prayer.schema.json.JObjectHabitus;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 类型规则器，根据规则的差异处理不同的配置
 * 
 * @author Lang
 *
 */
@Guarded
public final class TypesRuler implements ArrayRuler {

    // ~ Static Fields =======================================
    /** 防止创建同一个类型重复的Ruler **/
    private static ConcurrentMap<DataType, Ruler> TYPE_RULER = new ConcurrentHashMap<>();

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void apply(@NotNull final ArrayHabitus habitus) throws AbstractSchemaException {
        /** 7.0.优先处理habitus中的容器异常：10002/10006 **/
        habitus.ensure();
        /** 7.1.根据不同类型字段处理不同的Ruler **/
        verifyTypes(habitus);
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private void verifyTypes(final ArrayHabitus habitus) throws AbstractSchemaException {
        for (final Object value : habitus.data()) {
            if (null != value) {
                /** 1.构造基础数据对象 **/
                final JsonObject data = (JsonObject) value;
                final ObjectHabitus field = new JObjectHabitus(data);
                /** 2.构造Internal的Ruler **/
                final DataType type = DataType.fromString(field.get(Attributes.F_TYPE).toString());
                if (null != type) {
                    Ruler ruler = TYPE_RULER.get(type);
                    if (null == ruler) {
                        ruler = new TypeRuler(type);
                        TYPE_RULER.put(type, ruler);
                    }
                    /** 3.使用Ruler调用类型中的规则 **/
                    ruler.apply(field);
                }
            }
        }
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
