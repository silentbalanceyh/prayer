package com.prayer.schema.workflow;

import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.singleton;

import org.slf4j.Logger;

import com.prayer.AbstractTestCase;
import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.base.exception.AbstractSystemException;
import com.prayer.base.exception.AbstractTransactionException;
import com.prayer.exception.system.SerializationException;
import com.prayer.facade.dao.metadata.SchemaDao;
import com.prayer.facade.pool.JdbcConnection;
import com.prayer.facade.schema.OldImporter;
import com.prayer.facade.schema.Serializer;
import com.prayer.model.kernel.GenericSchema;
import com.prayer.pool.impl.jdbc.RecordConnImpl;
import com.prayer.schema.old.CommunionSerializer;
import com.prayer.schema.old.json.CommunionImporter;

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
    protected transient OldImporter oldImporter;
    /** **/
    protected transient Serializer serializer;
    /** **/
    protected transient SchemaDao service; // NOPMD
    /** **/
    protected transient JdbcConnection context; // NOPMD

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public AbstractSchemaTestCase() {
        super(CommunionImporter.class.getName());
        this.serializer = new CommunionSerializer();
        // this.service = singleton(SchemaDaoImpl.class);
        this.context = singleton(RecordConnImpl.class);
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
        oldImporter = new CommunionImporter(SCHEMA_ROOT + inputFile);
        try {
            oldImporter.readSchema();
            oldImporter.ensureSchema();
        } catch (AbstractSystemException ex) {
            peError(getLogger(), ex);
        }
        failure("[T-ERROR] " + errMsg);
    }

    /** **/
    protected void executeSync(final String identifier) {
        try {
            // 如果存在旧的先删除
            GenericSchema dbSchema = this.service.getById(identifier);
            if (null != dbSchema) {
                this.service.deleteById(dbSchema.getIdentifier());
            }
            // 1.读取Schema信息
            oldImporter.readSchema();
            // 2.验证Schema文件
            oldImporter.ensureSchema();
            // 3.转换Schema
            final GenericSchema schema = this.oldImporter.transformSchema();
            // 4.同步数据
            dbSchema = this.service.getById(identifier);
            if (null == dbSchema) {
                this.oldImporter.syncSchema(schema);
            }
        } catch (AbstractTransactionException ex) {
            peError(getLogger(), ex);
        } catch (SerializationException ex) {
            peError(getLogger(), ex);
        } catch (AbstractSystemException ex) {
            peError(getLogger(), ex);
        } catch (AbstractSchemaException ex) {
            peError(getLogger(), ex);
        }
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
