package com.prayer.accessor;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.model.crucial.Expression;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.model.meta.database.PEMeta;
import com.prayer.model.query.Restrictions;
import com.prayer.model.type.StringType;

/**
 * 
 * @author Lang
 *
 */
public class PEMetaAMTestCase extends AbstractAccessorTool<PEMeta> {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(PEMetaAMTestCase.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    // ~ Methods =============================================
    /**
     * 测试API：insert, getById, deleteById
     **/
    @Test
    public void testInsert() throws AbstractDatabaseException {
        assertTrue(insert("/accessor/pemeta/meta.json"));
    }

    /**
     * 测试API：insert, getById, update, deleteById
     * 
     * @throws AbstractDatabaseException
     */
    @Test
    public void testUpdate() throws AbstractDatabaseException {
        assertTrue(update("/accessor/pemeta/meta2.json", "/accessor/pemeta/meta2update.json"));
    }

    /**
     * 测试API：insert(批量), count, deleteById(批量), count，purge
     * 
     * @throws AbstractDatabaseException
     */
    @Test
    public void testBatch() throws AbstractDatabaseException {
        assertTrue(batchOp("/accessor/pemeta/meta3.json"));
    }

    /**
     * 测试API：insert(批量), purge, getByPage, getAll
     * 
     * @throws AbstractDatabaseException
     */
    @Test
    public void testPage() throws AbstractDatabaseException {
        assertTrue(page("/accessor/pemeta/meta4.json", "/accessor/pemeta/meta4page.json"));
    }

    /**
     * 测试API：queryList,, insert, deleteById
     * 
     * @throws AbstractDatabaseException
     */
    @Test
    public void testWhere() throws AbstractDatabaseException {
        // 特殊的查询语句，Where子句
        final Expression filter = Restrictions.eq("S_NAME", new StringType("User"));
        assertTrue(selectWhere("/accessor/pemeta/meta5.json", filter));
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
