package com.prayer.record.initialize;

import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.instance;

import com.prayer.AbstractCommonTool;
import com.prayer.business.impl.deployment.SchemaBllor;
import com.prayer.constant.Resources;
import com.prayer.facade.business.deployment.SchemaService;
import com.prayer.facade.schema.Schema;
import com.prayer.model.business.ServiceResult;
import com.prayer.util.string.StringKit;

/**
 * Record，Record Dao部分的抽象测试用例
 * 
 * @author Lang
 *
 */
public abstract class AbstractRecordTool extends AbstractCommonTool {
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
    public AbstractRecordTool() {
        this.service = instance(SchemaBllor.class);
    }

    // ~ Abstract Methods ====================================
    /** 当前数据库类型 **/
    protected abstract String getCategory();

    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** **/
    protected boolean isValidDB() {
        return StringKit.equals(getCategory(), Resources.DB_CATEGORY);
    }

    /** **/
    protected SchemaService getService() {
        return this.service;
    }

    /** Json -> H2 -> Database **/
    protected ServiceResult<Schema> prepareSchema(final String filePath, final String identifier) {
        ServiceResult<Schema> ret = new ServiceResult<>();
        if (this.isValidDB()) {
            // Json -> H2
            ret = this.getService().importSchema(DAO_DATA_PATH + filePath);
            if (ret.success()) {
                ret = this.getService().syncMetadata(ret.getResult());
            } else {
                peError(getLogger(), ret.getServiceError());
            }
        }
        return ret;
    }
    
    
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
