package com.prayer.record.initialize;

import static com.prayer.util.reflection.Instance.instance;

import com.prayer.business.impl.schema.SchemaSevImpl;
import com.prayer.facade.business.schema.SchemaService;
import com.prayer.facade.schema.Schema;
import com.prayer.model.business.ServiceResult;

/**
 * Record，Record Dao部分的抽象测试用例
 * 
 * @author Lang
 *
 */
public abstract class AbstractRecordTool {
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
    public AbstractRecordTool(){
        this.service = instance(SchemaSevImpl.class);
    }
    // ~ Abstract Methods ====================================
    /** 判断DB是否合法 **/
    public abstract boolean isValidDB();
    
    public abstract String getDbCategory();
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** **/
    protected SchemaService getService(){
        return this.service;
    }
    /** Json -> H2 -> Database **/
    protected ServiceResult<Schema> prepareSchema(final String filePath, final String identifier){
        ServiceResult<Schema> ret = new ServiceResult<>();
        
        return ret;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
