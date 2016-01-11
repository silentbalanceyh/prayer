package com.prayer.accessor;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.exception.AbstractDatabaseException;
import com.prayer.facade.kernel.Expression;
import com.prayer.model.query.Restrictions;
import com.prayer.model.type.StringType;
import com.prayer.model.vertx.PERoute;

/**
 * 
 * @author Lang
 *
 */
public class PERouteAMTestCase extends AbstractAMTestCase<PERoute> {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(PERouteAMTestCase.class);

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
        assertTrue(insert("/accessor/peroute/route.json"));
    }

    /**
     * 测试API：insert, getById, update, deleteById
     * 
     * @throws AbstractDatabaseException
     */
    @Test
    public void testUpdate() throws AbstractDatabaseException {
        assertTrue(update("/accessor/peroute/route2.json", "/accessor/peroute/route2update.json"));
    }

    /**
     * 测试API：insert(批量), count, deleteById(批量), count，purge
     * 
     * @throws AbstractDatabaseException
     */
    @Test
    public void testBatch() throws AbstractDatabaseException {
        assertTrue(batchOp("/accessor/peroute/route3.json"));
    }

    /**
     * 测试API：insert(批量), purge, getByPage, getAll
     * 
     * @throws AbstractDatabaseException
     */
    @Test
    public void testPage() throws AbstractDatabaseException {
        assertTrue(page("/accessor/peroute/route4.json", "/accessor/peroute/route4page.json"));
    }

    /**
     * 测试API：queryList,, insert, deleteById
     * 
     * @throws AbstractDatabaseException
     */
    @Test
    public void testWhere() throws AbstractDatabaseException {
        // 特殊的查询语句，Where子句
        final Expression filter = Restrictions.eq("S_PARENT", new StringType("/api"));
        final Expression filter1 = Restrictions.eq("S_PATH", new StringType("/sec/current"));
        final Expression finalFilter = Restrictions.and(filter, filter1);
        assertTrue(selectWhere("/accessor/peroute/route5.json", finalFilter));
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
