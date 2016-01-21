package com.prayer.accessor;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.kernel.Expression;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.model.meta.vertx.PEScript;
import com.prayer.model.query.Restrictions;
import com.prayer.model.type.StringType;

/**
 * 
 * @author Lang
 *
 */
public class PEScriptAMTestCase extends AbstractAMTestCase<PEScript> {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(PEScriptAMTestCase.class);

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
        assertTrue(insert("/accessor/pescript/script.json"));
    }

    /**
     * 测试API：insert, getById, update, deleteById
     * 
     * @throws AbstractDatabaseException
     */
    @Test
    public void testUpdate() throws AbstractDatabaseException {
        assertTrue(update("/accessor/pescript/script2.json", "/accessor/pescript/script2update.json"));
    }

    /**
     * 测试API：insert(批量), count, deleteById(批量), count，purge
     * 
     * @throws AbstractDatabaseException
     */
    @Test
    public void testBatch() throws AbstractDatabaseException {
        assertTrue(batchOp("/accessor/pescript/script3.json"));
    }

    /**
     * 测试API：insert(批量), purge, getByPage, getAll
     * 
     * @throws AbstractDatabaseException
     */
    @Test
    public void testPage() throws AbstractDatabaseException {
        assertTrue(page("/accessor/pescript/script4.json", "/accessor/pescript/script4page.json"));
    }
    /**
     * 测试API：queryList,, insert, deleteById
     * @throws AbstractDatabaseException
     */
    @Test
    public void testWhere() throws AbstractDatabaseException{
        // 特殊的查询语句，Where子句
        final Expression filter = Restrictions.eq("S_NAME",new StringType("LANG-XXX"));
        assertTrue(selectWhere("/accessor/pescript/script5.json",filter));
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
