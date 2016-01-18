package com.prayer.schema.old.workflow; // NOPMD

import static com.prayer.util.debug.Log.peError;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.base.exception.AbstractSystemException;
import com.prayer.base.exception.AbstractTransactionException;
import com.prayer.exception.system.SerializationException;
import com.prayer.model.crucial.GenericSchema;
import com.prayer.model.meta.database.PEField;
import com.prayer.model.meta.database.PEKey;
import com.prayer.model.meta.database.PEMeta;
import com.prayer.schema.old.json.CommunionImporter;
import com.prayer.util.io.JsonKit;

/**
 * 
 * @author Lang
 *
 */
public class _17SchemaCreateTestCase extends AbstractSchemaTestCase { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(_17SchemaCreateTestCase.class);

    // ~ Instance Fields =====================================
    /** **/
    private transient JsonNode rootNode = null; // NOPMD

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    // ~ Methods =============================================
    /**
     * 
     */
    @Before
    public void setUp() {
        oldImporter = new CommunionImporter("/schema/system/data/json/role.json");
        // 1.Read Schema File
        try {
            oldImporter.readSchema();
        } catch (AbstractSystemException ex) {
            peError(getLogger(), ex);
            failure(ex.toString());
        }
        // 2.Validate Schema File
        try {
            oldImporter.ensureSchema();
        } catch (AbstractSchemaException ex) {
            peError(getLogger(), ex);
            failure(ex.toString());
        }
        // 3.Extract Raw Data
        this.rootNode = this.oldImporter.getEnsurer().getRaw();
    }

    /**
     * 
     */
    @Test
    public void testMetaDeserialize() {
        if (null != this.rootNode) {
            final JsonNode metaNode = this.rootNode.path("__meta__");
            try {
                final PEMeta meta = this.serializer.readMeta(metaNode);
                assertNotNull("[T-ERROR] Serialization result should not be null !", meta);
            } catch (SerializationException ex) {
                peError(getLogger(), ex);
                failure("[T-ERROR] Serialization Exception, \"__meta__\" = " + metaNode.toString());
            }
        }
    }

    /**
     * 
     */
    @Test
    public void testKeysDeserialize() {
        if (null != this.rootNode) {
            final ArrayNode keysNode = JsonKit.fromJObject(this.rootNode.path("__keys__"));
            try {
                final List<PEKey> keys = this.serializer.readKeys(keysNode);
                // String Result
                if (!keys.isEmpty()) {
                    final StringBuilder builder = new StringBuilder();
                    for (final PEKey key : keys) {
                        builder.append(key).append(" | ");
                    }
                }
                assertEquals("[T-ERROR] Serialization result size should be the same.", 3, keys.size());
            } catch (SerializationException ex) {
                peError(getLogger(), ex);
                failure("[T-ERROR] Serialization Exception, \"__keys__\" = " + keysNode.toString());
            }
        }
    }

    /**
     * 
     */
    @Test
    public void testFieldsDeserialize() {
        if (null != this.rootNode) {
            final ArrayNode fieldsNode = JsonKit.fromJObject(this.rootNode.path("__fields__"));
            try {
                final List<PEField> fields = this.serializer.readFields(fieldsNode);
                // String Result
                if (!fields.isEmpty()) {
                    final StringBuilder builder = new StringBuilder();
                    for (final PEField field : fields) {
                        builder.append(field).append(" | ");
                    }
                }
                assertEquals("[T-ERROR] Serialization result size should not be the same.", 5, fields.size());
            } catch (SerializationException ex) {
                peError(getLogger(), ex);
                failure("[T-ERROR] Serialization Exception, \"__fields__\" = " + fieldsNode.toString());
            }
        }
    }

    /**
     * 
     */
    @Test
    public void testTransformModel() {
        GenericSchema schema = null;
        try {
            schema = this.oldImporter.transformSchema();
        } catch (SerializationException ex) {
            peError(getLogger(), ex);
            failure("[T-ERROR] Searialization Exception. ");
        }
        assertNotNull("[T-ERROR] Deserialization result must not be null.", schema);
    }

    /**
     * 
     */
    @Test
    public void testDataLoading() {
        GenericSchema schema = null;
        boolean result = false;
        try {
            schema = this.oldImporter.transformSchema();
            final GenericSchema prepSchema = this.service.getById("sys.sec.role");
            if (null == prepSchema) {
                result = this.oldImporter.syncSchema(schema);
            } else {
                // Skip Test Case
                result = true;
            }
        } catch (SerializationException ex) {
            peError(getLogger(), ex);
        } catch (AbstractTransactionException ex) {
            peError(getLogger(), ex);
            failure("[T-ERROR] Data Loading Exception. Loading Data...");
        }
        assertTrue("[T-ERROR] Data loading result must be \"true\".", result);
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
