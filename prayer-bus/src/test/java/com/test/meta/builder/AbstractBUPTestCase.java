package com.test.meta.builder;

import static com.prayer.util.Instance.singleton;

import org.slf4j.Logger;

import com.prayer.bus.SchemaService;
import com.prayer.bus.impl.SchemaSevImpl;
import com.prayer.constant.Resources;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.kernel.model.GenericSchema;
import com.prayer.model.bus.ServiceResult;
import com.test.AbstractTestCase;

import jodd.util.StringUtil;

/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractBUPTestCase extends AbstractTestCase { // NOPMD
	// ~ Static Fields =======================================
	/** **/
	protected static final String BUILDER_FILE = "/schema/data/json/database/";
	// ~ Instance Fields =====================================
	/** **/
	private transient final SchemaService service;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	public AbstractBUPTestCase() {
		super();
		this.service = singleton(SchemaSevImpl.class.getName());
	}

	// ~ Abstract Methods ====================================
	/** **/
	protected abstract Logger getLogger();

	/** **/
	protected abstract String getDbCategory();

	// ~ Override Methods ====================================
	// ~ Methods =============================================
	/** **/
	protected SchemaService getService() {
		return this.service;
	}

	/** **/
	protected boolean isValidDB() {
		return StringUtil.equals(getDbCategory(), Resources.DB_CATEGORY);
	}

	/** **/
	protected ServiceResult<GenericSchema> testUpdating(final String fromPath, final String toPath,
			final String errMsg) {
		ServiceResult<GenericSchema> finalRet = new ServiceResult<>(null, null);
		if (this.isValidDB()) {
			// From：基础数据
			ServiceResult<GenericSchema> syncRet = this.getService().syncSchema(BUILDER_FILE + fromPath);
			syncRet = this.getService().syncMetadata(syncRet.getResult());
			// To：第二次的数据，更新过后的数据
			if (ResponseCode.SUCCESS == syncRet.getResponseCode()) {
				syncRet = this.getService().syncSchema(BUILDER_FILE + toPath);
				finalRet = this.getService().syncMetadata(syncRet.getResult());
			} else {
				failure(errMsg);
			}
		}
		return finalRet;
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
