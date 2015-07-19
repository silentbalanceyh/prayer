package com.prayer.bus.schema.impl;

import static com.prayer.util.Error.info;
import static com.prayer.util.Instance.instance;
import static com.prayer.util.Instance.singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.bus.schema.SchemaService;
import com.prayer.constant.Accessors;
import com.prayer.exception.AbstractSchemaException;
import com.prayer.exception.AbstractSystemException;
import com.prayer.exception.system.DataLoadingException;
import com.prayer.exception.system.SchemaNotFoundException;
import com.prayer.exception.system.SerializationException;
import com.prayer.kernel.Builder;
import com.prayer.kernel.model.GenericSchema;
import com.prayer.model.bus.ServiceResult;
import com.prayer.schema.Importer;
import com.prayer.schema.dao.SchemaDao;
import com.prayer.schema.dao.impl.SchemaDaoImpl;
import com.prayer.schema.json.internal.CommunionImporter;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;
import net.sf.oval.guard.PreValidateThis;

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
	private transient Importer importer;
	/** 访问H2的Schema数据层接口 **/
	@NotNull
	private transient final SchemaDao dao;

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
	@PreValidateThis
	public ServiceResult<GenericSchema> syncSchema(@NotNull @NotEmpty @NotBlank final String filePath) {
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
	@PreValidateThis
	public ServiceResult<GenericSchema> syncMetadata(@NotNull final GenericSchema schema) {
		final Builder builder = instance(Accessors.builder(), schema);
		if (builder.existTable()) {
			builder.syncTable(schema);
		} else {
			builder.createTable();
		}
		// 如果有错误则getError()就不是null值则会导致Build异常
		final ServiceResult<GenericSchema> result = new ServiceResult<>();
		result.setResponse(schema, builder.getError());
		return result;
	}

	/** **/
	@Override
	@PreValidateThis
	public ServiceResult<GenericSchema> findSchema(@NotNull @NotEmpty @NotBlank final String identifier) {
		final GenericSchema schema = this.dao.getById(identifier);
		final ServiceResult<GenericSchema> result = new ServiceResult<>();
		if (null == schema) {
			result.setResponse(null, new SchemaNotFoundException(getClass(), identifier));
		} else {
			result.setResponse(schema, null);
		}
		return result;
	}

	/** **/
	@Override
	@PreValidateThis
	public ServiceResult<Boolean> removeSchema(@NotNull @NotEmpty @NotBlank final String identifier) {
		final ServiceResult<Boolean> result = new ServiceResult<>();
		try {
			final Boolean ret = this.dao.deleteById(identifier);
			result.setResponse(ret, null);
		} catch (DataLoadingException ex) {
			info(LOGGER, "[I-BUS] Removing Schame met error...", ex);
			result.setResponse(null, ex);
		}
		return result;
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
