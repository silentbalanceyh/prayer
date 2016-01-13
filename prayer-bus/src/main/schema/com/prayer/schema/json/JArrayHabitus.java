package com.prayer.schema.json;

import java.util.ArrayList;
import java.util.List;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.exception.schema.JsonTypeConfusedException;
import com.prayer.exception.schema.ZeroLengthException;
import com.prayer.facade.schema.rule.ArrayHabitus;
import com.prayer.facade.schema.rule.ObjectHabitus;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class JArrayHabitus implements ArrayHabitus {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 维持一个拷贝，防止变更 **/
    private transient final JsonArray raw;
    /** **/
    private transient final String name;
    /** 正在操作的List对象 **/
    private transient List<ObjectHabitus> data = new ArrayList<>();
    /** 保留一个Error用于中间验证 **/
    private transient AbstractSchemaException error;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 使用JsonArray构造
     * 
     * @param data
     */
    public JArrayHabitus(@NotNull final JsonArray data, final String name) {
        this.name = name;
        this.raw = data.copy();
        this.reset();
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
    /** **/
    @Override
    public List<ObjectHabitus> objects() {
        return this.data;
    }

    /** **/
    @Override
    public ObjectHabitus get(@Min(0) int pos) {
        return this.data.get(pos);
    }

    /** **/
    @Override
    public AbstractSchemaException getError() {
        return this.error;
    }

    /** **/
    @Override
    public void reset() {
        this.data.clear();
        final JsonArray data = this.raw.copy();
        final int size = data.size();
        for (int pos = 0; pos < size; pos++) {
            final Object value = data.getValue(pos);
            if (null != value && JsonObject.class == value.getClass()) {
                final JsonObject obj = data.getJsonObject(pos);
                final ObjectHabitus habitus = new JObjectHabitus(obj);
                this.data.add(habitus);
            } else {
                // 1.初始化内置异常：10002
                this.error = new JsonTypeConfusedException(getClass(), "Array: " + this.name + "[" + pos + "] = " + value);
                break;
            }
        }
        // 2.并未发现JsonType异常，但没有任何元素: 10006
        if (null == this.error && this.data.isEmpty()) {
            this.error = new ZeroLengthException(getClass(), "Array : " + this.name);
        }
    }

}
