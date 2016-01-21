package com.prayer.builder;

import static com.prayer.util.debug.Log.debug;
import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.singleton;

import org.slf4j.Logger;

import com.prayer.base.exception.AbstractException;
import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.base.exception.AbstractSystemException;
import com.prayer.base.exception.AbstractTransactionException;
import com.prayer.builder.fun.TestMethod;
import com.prayer.constant.Resources;
import com.prayer.dao.impl.builder.MetadataBuilder;
import com.prayer.dao.impl.schema.CommuneImporter;
import com.prayer.dao.impl.schema.SchemaDaoImpl;
import com.prayer.exception.system.SerializationException;
import com.prayer.facade.dao.builder.Builder;
import com.prayer.facade.dao.schema.Importer;
import com.prayer.facade.dao.schema.SchemaDao;
import com.prayer.facade.schema.Schema;

/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractBDTestCase {
    // ~ Static Fields =======================================
    /** **/
    protected static final String SCHEMA_FLD = "/schema/data/json/database/";
    // ~ Instance Fields =====================================
    /** 访问SchemaDao用于做Schema的导入 **/
    private transient SchemaDao dao;
    /** Schema的导入器 **/
    private transient Importer importer;
    /** Builder的构造器 **/
    private transient Builder builder;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public AbstractBDTestCase() {
        this.dao = singleton(SchemaDaoImpl.class);
        this.builder = singleton(MetadataBuilder.class);
        this.importer = singleton(CommuneImporter.class);
    }

    // ~ Abstract Methods ====================================
    /** **/
    protected abstract Logger getLogger();

    /** **/
    protected abstract boolean validDB();
    
    /** **/
    protected abstract String getCategory();
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 执行测试Container
     * @param method
     * @return
     */
    protected boolean executeContainer(final TestMethod method){
        boolean ret = false;
        if(validDB()){
            try{
                method.testing();
                ret = true;
            }catch(AbstractException ex){
                peError(getLogger(),ex);
                ret = false;
            }
        }else{
            this.unMatch();
            ret = false;
        }
        return ret;
    }
    /**
     * 数据准备
     * @param file
     * @return
     */
    protected Schema prepare (final String file) {
        final String filePath = SCHEMA_FLD + file;
        Schema schema = null;
        try {
            schema = importer.read(filePath);
            schema = this.dao.save(schema);
        } catch (SerializationException ex) {
            debug(getLogger(), ex.getErrorMessage(), ex);
        } catch (AbstractTransactionException ex) {
            debug(getLogger(), ex.getErrorMessage(), ex);
        } catch (AbstractSystemException ex) {
            debug(getLogger(), ex.getErrorMessage(), ex);
        } catch (AbstractSchemaException ex) {
            debug(getLogger(), ex.getErrorMessage(), ex);
        }
        return schema;
    }
    /**
     * 数据库不匹配
     */
    protected void unMatch(){
        debug(getLogger(), "[T] Database not match ! Expected: " + getCategory() + ", Actual: " + Resources.DB_CATEGORY);
    }
    
    
    /**
     * 获取Builder
     * @return
     */
    protected Builder builder(){
        return this.builder;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
