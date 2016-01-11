package com.prayer.schema.old.json; // NOPMD

import static com.prayer.util.debug.Log.debug;
import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.singleton;

import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.prayer.base.exception.AbstractException;
import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.base.exception.AbstractSystemException;
import com.prayer.base.exception.AbstractTransactionException;
import com.prayer.constant.Constants;
import com.prayer.constant.log.DebugKey;
import com.prayer.exception.system.SerializationException;
import com.prayer.exception.system.TypeInitException;
import com.prayer.facade.dao.metadata.SchemaDao;
import com.prayer.facade.schema.ExternalEnsurer;
import com.prayer.facade.schema.OldImporter;
import com.prayer.facade.schema.Serializer;
import com.prayer.model.database.PEField;
import com.prayer.model.database.PEKey;
import com.prayer.model.database.PEMeta;
import com.prayer.model.kernel.GenericSchema;
import com.prayer.model.kernel.SchemaExpander;
import com.prayer.schema.old.CommunionSerializer;
import com.prayer.util.io.JsonKit;

import net.sf.oval.constraint.AssertFieldConstraints;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;
import net.sf.oval.guard.Pre;

/**
 * Json中的导入器
 * 
 * @author Lang
 * @see
 */
@Guarded
public class CommunionImporter implements OldImporter {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(CommunionImporter.class);
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    @NotEmpty
    @NotBlank
    private transient String filePath; // NOPMD
    /** **/
    @NotNull
    private transient ExternalEnsurer ensurer;
    /** **/
    @NotNull
    private transient Serializer serializer;
    /** **/
    @NotNull
    private transient SchemaDao schemaDao;
    /** **/
    private transient JsonNode rawData;
    /** **/
    private transient GenericSchema schema;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param filePath
     */
    @PostValidateThis
    public CommunionImporter(@AssertFieldConstraints("filePath") final String filePath) {
        this.initialize(filePath);
        if (null == this.filePath) {
            final AbstractException error = new TypeInitException(getClass(), "Constructor: CommunionImporter(String)",
                    this.filePath);
            peError(LOGGER, error);
        }
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 初始化文件读取的过程
     */
    @Override
    @Pre(expr = "_this.filePath != null && _this.ensurer != null", lang = Constants.LANG_GROOVY)
    public void readSchema() throws AbstractSystemException {
        final JsonNode schemaData = JsonKit.readJson(this.filePath);
        /**
         * JsonKit.readJson本身会抛出AbstractSystemException
         * <code>-20002: ResourceIOException</code>
         * <code>-20003：JsonParserException</code>
         * 如果异常会中断直接抛出异常，否则继续，如果读取的数据为null则也表示读取异常
         */
        if (null != schemaData) {
            this.ensurer.refreshData(schemaData);
        }
    }

    /**
     * 验证Schema文件信息
     */
    @Override
    @Pre(expr = "_this.ensurer != null", lang = Constants.LANG_GROOVY)
    public void ensureSchema() throws AbstractSchemaException {
        if (this.ensurer.validate()) {
            this.rawData = this.ensurer.getRaw();
        } else {
            if (null != this.ensurer.getError()) {
                throw this.ensurer.getError();
            }
        }
    }

    /**
     * 
     */
    @Override
    @PostValidateThis
    public void refreshSchema(@AssertFieldConstraints("filePath") final String filePath) {
        this.initialize(filePath);
        if (null == this.filePath) {
            final AbstractException error = new TypeInitException(getClass(), "void refreshSchema(String)",
                    this.filePath);
            peError(LOGGER, error);
        }
    }

    /**
     * 
     */
    @Override
    @Pre(expr = "_this.schema != null && _this.rawData != null && _this.serializer != null", lang = Constants.LANG_GROOVY)
    public GenericSchema transformSchema() throws SerializationException {
        final PEMeta meta = this.readMeta();
        try {
            this.schema.setMeta(meta);
            this.schema.setIdentifier(meta.getGlobalId());
            this.schema.setKeys(this.readKeys());
            this.schema.setFields(this.readFields());
        } catch (SerializationException ex) {
            peError(LOGGER, ex);
            this.schema = null; // NOPMD
        }
        return schema;
    }

    /**
     * 
     */
    @Override
    @Pre(expr = "_this.schemaDao != null", lang = Constants.LANG_GROOVY)
    public boolean syncSchema(@NotNull final GenericSchema schema) throws AbstractTransactionException {
        GenericSchema retSchema = null;
        if (null == this.schemaDao.getById(schema.getIdentifier())) {
            debug(LOGGER, DebugKey.INFO_SMA_SYNC, "New Build", "Creating", schema.getMeta());
            retSchema = schemaDao.create(schema);
        } else {
            debug(LOGGER, DebugKey.INFO_SMA_SYNC, "Sync", "Updating", schema.getMeta());
            retSchema = schemaDao.synchronize(schema);
        }
        boolean result = false;
        if (null != retSchema) {
            result = true;
        }
        return result;
    }

    /** **/
    @Override
    @Pre(expr = "_this.schema != null", lang = Constants.LANG_GROOVY)
    public GenericSchema getSchema() {
        return this.schema;
    }

    /**
     * 
     */
    @Override
    @Pre(expr = "_this.ensurer != null", lang = Constants.LANG_GROOVY)
    public ExternalEnsurer getEnsurer() {
        return this.ensurer;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private void initialize(final String filePath) {
        this.filePath = filePath;
        this.schema = new GenericSchema();
        // Singleton
        this.ensurer = singleton(GenericEnsurer.class);
        this.serializer = singleton(CommunionSerializer.class);
        //this.schemaDao = singleton(SchemaDaoImpl.class);
    }

    private PEMeta readMeta() throws SerializationException {
        return this.serializer.readMeta(this.rawData.path("__meta__"));
    }

    private ConcurrentMap<String, PEField> readFields() throws SerializationException {
        return SchemaExpander
                .toFieldsMap(this.serializer.readFields(JsonKit.fromJObject(this.rawData.path("__fields__"))));
    }

    private ConcurrentMap<String, PEKey> readKeys() throws SerializationException {
        return SchemaExpander.toKeysMap(this.serializer.readKeys(JsonKit.fromJObject(this.rawData.path("__keys__"))));
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
