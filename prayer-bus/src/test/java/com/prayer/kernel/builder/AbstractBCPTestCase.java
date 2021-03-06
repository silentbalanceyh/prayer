package com.prayer.kernel.builder;    // NOPMD

import static com.prayer.util.Instance.instance;
import static com.prayer.util.Instance.reservoir;
import static com.prayer.util.Instance.singleton;
import static com.prayer.util.debug.Log.info;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;

import com.prayer.AbstractTestCase;
import com.prayer.Assistant;
import com.prayer.base.exception.AbstractDatabaseException;
import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.base.exception.AbstractSystemException;
import com.prayer.base.exception.AbstractTransactionException;
import com.prayer.constant.Accessors;
import com.prayer.constant.Resources;
import com.prayer.dao.impl.jdbc.JdbcConnImpl;
import com.prayer.dao.impl.schema.SchemaDaoImpl;
import com.prayer.dao.impl.std.record.RecordDaoImpl;
import com.prayer.exception.system.SerializationException;
import com.prayer.facade.dao.Builder;
import com.prayer.facade.dao.RecordDao;
import com.prayer.facade.dao.jdbc.JdbcContext;
import com.prayer.facade.dao.schema.SchemaDao;
import com.prayer.facade.kernel.Record;
import com.prayer.facade.schema.DataValidator;
import com.prayer.facade.schema.Importer;
import com.prayer.model.kernel.GenericRecord;
import com.prayer.model.kernel.GenericSchema;
import com.prayer.schema.json.CommunionImporter;

import jodd.util.StringUtil;

/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractBCPTestCase extends AbstractTestCase { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    protected static final String BUILDER_FILE = "/schema/data/json/database/";
    /** **/
    private static final ConcurrentMap<String, Importer> I_POOLS = new ConcurrentHashMap<>();
    // ~ Instance Fields =====================================
    /** **/
    private transient final SchemaDao dao;
    /** **/
    private transient final JdbcContext context;
    /** **/
    private transient RecordDao recordDao;
    /** **/
    protected transient Builder builder;
    /** **/
    private transient final DataValidator verifier;
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     */
    public AbstractBCPTestCase() {
        super();
        this.dao = singleton(SchemaDaoImpl.class);
        this.context = instance(JdbcConnImpl.class.getName());
        this.verifier = singleton(Accessors.validator());
    }

    // ~ Abstract Methods ====================================
    /** **/
    protected abstract Logger getLogger();

    /** **/
    protected abstract String getDbCategory();

    /** **/
    protected abstract Class<?> getBuilder();

    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** **/
    protected SchemaDao getService() {
        return this.dao;
    }

    /** **/
    protected RecordDao getRecordDao() {
        return this.recordDao;
    }

    /** **/
    protected JdbcContext getContext() {
        return this.context;
    }
    /** **/
    protected DataValidator getVerifier(){
        return this.verifier;
    }

    /** **/
    protected boolean isValidDB() {
        return StringUtil.equals(getDbCategory(), Resources.DB_CATEGORY);
    }

    /** **/
    protected void beforeExecute(final String inputFile, final String globalId) {
        if (this.isValidDB()) {
            final GenericSchema schema = this.prepData(inputFile, globalId);
            if (null == schema) {
                this.executeNotMatch();
            } else {
                this.builder = instance(getBuilder().getName(), schema);
            }
        }
    }

    /** **/
    protected void afterExecute() {
        if (this.isValidDB() && null != this.builder) {
            if (!this.builder.existTable()) {
                this.builder.createTable();
            }
        } else {
            this.executeNotMatch();
        }
    }

    /** Push Data via Insert **/
    protected void pushData(final String identifier) throws AbstractDatabaseException {
        if (this.isValidDB()) {
            final Record before = this.getRecord(identifier);
            info(getLogger(), "[T] Before Insert : " + before);
            final Record after = this.getRecordDao().insert(before);
            info(getLogger(), "[T] After Insert : " + after);
        } else {
            this.executeNotMatch();
        }
    }

    /** **/
    protected boolean createTable() {
        boolean ret = false;
        if (this.isValidDB() && null != this.builder) {
            boolean exist = this.builder.existTable();
            if (exist) {
                this.builder.purgeTable();
            }
            exist = this.builder.existTable();
            if (!exist) {
                ret = this.builder.createTable();
            }
        } else {
            this.executeNotMatch();
            ret = true;
        }
        return ret;
    }

    /** **/
    protected boolean purgeTable() {
        boolean ret = false;
        if (this.isValidDB() && null != this.builder) {
            boolean exist = this.builder.existTable();
            if (!exist) {
                this.builder.createTable();
            }
            exist = this.builder.existTable();
            if (exist) {
                ret = this.builder.purgeTable();
            }
        } else {
            this.executeNotMatch();
            ret = true;
        }
        return ret;
    }

    /** **/
    protected Record getRecord(final String identifier) {
        this.recordDao = singleton(RecordDaoImpl.class);
        final Record record = instance(GenericRecord.class.getName(), identifier);
        for(final String field: record.fields().keySet()){
            try {
                record.set(field, Assistant.generate(record.fields().get(field),false));
            } catch (AbstractDatabaseException ex) {
                info(getLogger(), ex.getErrorMessage(), ex);
            }
        }
        return record;
    }
    // ~ Private Methods =====================================

    /** **/
    private GenericSchema prepData(final String inputFile, final String globalId) {
        // this.importer = new CommunionImporter(BUILDER_FILE + inputFile);
        final Importer importer = this.getImporter(BUILDER_FILE + inputFile);
        GenericSchema schema = null;
        try {
            importer.readSchema();
            importer.ensureSchema();
            schema = importer.transformSchema();
            if(schema.getMeta().getGlobalId().equals(globalId)){
                importer.syncSchema(schema);
            }
        } catch (SerializationException ex) {
            info(getLogger(), ex.getErrorMessage(), ex);
        } catch (AbstractTransactionException ex) {
            info(getLogger(), ex.getErrorMessage(), ex);
        } catch (AbstractSystemException ex) {
            info(getLogger(), ex.getErrorMessage(), ex);
        } catch (AbstractSchemaException ex) {
            info(getLogger(), ex.getErrorMessage(), ex);
        }
        return schema;
    }
    
    /**
     * 注意Importer本身的刷新流程，根据文件路径刷新了filePath
     **/
    private Importer getImporter(final String filePath) {
        Importer importer = I_POOLS.get(filePath);
        if (null == importer) {
            importer = reservoir(I_POOLS, filePath, CommunionImporter.class, filePath);
            info(getLogger(), "[IP] Init new importer: file = " + filePath);
        } else {
            importer.refreshSchema(filePath);
            info(getLogger(), "[IP] Refresh schema of importer: file = " + filePath);
        }
        return importer;//instance(CommunionImporter.class.getName(), filePath);
    }

    private void executeNotMatch() {
        info(getLogger(), "[T] Database not match ! Expected: " + getDbCategory() + ", Actual: " + Resources.DB_CATEGORY
                + " Or Builder is Null: " + (this.builder == null));
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
