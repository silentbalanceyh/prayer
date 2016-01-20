package com.prayer.kernel.builder;

import static com.prayer.util.reflection.Instance.singleton;

import org.slf4j.Logger;

import com.prayer.AbstractTestCase;
import com.prayer.bus.impl.schema.SchemaSevImpl;
import com.prayer.constant.Resources;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.facade.bus.schema.SchemaService;
import com.prayer.facade.schema.Schema;
import com.prayer.model.bus.ServiceResult;

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
    protected ServiceResult<Schema> testUpdating(final String fromPath, final String toPath,
            final String errMsg) {
        ServiceResult<Schema> finalRet = new ServiceResult<>();
        if (this.isValidDB()) {
            // From：基础数据
            ServiceResult<Schema> syncRet = this.getService().syncSchema(BUILDER_FILE + fromPath);
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
