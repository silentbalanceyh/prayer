package com.prayer.builder;

import static com.prayer.util.debug.Log.debug;
import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.singleton;

import org.slf4j.Logger;

import com.prayer.builder.impl.MetadataBuilder;
import com.prayer.constant.Resources;
import com.prayer.dao.impl.schema.CommuneImporter;
import com.prayer.dao.impl.schema.SchemaDaoImpl;
import com.prayer.exception.system.SerializationException;
import com.prayer.facade.builder.Builder;
import com.prayer.facade.dao.schema.Importer;
import com.prayer.facade.dao.schema.SchemaDao;
import com.prayer.facade.schema.Schema;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.fantasm.exception.AbstractSchemaException;
import com.prayer.fantasm.exception.AbstractSystemException;
import com.prayer.fantasm.exception.AbstractTransactionException;

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
     * 执行Container
     * 
     * @param method
     * @return
     */
    protected boolean executeSyncContainer(final String file) {
        boolean ret = true;
        if (validDB()) {
            try {
                final Schema schema = this.prepare(file);
                this.builder().synchronize(schema);
                ret = true;
            } catch (AbstractException ex) {
                peError(getLogger(), ex);
                ret = false;
            }
        } else {
            this.unMatch();
        }
        return ret;
    }

    /**
     * 执行Container
     * 
     * @param file
     * @return
     */
    protected boolean executePurgeContainer(final String file) {
        boolean ret = true;
        if (validDB()) {
            try {
                final Schema schema = this.prepare(file);
                this.builder().purge(schema);
                ret = true;
            } catch (AbstractException ex) {
                peError(getLogger(), ex);
                ret = false;
            }
        } else {
            this.unMatch();
        }
        return ret;
    }

    /**
     * 执行Container
     * 
     * @param fromFile
     * @param toFile
     * @return
     */
    protected boolean executeUpdateContainer(final String fromFile, final String toFile) {
        boolean ret = true;
        if (validDB()) {
            try {
                // 1.先执行创建
                final Schema schema = this.prepare(fromFile);
                this.builder().synchronize(schema);
                // 2.再执行更新
                final Schema updated = this.prepare(toFile);
                this.builder().synchronize(updated);
                // 3.最后Purge
                this.builder().purge(updated);
                ret = true;
            } catch (AbstractException ex) {
                peError(getLogger(), ex);
                ret = false;
            }
        } else {
            this.unMatch();
        }
        return ret;
    }

    // ~ Component ===========================================
    /**
     * 获取Builder
     * 
     * @return
     */
    protected Builder builder() {
        return this.builder;
    }
    // ~ Private Methods =====================================

    /**
     * 数据准备
     * 
     * @param file
     * @return
     */
    private Schema prepare(final String file) {
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
    private void unMatch() {
        debug(getLogger(),
                "[T] Database not match ! Expected: " + getCategory() + ", Actual: " + Resources.DB_CATEGORY);
    }

    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
