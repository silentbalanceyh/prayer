package com.prayer.business.impl.ordered;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.prayer.facade.schema.verifier.Attributes;

import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class SimpleExtractor {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 读取当前节点的表名
     * 
     * @param data
     * @return
     */
    public static String getTable(@NotNull final JsonObject data) {
        return extractAttr(data, Attributes.M_TABLE);
    }

    /**
     * 读取当前节点的Global ID
     * 
     * @param data
     * @return
     */
    public static String getIdentifier(@NotNull final JsonObject data) {
        return extractAttr(data, Attributes.M_IDENTIFIER);
    }

    /**
     * 
     * @param data
     * @return
     */
    public static Map<String, OrderNode> getRefs(@NotNull final JsonObject data) {
        final Map<String, OrderNode> ret = new LinkedHashMap<>();
        final Set<String> tables = extractRefs(data);
        for (final String key : tables) {
            ret.put(key, null);
        }
        return ret;
    }
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    
    private SimpleExtractor(){}

    private static Set<String> extractRefs(final JsonObject data) {
        final JsonArray arrays = data.getJsonArray(Attributes.R_FIELDS);
        final Set<String> references = new HashSet<>();
        final int size = arrays.size();
        for (int idx = 0; idx < size; idx++) {
            try {
                final JsonObject field = arrays.getJsonObject(idx);
                if (field.containsKey(Attributes.F_REF_TABLE)) {
                    references.add(field.getString(Attributes.F_REF_TABLE));
                }
            } catch (DecodeException ex) {
                continue;
            }
        }
        return references;
    }

    /**
     * 从__meta__中提取数据
     * 
     * @param data
     * @param key
     * @return
     */
    private static String extractAttr(final JsonObject data, final String key) {
        final JsonObject meta = data.getJsonObject(Attributes.R_META);
        String retValue = null;
        if (null != meta && meta.containsKey(key)) {
            retValue = meta.getString(key);
        }
        return retValue;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
