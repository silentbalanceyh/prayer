package com.prayer.schema.json;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.facade.schema.rule.ObjectHabitus;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 从JsonObject继承，用于描述JsonObject的状态信息
 * 
 * @author Lang
 *
 */
@Guarded
public class JObjectHabitus implements ObjectHabitus {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 维持一个拷贝，防止变更 **/
    private transient final JsonObject raw;

    /** **/
    private transient JsonObject data;
    /** **/
    private transient ConcurrentMap<String, Class<?>> types;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 使用一个节点构造对应的Habitus
     * 
     * @param data
     */
    public JObjectHabitus(@NotNull final JsonObject data) {
        this.raw = data.copy();
        // 统一操作，通过Reset的过程创建节点拷贝，每次Apply时会reset
        this.reset();
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public ConcurrentMap<String, Class<?>> typeMap() {
        return this.types;
    }

    /** **/
    @Override
    public Set<String> fields() {
        return this.data.fieldNames();
    }
    /** **/
    @Override
    public JsonObject getRaw(){
        return this.raw.copy();
    }

    /** **/
    @Override
    public void reset() {
        this.data = this.raw.copy();
        this.types = calculate(this.data);
    }

    /** **/
    @Override
    public String get(@NotNull @NotBlank @NotEmpty final String field) {
        final Class<?> type = this.types.get(field);
        String literal = null;
        if (JsonObject.class != type && JsonArray.class != type) {
            final Object value = this.data.getValue(field);
            if (null != value) {
                literal = value.toString();
            }
        }
        return literal;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private ConcurrentMap<String, Class<?>> calculate(final JsonObject data) {
        final ConcurrentMap<String, Class<?>> retMap = new ConcurrentHashMap<>();
        for (final String attr : data.fieldNames()) {
            retMap.put(attr, data.getValue(attr).getClass());
        }
        return retMap;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
