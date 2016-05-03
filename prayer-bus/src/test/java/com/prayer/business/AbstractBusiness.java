package com.prayer.business;

import static com.prayer.util.debug.Log.jvmError;
import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.reservoir;
import static com.prayer.util.reflection.Instance.singleton;

import org.slf4j.Logger;

import com.prayer.business.schema.SchemaBllor;
import com.prayer.facade.business.instantor.schema.SchemaInstantor;
import com.prayer.facade.database.dao.schema.DataValidator;
import com.prayer.facade.schema.Schema;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.resource.Injections;
import com.prayer.resource.Resources;
import com.prayer.util.io.IOKit;

import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractBusiness {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    /** 获取日志器 **/
    public abstract Logger getLogger();

    /** 获取数据根目录 **/
    public abstract String getFolder();

    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * Direct：Instantor - Bllor
     * 
     * @return
     */
    protected SchemaInstantor getSchemaItor() {
        return singleton(SchemaBllor.class);
    }
    
    // ~ Data Validator ======================================
    /**
     * 子类可用，获取DataValidator引用
     * 
     * @return
     */
    protected DataValidator validator() {
        return reservoir(Resources.Data.CATEGORY, Injections.Data.VALIDATOR);
    }

    // ~ Path Preparing ======================================
    /**
     * 
     * @param file
     * @return
     */
    protected JsonObject read(final String file) {
        final String content = IOKit.getContent(path(file));
        JsonObject data = null;
        try {
            data = new JsonObject(content);
        } catch (DecodeException ex) {
            jvmError(getLogger(), ex);
            data = new JsonObject();
        }
        return data;
    }
    /**
     * 
     * @param file
     * @return
     */
    protected String path(final String file){
        return getFolder() + file;
    }
    // ~ Data Preparing ======================================
    /**
     * 
     * @param identifier
     * @return
     */
    protected boolean purgeData(final String identifier) {
        boolean prepared = false;
        try {
            final SchemaInstantor instantor = this.getSchemaItor();
            /** 1.删除信息 **/
            prepared = instantor.removeById(identifier);
        } catch (AbstractException ex) {
            peError(getLogger(), ex);
            prepared = false;
        }
        return prepared;
    }

    /**
     * 
     * @param file
     */
    protected boolean prepareData(final String file) {
        boolean prepared = false;
        try {
            final SchemaInstantor instantor = this.getSchemaItor();
            /** 1.Schema准备 **/
            Schema schema = instantor.importSchema(path(file));
            if (null == schema) {
                prepared = false;
            } else {
                /** 2.导入到数据库 **/
                schema = instantor.syncMetadata(schema);
                if (null == schema) {
                    prepared = false;
                } else {
                    prepared = true;
                }
            }
        } catch (AbstractException ex) {
            peError(getLogger(), ex);
            prepared = false;
        }
        return prepared;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
