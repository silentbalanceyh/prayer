package com.test.schema;

import static com.prayer.util.Error.info;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.prayer.exception.AbstractSchemaException;
import com.prayer.exception.AbstractSystemException;
import com.prayer.exception.system.SerializationException;
import com.prayer.mod.meta.FieldModel;
import com.prayer.mod.meta.KeyModel;
import com.prayer.mod.meta.MetaModel;
import com.prayer.schema.json.internal.CommunionImporter;
import com.prayer.util.JsonKit;

/**
 * 
 * @author Lang
 *
 */
public class _17DeserializerTestCase extends AbstractSchemaTestCase { // NOPMD
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(_17DeserializerTestCase.class);

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
		importer = new CommunionImporter("/schema/system/data/json/role.json");
		// 1.Read Schema File
		try {
			importer.importFile();
		} catch (AbstractSystemException ex) {
			if (getLogger().isDebugEnabled()) {
				getLogger().debug("1.Error when reading json schema file.", ex);
			}
			failure(ex.toString());
		}
		// 2.Validate Schema File
		try {
			importer.ensureSchema();
		} catch (AbstractSchemaException ex) {
			if (getLogger().isDebugEnabled()) {
				getLogger().debug("2.Error when verifying json schema.", ex);
			}
			failure(ex.toString());
		}
		// 3.Extract Raw Data
		this.rootNode = this.importer.getEnsurer().getRaw();
	}

	/**
	 * 
	 */
	@Test
	public void testMetaDeserialize() {
		if (null != this.rootNode) {
			final JsonNode metaNode = this.rootNode.path("__meta__");
			try {
				final MetaModel meta = this.serializer.readMeta(metaNode);
				info(getLogger(), "Json Data : \"__meta__\" = " + metaNode.toString() + ", Model Data : " + meta);
				assertNotNull("[T] Serialization result should not be null !", meta);
			} catch (SerializationException ex) {
				info(getLogger(), "Serialization Exception, \"__meta__\" = " + metaNode.toString(), ex);
				failure("Serialization Exception, \"__meta__\" = " + metaNode.toString());
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
				final List<KeyModel> keys = this.serializer.readKeys(keysNode);
				// String Result
				if (!keys.isEmpty()) {
					final StringBuilder builder = new StringBuilder();
					for (final KeyModel key : keys) {
						builder.append(key).append(" | ");
					}
					info(getLogger(), "Json Data: \"__keys__\" = " + keysNode.toString() + ", Keys List Data : "
							+ builder.toString());
				}
				assertEquals("[T] Serialization result size should be the same.", 3, keys.size());
			} catch (SerializationException ex) {
				info(getLogger(), "Serialization Exception, \"__keys__\" = " + keysNode.toString(), ex);
				failure("Serialization Exception, \"__keys__\" = " + keysNode.toString());
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
				final List<FieldModel> fields = this.serializer.readFields(fieldsNode);
				// String Result
				if (!fields.isEmpty()) {
					final StringBuilder builder = new StringBuilder();
					for (final FieldModel field : fields) {
						builder.append(field).append(" | ");
					}
					info(getLogger(), "Json Data: \"__fields__\" = " + fieldsNode.toString() + ", Fields List Data : "
							+ builder.toString());
				}
				assertEquals("[T] Serialization result size should not be the same.", 5, fields.size());
			} catch (SerializationException ex) {
				info(getLogger(), "Serialization Exception, \"__fields__\" = " + fieldsNode.toString(), ex);
				failure("Serialization Exception, \"__fields__\" = " + fieldsNode.toString());
			}
		}
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}