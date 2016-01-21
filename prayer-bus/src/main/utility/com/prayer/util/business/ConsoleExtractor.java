package com.prayer.util.business;

import com.prayer.model.business.Metadata;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class ConsoleExtractor {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 
     * @param metadata
     * @return
     */
    public static JsonObject extractMetadata(@NotNull final Metadata metadata) {
        final JsonObject retJson = new JsonObject();
        retJson.put("pname", metadata.getProductName());
        retJson.put("pversion", metadata.getProductVersion());
        retJson.put("dname", metadata.getDriverName());
        retJson.put("dversion", metadata.getDriverVersion());
        retJson.put("username", metadata.getUsername());
        return retJson;
    }
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private ConsoleExtractor(){}
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
