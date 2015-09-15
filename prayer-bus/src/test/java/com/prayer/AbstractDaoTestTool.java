package com.prayer;

import static com.prayer.util.Instance.instance;
import static com.prayer.util.Instance.singleton;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.prayer.bus.std.SchemaService;
import com.prayer.bus.std.impl.SchemaSevImpl;
import com.prayer.constant.Resources;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.dao.record.RecordDao;
import com.prayer.dao.record.impl.RecordDaoImpl;
import com.prayer.exception.AbstractMetadataException;
import com.prayer.kernel.Record;
import com.prayer.kernel.model.GenericRecord;
import com.prayer.kernel.model.GenericSchema;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.h2.FieldModel;

import jodd.util.StringUtil;

/**
 * Dao数据层测试基类
 * 
 * @author Lang
 *
 */
public abstract class AbstractDaoTestTool extends AbstractTestTool {
	// ~ Static Fields =======================================
	/** **/
	protected static final String DAO_DATA_PATH = "/schema/data/json/dao/";
	// ~ Instance Fields =====================================
	/** Schema服务层接口 **/
	private transient final SchemaService service;
	/** Record数据访问层 **/
	private transient final RecordDao recordDao;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	public AbstractDaoTestTool() {
		super();
		this.service = instance(SchemaSevImpl.class.getName());
		this.recordDao = singleton(RecordDaoImpl.class);
	}

	// ~ Abstract Methods ====================================
	/** 获取数据库类型 **/
	protected abstract String getDbCategory();

	// ~ Override Methods ====================================
	// ~ Methods =============================================
	/** 当前访问的Database是否合法 **/
	protected boolean isValidDB() {
		return StringUtil.equals(getDbCategory(), Resources.DB_CATEGORY);
	}

	/** 获取服务层访问接口 **/
	protected SchemaService getService() {
		return this.service;
	}

	/** 从Json到H2并且读取Metadata同步到对应数据库 **/
	protected ServiceResult<GenericSchema> syncMetadata(final String filePath, final String identifier) {
		ServiceResult<GenericSchema> finalRet = new ServiceResult<>(null, null);
		if (this.isValidDB()) {
			// 基础数据
			final ServiceResult<GenericSchema> syncRet = this.getService().syncSchema(DAO_DATA_PATH + filePath);
			if (ResponseCode.SUCCESS == syncRet.getResponseCode()) {
				finalRet = this.getService().syncMetadata(syncRet.getResult());
			} else {
				info(getLogger(), syncRet.getErrorMessage());
				finalRet = syncRet;
			}
		}
		return finalRet;
	}

	/** **/
	protected Record getRecord(final String identifier) {
		final Record record = instance(GenericRecord.class.getName(), identifier);
		for (final String field : record.fields().keySet()) {
			try {
				record.set(field, Assistant.generate(record.fields().get(field), false));
			} catch (AbstractMetadataException ex) {
				info(getLogger(), ex.getErrorMessage(), ex);
			}
		}
		return record;
	}

	/** **/
	protected void updateRecord(final Record record) {
		// ID不可更新
		final List<FieldModel> pkeys = record.idschema();
		final Set<String> ids = new HashSet<>();
		for (final FieldModel pkey : pkeys) {
			ids.add(pkey.getName());
		}
		// 和添加不一样，这里面存在一个判断
		for (final String field : record.fields().keySet()) {
			try {
				if (!ids.contains(field)) {
					record.set(field, Assistant.generate(record.fields().get(field), true));
				}
			} catch (AbstractMetadataException ex) {
				info(getLogger(), ex.getErrorMessage(), ex);
			}
		}
	}

	/** **/
	protected RecordDao getRecordDao() {
		return this.recordDao;
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
