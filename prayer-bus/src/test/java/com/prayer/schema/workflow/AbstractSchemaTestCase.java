package com.prayer.schema.workflow;

import static com.prayer.util.Error.info;
import static com.prayer.util.Instance.singleton;

import org.slf4j.Logger;

import com.prayer.AbstractTestCase;
import com.prayer.dao.impl.schema.SchemaDaoImpl;
import com.prayer.exception.AbstractSchemaException;
import com.prayer.exception.AbstractSystemException;
import com.prayer.exception.AbstractTransactionException;
import com.prayer.exception.system.SerializationException;
import com.prayer.facade.dao.schema.SchemaDao;
import com.prayer.facade.schema.Importer;
import com.prayer.facade.schema.Serializer;
import com.prayer.model.kernel.GenericSchema;
import com.prayer.schema.CommunionSerializer;
import com.prayer.schema.json.CommunionImporter;

/**
 * 
 * @author Lang
 * @see
 */
public abstract class AbstractSchemaTestCase extends AbstractTestCase {
    // ~ Static Fields =======================================
    /** **/
    protected static final String SCHEMA_ROOT = "/schema/data/json/validation/";
    /** **/
    protected static final String M_IMPORT_FILE = "importFile()";
    // ~ Instance Fields =====================================
    /** **/
    protected transient Importer importer;
    /** **/
    protected transient Serializer serializer;
    /** **/
    protected transient SchemaDao service;    // NOPMD

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public AbstractSchemaTestCase() {
        super(CommunionImporter.class.getName());
        this.serializer = new CommunionSerializer();
        this.service = singleton(SchemaDaoImpl.class);
    }

    // ~ Abstract Methods ====================================
    /** **/
    public abstract Logger getLogger();

    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 
     * @param inputFile
     * @param errMsg
     */
    protected void testImport(final String inputFile, final String errMsg) throws AbstractSchemaException {
        setMethod(M_IMPORT_FILE);
        importer = new CommunionImporter(SCHEMA_ROOT + inputFile);
        try {
            importer.readSchema();
            importer.ensureSchema();
        } catch (AbstractSystemException ex) {
            info(getLogger(),errMsg + " Error : " + ex.getErrorMessage());
        }
        failure("[T-ERROR] " + errMsg);
    }

    /** **/
    protected void executeSync(final String identifier) {
        try {
            // 如果存在旧的先删除
            GenericSchema dbSchema = this.service.getById(identifier);
            if(null != dbSchema){
                this.service.deleteById(dbSchema.getIdentifier());
            }
            // 1.读取Schema信息
            importer.readSchema();
            // 2.验证Schema文件
            importer.ensureSchema();
            // 3.转换Schema
            final GenericSchema schema = this.importer.transformSchema();
            // 4.同步数据
            dbSchema = this.service.getById(identifier);
            if(null == dbSchema){
                this.importer.syncSchema(schema);
            }
            info(getLogger(),"[T] =======================> Prepare Data Finished! ");
        } catch (AbstractTransactionException ex) {
            info(getLogger(), "4.Data Loading Exception. Loading Data... Error : " + ex.getErrorMessage());
        } catch (SerializationException ex) {
            info(getLogger(), "3.Serialization Exception. Error : " + ex.getErrorMessage());
        } catch (AbstractSystemException ex) {
            info(getLogger(), "1.Reading json schema file. Error : " + ex.getErrorMessage());
        } catch (AbstractSchemaException ex) {
            info(getLogger(), "2.Error when verifying json schema. Error : " + ex.getErrorMessage());
        }
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}