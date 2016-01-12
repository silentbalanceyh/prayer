package com.prayer.schema.json;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.facade.schema.rule.ObjectHabitus;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 从JsonObject继承，用于描述JsonObject的状态信息
 * 
 * @author Lang
 *
 */
@Guarded
public class JObjectHabitus extends JsonObject implements ObjectHabitus {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    private transient final JsonObject data;
    /** **/
    private transient final ConcurrentMap<String, Class<?>> types;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 使用一个节点构造对应的Habitus
     * 
     * @param data
     */
    public JObjectHabitus(@NotNull final JsonObject data) {
        this.data = data;
        this.types = calculate(data);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     * @return
     */
    @Override
    public ConcurrentMap<String,Class<?>> typeMap(){
        return this.types;
    }
    /**
     *  
     */
    @Override
    public Set<String> fields(){
        return this.data.fieldNames();
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
