package com.prayer;

import static com.prayer.util.Instance.instance;

import com.prayer.bus.schema.SchemaService;
import com.prayer.bus.schema.impl.SchemaSevImpl;
import com.prayer.constant.Resources;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.kernel.model.GenericSchema;
import com.prayer.model.bus.ServiceResult;

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

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	public AbstractDaoTestTool() {
		super();
		this.service = instance(SchemaSevImpl.class.getName());
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
			final ServiceResult<GenericSchema> checkRet = this.getService().findSchema(identifier);
			if (ResponseCode.SUCCESS == checkRet.getResponseCode() && null != checkRet.getResult()) {
				finalRet = checkRet;
			} else {
				// 基础数据
				final ServiceResult<GenericSchema> syncRet = this.getService().syncSchema(DAO_DATA_PATH + filePath);
				if (ResponseCode.SUCCESS == syncRet.getResponseCode()) {
					finalRet = this.getService().syncMetadata(syncRet.getResult());
				} else {
					info(getLogger(), syncRet.getErrorMessage());
					finalRet = syncRet;
				}
			}
		}
		return finalRet;
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
