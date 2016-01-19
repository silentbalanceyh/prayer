package com.prayer.schema.dao;

import static com.prayer.util.reflection.Instance.singleton;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.prayer.base.exception.AbstractException;
import com.prayer.dao.impl.schema.CommuneImporter;
import com.prayer.dao.impl.schema.SchemaDaoImpl;
import com.prayer.facade.dao.schema.Importer;
import com.prayer.facade.dao.schema.SchemaDao;
import com.prayer.facade.schema.Schema;

/**
 * 
 * @author Lang
 *
 */
public class SchemaDaoTestCase {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    private transient Importer importer;
    /** **/
    private transient SchemaDao dao;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    public SchemaDaoTestCase() {
        this.importer = singleton(CommuneImporter.class);
        this.dao = singleton(SchemaDaoImpl.class);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 创建Schema的方法
     */
    @Test
    public void testSchemaSave() throws AbstractException {
        /** 1.Schema基础操作 **/
        final Schema schema = this.importer.readFrom("/schema/system/data/json/role.json");
        if (null != schema) {
            /** 2.插入新的Schema **/
            final Schema inserted = this.dao.save(schema);
            assertEquals(schema, inserted);
            /** 3.删除插入的Schema **/
            this.dao.delete(inserted.identifier());
        }
    }

    /**
     * 读取Schema的方法
     * 
     * @throws AbstractException
     */
    @Test
    public void testSchemaGet() throws AbstractException {
        /** 1.Schema基础操作 **/
        final Schema schema = this.importer.readFrom("/schema/system/data/json/role.json");
        if (null != schema) {
            /** 2.从系统中读取Schema **/
            final Schema inserted = this.dao.save(schema);
            final Schema selected = this.dao.get(schema.identifier());
            assertEquals(inserted, selected);
            /** 3.删除插入的Schema **/
            this.dao.delete(inserted.identifier());
        }
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
