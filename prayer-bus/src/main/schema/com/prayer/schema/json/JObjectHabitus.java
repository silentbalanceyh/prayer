package com.prayer.schema.json;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.RuleConstants;
import com.prayer.util.string.StringKit;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.InstanceOfAny;
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
    /** 设置Filter信息 **/
    private transient JsonObject filter;
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
        /**
         * 仅仅在对象构造的时候设置filter，Reset不会重新构造filter
         */
        this.filter = null;
        this.reset();
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public ConcurrentMap<String, Class<?>> types() {
        return this.types;
    }

    /** **/
    @Override
    public ConcurrentMap<String, Object> values() {
        final ConcurrentMap<String, Object> valueMap = new ConcurrentHashMap<>();
        /** 进行Data的计算 **/
        JsonObject iterateData = this.data.copy();
        if (iterateData.containsKey(RuleConstants.R_DATA)) {
            final Object item = iterateData.getValue(RuleConstants.R_DATA);
            if (JsonObject.class == item.getClass()) {
                iterateData = iterateData.getJsonObject(RuleConstants.R_DATA);
            }
        }
        /** 遍历最终拿到的Data **/
        for (final String field : iterateData.fieldNames()) {
            if (StringKit.isNonNil(field)) {
                valueMap.put(field, iterateData.getValue(field));
            }
        }
        return valueMap;
    }

    /**
     * 获取节点中的数据，双返回
     * 
     * @param clazz
     * @return
     */
    @Override
    @InstanceOfAny({ JsonObject.class, JsonArray.class })
    @SuppressWarnings("unchecked")
    public <T> T data() {
        T data = null;
        final JsonObject raw = this.data.copy();
        if (raw.containsKey(RuleConstants.R_DATA)) {
            final Object value = raw.getValue(RuleConstants.R_DATA);
            if (null != value) {
                if (JsonObject.class == value.getClass()) {
                    data = (T) raw.getJsonObject(RuleConstants.R_DATA);
                } else if (JsonArray.class == value.getClass()) {
                    data = (T) raw.getJsonArray(RuleConstants.R_DATA);
                }
            }
        } else {
            data = (T) raw;
        }
        return data;
    }

    /** **/
    @Override
    public JsonObject addtional() {
        JsonObject data = this.data.copy();
        JsonObject addtional = new JsonObject();
        if (data.containsKey(RuleConstants.R_ADDT)) {
            final Object item = data.getValue(RuleConstants.R_ADDT);
            if (JsonObject.class == item.getClass()) {
                addtional = data.getJsonObject(RuleConstants.R_ADDT);
            }
        }
        return addtional;
    }

    /** **/
    @Override
    public JsonArray fields() {
        final JsonArray fieldArr = new JsonArray();
        /** 这里生成的fieldArr一定不会重复，因为this.data.fieldNames()的返回值是Set<String> **/
        for (final String field : this.data.fieldNames()) {
            if (StringKit.isNonNil(field)) {
                fieldArr.add(field);
            }
        }
        return fieldArr;
    }

    /** **/
    @Override
    public JsonObject filter() {
        return this.filter;
    }

    /** **/
    @Override
    public void filter(@NotNull final JsonObject filter) {
        this.filter = filter;
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
