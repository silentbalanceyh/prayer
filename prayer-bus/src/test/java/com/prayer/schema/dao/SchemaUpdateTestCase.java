package com.prayer.schema.dao;

import static com.prayer.util.reflection.Instance.singleton;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.prayer.dao.impl.schema.CommuneImporter;
import com.prayer.dao.impl.schema.SchemaDalor;
import com.prayer.facade.dao.schema.Importer;
import com.prayer.facade.dao.schema.SchemaDao;
import com.prayer.facade.schema.Schema;
import com.prayer.fantasm.exception.AbstractException;

/**
 * 
 * @author Lang
 *
 */
public class SchemaUpdateTestCase {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    private transient Importer importer;
    /** **/
    private transient SchemaDao dao;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public SchemaUpdateTestCase() {
        this.importer = singleton(CommuneImporter.class);
        this.dao = singleton(SchemaDalor.class);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 
     */
    @Test
    public void testSchemaUpdate() throws AbstractException {
        /** 1.Schema基础操作 **/
        final Schema oldSchema = this.importer.read("/schema/data/json/validation/P012meta-schema1-from.json");
        if (null != oldSchema) {
            /** 2.插入一条新的Schema **/
            final Schema inserted = this.dao.save(oldSchema);
            assertEquals(oldSchema, inserted);
            /** 3.读取一条新的Schema **/
            final Schema newSchema = this.importer.read("/schema/data/json/validation/P012meta-schema1-to.json");
            if (null != newSchema) {
                final Schema updated = this.dao.save(newSchema);
                /** 4.从系统中读取最新的Schema **/
                final Schema selected = this.dao.get(newSchema.identifier());
                assertEquals(selected, updated);
            } else {
                fail("[ERR] Importing met error.(NEW)");
            }
            /** 5.删除系统中的数据 **/
            this.dao.delete(inserted.identifier());
        } else {
            fail("[ERR] Importing met error.(OLD)");
        }
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
