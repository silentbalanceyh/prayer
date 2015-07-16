package com.prayer.bus.schema.impl;

import static com.prayer.util.Error.info;
import static com.prayer.util.Instance.singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.bus.schema.SchemaService;
import com.prayer.constant.Accessors;
import com.prayer.dao.schema.SchemaDao;
import com.prayer.dao.schema.impl.SchemaDaoImpl;
import com.prayer.exception.AbstractSchemaException;
import com.prayer.exception.AbstractSystemException;
import com.prayer.exception.system.DataLoadingException;
import com.prayer.exception.system.SchemaNotFoundException;
import com.prayer.exception.system.SerializationException;
import com.prayer.meta.Builder;
import com.prayer.mod.bus.ServiceResult;
import com.prayer.mod.meta.GenericSchema;
import com.prayer.schema.Importer;
import com.prayer.schema.json.internal.CommunionImporter;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class SchemaSevImpl implements SchemaService {
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(SchemaSevImpl.class);
	// ~ Instance Fields =====================================
	/** H2数据库的Importer **/
	private transient Importer importer = null;
	/** SQL数据库的Builder **/
	private transient Builder builder = null;
	/** 访问H2的Schema数据层接口 **/
	@NotNull
	private transient SchemaDao dao = null;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	@PostValidateThis
	public SchemaSevImpl() {
		this.dao = singleton(SchemaDaoImpl.class);
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/**
	 * 
	 */
	@Override
	public ServiceResult<GenericSchema> syncSchema(@NotNull @NotEmpty @NotBlank String filePath) {
		this.importer = this.getImporter(filePath);
		final ServiceResult<GenericSchema> result = new ServiceResult<>();
		try {
			// 1.读取filePath -> AbstractSystemException
			this.importer.readSchema();
			// 2.验证Schema文件流程
			this.importer.ensureSchema();
			// 3.转换Schema文件
			final GenericSchema schema = this.importer.transformSchema();
			// 4.因为importer中已经检查过，所以不需要再检查
			this.importer.syncSchema(schema);
			// 5.成功代码
			result.setResponse(schema, null);
		} catch (DataLoadingException ex) {
			info(LOGGER, "[I-BUS] 4.Data Loading Exception. Loading Data...", ex);
			result.setResponse(null, ex);
		} catch (SerializationException ex) {
			info(LOGGER, "[I-BUS] 3.Serialization Exception. ", ex);
			result.setResponse(null, ex);
		} catch (AbstractSystemException ex) {
			info(LOGGER, "[I-BUS] 1.Reading json schema file. file = " + filePath, ex);
			result.setResponse(null, ex);
		} catch (AbstractSchemaException ex) {
			info(LOGGER, "[I-BUS] 2.Error when verifying json schema.", ex);
			result.setResponse(null, ex);
		}
		return result;
	}

	/** **/
	@Override
	public ServiceResult<GenericSchema> syncMetadata(@NotNull GenericSchema schema) {
		this.builder = singleton(Accessors.builder(), schema);
		if (this.builder.existTable()) {
			this.builder.syncTable(schema);
		} else {
			this.builder.createTable();
		}
		return new ServiceResult<>(schema, null);
	}

	/** **/
	@Override
	public ServiceResult<GenericSchema> findSchema(String identifier) {
		final GenericSchema schema = this.dao.getById(identifier);
		final ServiceResult<GenericSchema> result = new ServiceResult<>();
		if (null == schema) {
			result.setResponse(null, new SchemaNotFoundException(getClass(), identifier));
		} else {
			result.setResponse(schema, null);
		}
		return result;
	}

	@Override
	public ServiceResult<Boolean> removeSchema(String identifier) {
		return null;
	}

	// ~ Methods =============================================
	// ~ Private Methods =====================================
	/** **/
	private Importer getImporter(final String filePath) {
		if (null == this.importer) {
			this.importer = new CommunionImporter(filePath);
			info(LOGGER, "[I] Init new importer: file = " + filePath);
		} else {
			this.importer.refreshSchema(filePath);
			info(LOGGER, "[I] Refresh schema of importer: file = " + filePath);
		}
		return this.importer;
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
