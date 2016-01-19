package com.prayer.schema.transfer;

import static com.prayer.util.debug.Log.debug;
import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.singleton;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.exception.AbstractException;
import com.prayer.base.exception.AbstractSystemException;
import com.prayer.dao.impl.schema.CommuneImporter;
import com.prayer.dao.impl.schema.JsonSerializer;
import com.prayer.facade.dao.schema.Importer;
import com.prayer.facade.dao.schema.Serializer;
import com.prayer.facade.schema.Schema;
import com.prayer.facade.schema.verifier.Attributes;
import com.prayer.model.crucial.schema.JsonSchema;
import com.prayer.model.meta.database.PEField;
import com.prayer.model.meta.database.PEKey;
import com.prayer.util.io.IOKit;

import io.vertx.core.json.JsonObject;

/**
 * 测试Importer
 * 
 * @author Lang
 *
 */
public class ImporterTestCase {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(ImporterTestCase.class);
    // ~ Instance Fields =====================================
    /** **/
    private transient Importer importer;
    /** 序列化生成器 **/
    private transient Serializer serializer;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    public ImporterTestCase() {
        this.importer = singleton(CommuneImporter.class);
        this.serializer = singleton(JsonSerializer.class);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** Test Importing **/
    @Test
    public void testImporting() throws AbstractException {
        final Schema expected = this.getSchema();
        final Schema actual = this.importer.readFrom("/importer/serializer/role.json");
        expected.equals(actual);
        debug(LOGGER, actual.toString());
        assertEquals(expected, actual);
    }

    // ~ Private Methods =====================================
    private Schema getSchema() {
        final String content = IOKit.getContent("/importer/serializer/role.json");
        Schema schema = new JsonSchema();
        if (null != content) {
            try {
                final JsonObject data = new JsonObject(content);
                /** 1.构造Meta **/
                schema.meta(this.serializer.transferMeta(data.getJsonObject(Attributes.R_META)));
                /** 2.构造Keys **/
                schema.keys(this.serializer.transferKeys(data.getJsonArray(Attributes.R_KEYS)).toArray(new PEKey[] {}));
                /** 3.构造Fields **/
                schema.fields(this.serializer.transferFields(data.getJsonArray(Attributes.R_FIELDS))
                        .toArray(new PEField[] {}));
            } catch (AbstractSystemException ex) {
                peError(LOGGER, ex);
            }
        }
        return schema;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
