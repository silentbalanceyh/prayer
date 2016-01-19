package com.prayer.schema.transfer;

import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.singleton;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.dao.impl.schema.JsonSerializer;
import com.prayer.exception.system.SerializationException;
import com.prayer.facade.dao.schema.Serializer;
import com.prayer.facade.schema.verifier.Attributes;
import com.prayer.model.meta.database.PEField;
import com.prayer.model.meta.database.PEKey;
import com.prayer.model.meta.database.PEMeta;
import com.prayer.util.io.IOKit;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * 测试序列化器
 * 
 * @author Lang
 *
 */
public class SerializerTestCase {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(SerializerTestCase.class);
    // ~ Instance Fields =====================================
    /** **/
    private transient Serializer serializer;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public SerializerTestCase() {
        this.serializer = singleton(JsonSerializer.class);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** Meta Serializer **/
    @Test
    public void testMetaSerializer() {
        final JsonObject meta = this.getJson().getJsonObject(Attributes.R_META);
        try {
            final PEMeta expected = new PEMeta(meta);
            final PEMeta actual = this.serializer.transferMeta(meta);
            assertEquals(expected, actual);
        } catch (SerializationException ex) {
            peError(LOGGER, ex);
            fail("[ERROR] Serialization Exception, __meta__ -> " + meta.encode());
        }
    }

    /** Keys Serializer **/
    @Test
    public void testKeysSerializer() {
        final JsonArray keys = this.getJson().getJsonArray(Attributes.R_KEYS);
        try {
            final List<PEKey> expected = new ArrayList<>();
            for (final Object key : keys) {
                if (null != key && JsonObject.class == key.getClass()) {
                    expected.add(new PEKey((JsonObject) key));
                }
            }
            final List<PEKey> actual = this.serializer.transferKeys(keys);
            final PEKey[] types = new PEKey[] {};
            assertArrayEquals(expected.toArray(types), actual.toArray(types));
        } catch (SerializationException ex) {
            peError(LOGGER, ex);
            fail("[ERROR] Serialization Exception, __keys__ -> " + keys.encode());
        }
    }

    /** Fields Serializer **/
    @Test
    public void testFieldsSerializer() {
        final JsonArray fields = this.getJson().getJsonArray(Attributes.R_FIELDS);
        try {
            final List<PEField> expected = new ArrayList<>();
            for (final Object field : fields) {
                if (null != field && JsonObject.class == field.getClass()) {
                    expected.add(new PEField((JsonObject) field));
                }
            }
            final List<PEField> actual = this.serializer.transferFields(fields);
            final PEField[] types = new PEField[] {};
            assertArrayEquals(expected.toArray(types), actual.toArray(types));
        } catch (SerializationException ex) {
            peError(LOGGER, ex);
            fail("[ERROR] Serialization Exception, __fields__ -> " + fields.encode());
        }
    }
    // ~ Private Methods =====================================

    private JsonObject getJson() {
        final String content = IOKit.getContent("/importer/serializer/role.json");
        final JsonObject raw = new JsonObject();
        if (null != content) {
            raw.mergeIn(new JsonObject(content));
        }
        return raw;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
