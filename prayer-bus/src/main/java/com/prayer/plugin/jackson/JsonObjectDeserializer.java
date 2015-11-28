package com.prayer.plugin.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public class JsonObjectDeserializer extends JsonDeserializer<JsonObject> { // NOPMD
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public JsonObject deserialize(final JsonParser parser, final DeserializationContext context)
            throws IOException, JsonProcessingException {
        final ObjectCodec codec = parser.getCodec();
        final JsonNode node = codec.readTree(parser);
        return new JsonObject(node.toString());
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
