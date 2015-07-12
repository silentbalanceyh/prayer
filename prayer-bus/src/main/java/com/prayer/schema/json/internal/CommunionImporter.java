package com.prayer.schema.json.internal;

import static com.prayer.util.Error.info;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.prayer.exception.AbstractSchemaException;
import com.prayer.exception.AbstractSystemException;
import com.prayer.exception.system.SerializationException;
import com.prayer.exception.system.TypeInitException;
import com.prayer.mod.meta.FieldModel;
import com.prayer.mod.meta.GenericSchema;
import com.prayer.mod.meta.KeyModel;
import com.prayer.mod.meta.MetaModel;
import com.prayer.schema.Ensurer;
import com.prayer.schema.Importer;
import com.prayer.schema.Serializer;
import com.prayer.schema.json.CommunionSerializer;
import com.prayer.util.JsonKit;
import com.prayer.util.StringKit;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;
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
	private transient final String filePath;
	/** **/
	@NotNull
	private transient final Ensurer ensurer;
	/** **/
	@NotNull
	private transient final Serializer serializer;
	/** **/
	private transient JsonNode rawData;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/**
	 * 
	 * @param filePath
	 */
	@PostValidateThis
	public CommunionImporter(@NotNull @NotEmpty @NotBlank final String filePath) {
		this.filePath = filePath;
		this.ensurer = new GenericEnsurer();
		this.serializer = new CommunionSerializer();
		if (null == this.filePath) {
			info(LOGGER, "[E] File path initializing met error!",
					new TypeInitException(getClass(), "Constructor: GenericImporter(String)", this.filePath));
		}
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/**
	 * 初始化文件读取的过程
	 */
	@Override
	@PreValidateThis
	public void importFile() throws AbstractSystemException {
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
	public GenericSchema transformModel() throws SerializationException {
		GenericSchema schema = null;
		if (null != this.rawData) {
			schema = new GenericSchema();
			final MetaModel meta = this.readMeta();
			schema.setMeta(meta);
			schema.setIdentifier(meta.getGlobalId());
			schema.setKeys(this.readKeys());
			schema.setFields(this.readFields());
		}
		return schema;
	}

	/**
	 * 
	 */
	@Override
	public Ensurer getEnsurer() {
		return this.ensurer;
	}

	// ~ Methods =============================================
	// ~ Private Methods =====================================

	private MetaModel readMeta() {
		MetaModel meta = null;
		try {
			meta = this.serializer.readMeta(this.rawData.path("__meta__"));
		} catch (SerializationException ex) {
			info(LOGGER, "Serialization Exception, \"__meta__\" = " + this.rawData.path("__meta__").toString(), ex);
		}
		return meta;
	}

	private ConcurrentMap<String, FieldModel> readFields() {
		final ConcurrentMap<String, FieldModel> fieldsMap = new ConcurrentHashMap<>();
		List<FieldModel> fields = null;
		try {
			fields = this.serializer.readFields(JsonKit.fromJObject(this.rawData.path("__fields__")));
			for (final FieldModel field : fields) {
				if (StringKit.isNonNil(field.getName())) {
					fieldsMap.put(field.getName(), field);
				}
			}
		} catch (SerializationException ex) {
			info(LOGGER, "Serialization Exception, \"__fields__\" = " + this.rawData.path("__fields__").toString(), ex);
		}
		return fieldsMap;
	}

	private ConcurrentMap<String, KeyModel> readKeys() {
		final ConcurrentMap<String, KeyModel> keysMap = new ConcurrentHashMap<>();
		List<KeyModel> keys = null;
		try {
			keys = this.serializer.readKeys(JsonKit.fromJObject(this.rawData.path("__keys__")));
			for (final KeyModel key : keys) {
				if (StringKit.isNonNil(key.getName())) {
					keysMap.put(key.getName(), key);
				}
			}
		} catch (SerializationException ex) {
			info(LOGGER, "Serialization Exception, \"__keys__\" = " + this.rawData.path("__keys__").toString(), ex);
		}
		return keysMap;
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
