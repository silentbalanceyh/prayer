package com.prayer.schema.json.internal; // NOPMD

import static com.prayer.util.Error.info;
import static com.prayer.util.Instance.singleton;

import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.prayer.dao.schema.SchemaDao;
import com.prayer.dao.schema.impl.SchemaDaoImpl;
import com.prayer.exception.AbstractSchemaException;
import com.prayer.exception.AbstractSystemException;
import com.prayer.exception.system.DataLoadingException;
import com.prayer.exception.system.SerializationException;
import com.prayer.exception.system.TypeInitException;
import com.prayer.mod.meta.FieldModel;
import com.prayer.mod.meta.GenericSchema;
import com.prayer.mod.meta.KeyModel;
import com.prayer.mod.meta.MetaModel;
import com.prayer.schema.ExternalEnsurer;
import com.prayer.schema.Importer;
import com.prayer.schema.Serializer;
import com.prayer.schema.json.CommunionSerializer;
import com.prayer.util.JsonKit;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;
import net.sf.oval.guard.Pre;
import net.sf.oval.guard.PreValidateThis;

/**
 * Json中的导入器
 * 
 * @author Lang
 * @see
 */
@Guarded
public class CommunionImporter implements Importer {
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(CommunionImporter.class);
	// ~ Instance Fields =====================================
	/** **/
	@NotNull
	private transient String filePath;
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
	public CommunionImporter(@NotNull @NotEmpty @NotBlank final String filePath) {
		this.initialize(filePath);
		if (null == this.filePath) {
			info(LOGGER, "[E] File path initializing met error!",
					new TypeInitException(getClass(), "Constructor: CommunionImporter(String)", this.filePath));
		}
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/**
	 * 初始化文件读取的过程
	 */
	@Override
	@PreValidateThis
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
	@PreValidateThis
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
	@PreValidateThis
	public void refreshSchema(@NotNull @NotEmpty @NotBlank final String filePath) {
		this.initialize(filePath);
		if (null == this.filePath) {
			info(LOGGER, "[E] File path initializing met error!",
					new TypeInitException(getClass(), "void refreshSchema(String)", this.filePath));
		}
	}

	/**
	 * 
	 */
	@Override
	@Pre(expr = "_this.schema != null && _this.rawData != null", lang = "groovy")
	public GenericSchema transformSchema() throws SerializationException {
		final MetaModel meta = this.readMeta();
		try {
			schema.setMeta(meta);
			schema.setIdentifier(meta.getGlobalId());
			schema.setKeys(this.readKeys());
			schema.setFields(this.readFields());
		} catch (SerializationException ex) {
			info(LOGGER, "Serialization Exception Happen! Data = " + this.rawData.toString(), ex);
			this.schema = null; // NOPMD
		}
		return schema;
	}

	/**
	 * 
	 */
	@Override
	@PreValidateThis
	public boolean syncSchema(@NotNull final GenericSchema schema) throws DataLoadingException {
		GenericSchema retSchema = null;
		info(LOGGER, "[I] UniqueId = " + schema.getMeta().getUniqueId());
		if (null == this.schemaDao.getById(schema.getIdentifier())) {
			info(LOGGER, "[I] Going to Build Model: Create Process! Input Meta: " + schema.getMeta());
			retSchema = schemaDao.create(schema);
		} else {
			info(LOGGER, "[I] Going to Sync Model: Update Process! Input Meta: " + schema.getMeta());
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
	public GenericSchema getSchema() {
		return this.schema;
	}

	/**
	 * 
	 */
	@Override
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
		this.schemaDao = singleton(SchemaDaoImpl.class);
	}

	private MetaModel readMeta() throws SerializationException {
		return this.serializer.readMeta(this.rawData.path("__meta__"));
	}

	private ConcurrentMap<String, FieldModel> readFields() throws SerializationException {
		return GenericSchema
				.getFieldsMap(this.serializer.readFields(JsonKit.fromJObject(this.rawData.path("__fields__"))));
	}

	private ConcurrentMap<String, KeyModel> readKeys() throws SerializationException {
		return GenericSchema.getKeysMap(this.serializer.readKeys(JsonKit.fromJObject(this.rawData.path("__keys__"))));
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
