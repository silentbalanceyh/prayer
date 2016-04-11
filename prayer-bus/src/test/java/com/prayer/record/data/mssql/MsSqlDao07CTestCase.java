package com.prayer.record.data.mssql;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.ConcurrentHashMap;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.database.PolicyConflictCallException;
import com.prayer.facade.model.crucial.Value;
import com.prayer.facade.model.record.Record;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.record.data.AbstractMsSqlDaoTool;
import com.prayer.util.business.RecordKit;

import net.sf.oval.exception.ConstraintsViolatedException;

/**
 * 
 * @author Lang
 *
 */
public class MsSqlDao07CTestCase extends AbstractMsSqlDaoTool {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(MsSqlDao04GTestCase.class);
    /** **/
    private static final String IDENTIFIER = "tst.mod.dao7";

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
    /** **/
    @Before
    public void setUp() {
        try {
            this.prepareSchema("MsSqlP002OpTestDAO7.json", IDENTIFIER);
        } catch (AbstractException ex) {
            failure(TST_PREP, ex.getErrorMessage());
        }
    }

    /** **/
    @After
    public void setDown() {
        try {
            this.getService().removeById(IDENTIFIER);
        } catch (AbstractException ex) {
            failure(ex.getErrorMessage());
        }
    }

    // ~ Testing Method ======================================
    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05095Minsert() throws AbstractDatabaseException {
        if (this.isValidDB()) {
            this.getDao().insert(null);
            failure(message(TST_OVAL));
        }
    }

    /** **/
    @Test
    public void testT05045Minsert() throws AbstractDatabaseException {
        this.testInsert(IDENTIFIER, Assert::assertTrue);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05098Mupdate() throws AbstractDatabaseException {
        if (this.isValidDB()) {
            this.getDao().update(null);
            failure(message(TST_OVAL));
        }
    }

    /** **/
    @Test
    public void testT05048Mupdate() throws AbstractDatabaseException {
        this.testUpdate(IDENTIFIER, Assert::assertTrue);
    }

    /**
     * 非法调用：this.getDao().selectById(before, null);
     **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05096MselectById() throws AbstractDatabaseException {
        if (this.isValidDB()) {
            this.getDao().selectById(null, V_ID);
            failure(message(TST_OVAL));
        }
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05097MselectById() throws AbstractDatabaseException {
        if (this.isValidDB()) {
            final Record before = this.createRecord(IDENTIFIER);
            this.getDao().selectById(before, new ConcurrentHashMap<>());
            failure(message(TST_OVAL));
        }
    }

    /** **/
    @Test(expected = PolicyConflictCallException.class)
    public void testT05046MselectById() throws AbstractDatabaseException {
        if (this.isValidDB()) {
            // 准备数据
            final Record before = this.createRecord(IDENTIFIER);
            final Record after = this.getDao().insert(before);
            // 调用select
            final Value<?> uniqueId = after.idKV().values().iterator().next();
            final Record selectR = this.getDao().selectById(after, uniqueId);
            // 判断最终结果
            final boolean ret = RecordKit.equal(after, selectR);
            assertTrue(message(TST_TF, Boolean.TRUE), ret);
            // 检查完毕将新插入的数据删除掉
            this.getDao().delete(selectR);
        }
    }

    /** **/
    @Test
    public void testT05047MselectById() throws AbstractDatabaseException {
        this.testSelect(IDENTIFIER, Assert::assertTrue);
    }

    /** **/
    @Test
    public void testT05049MselectById() throws AbstractDatabaseException {
        this.testInvalidSelect(IDENTIFIER, Assert::assertTrue);
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
