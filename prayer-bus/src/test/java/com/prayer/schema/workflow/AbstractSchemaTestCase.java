package com.prayer.schema.workflow;

import static com.prayer.util.Instance.singleton;
import static com.prayer.util.debug.Log.peError;

import org.slf4j.Logger;

import com.prayer.AbstractTestCase;
import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.base.exception.AbstractSystemException;
import com.prayer.base.exception.AbstractTransactionException;
import com.prayer.dao.impl.jdbc.JdbcConnImpl;
import com.prayer.dao.impl.schema.SchemaDaoImpl;
import com.prayer.exception.system.SerializationException;
import com.prayer.facade.dao.jdbc.JdbcContext;
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
    /** **/
    protected transient JdbcContext context;    // NOPMD

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public AbstractSchemaTestCase() {
        super(CommunionImporter.class.getName());
        this.serializer = new CommunionSerializer();
        this.service = singleton(SchemaDaoImpl.class);
        this.context = singleton(JdbcConnImpl.class);
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
            peError(getLogger(),ex);
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
        } catch (AbstractTransactionException ex) {
            peError(getLogger(),ex);
        } catch (SerializationException ex) {
            peError(getLogger(),ex);
        } catch (AbstractSystemException ex) {
            peError(getLogger(),ex);
        } catch (AbstractSchemaException ex) {
            peError(getLogger(),ex);
        }
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
