package com.prayer.dao.record.impl;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.ConcurrentHashMap;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.AbstractRDaoTestTool;
import com.prayer.exception.AbstractDatabaseException;
import com.prayer.exception.database.PolicyConflictCallException;
import com.prayer.kernel.i.Record;
import com.prayer.kernel.i.Value;
import com.prayer.kernel.model.GenericRecord;
import com.prayer.kernel.model.GenericSchema;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.type.StringType;
import com.prayer.util.cv.SystemEnum.MetaPolicy;
import com.prayer.util.cv.SystemEnum.ResponseCode;

import jodd.util.StringUtil;
import net.sf.oval.exception.ConstraintsViolatedException;

/**
 * 
 * @author Lang
 *
 */
public class MsSqlDao05TestCase extends AbstractRDaoTestTool { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(MsSqlDao05TestCase.class);
    /** **/
    private static final String DB_CATEGORY = "MSSQL";
    /** **/
    private static final String IDENTIFIER = "tst.mod.dao5";
    /** **/
    private static final Value<?> V_ID = new StringType("ID");

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

    /** **/
    @Override
    protected Class<?> getTarget() {
        return GenericRecord.class;
    }

    /** **/
    @Override
    protected String getDbCategory() {
        return DB_CATEGORY;
    }

    // ~ Set Up Method =======================================
    /** **/
    @Before
    public void setUp() {
        final ServiceResult<GenericSchema> ret = this.syncMetadata("MsSqlP002OpTestDAO5.json", IDENTIFIER);
        if (ResponseCode.FAILURE == ret.getResponseCode()) {
            failure(TST_PREP, ret.getErrorMessage());
        }
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05095Minsert() throws AbstractDatabaseException {
        if (this.isValidDB()) {
            this.getRecordDao().insert(null);
            failure(message(TST_OVAL));
        }
    }

    /** **/
    @Test
    public void testT05045Minsert() throws AbstractDatabaseException {
        if (this.isValidDB()) {
            final Record before = this.getRecord(IDENTIFIER);
            final Record after = this.getRecordDao().insert(before);
            final boolean ret = before == after; // NOPMD
            assertTrue(message(TST_TF, Boolean.TRUE), ret);
            // 删除刚刚插入的数据
            this.getRecordDao().delete(after);
        }
    }

    /**
     * 非法调用：this.getRecordDao().selectById(before, null);
     **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05096MselectById() throws AbstractDatabaseException {
        if (this.isValidDB()) {
            this.getRecordDao().selectById(null, V_ID);
            failure(message(TST_OVAL));
        }
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05097MselectById() throws AbstractDatabaseException {
        if (this.isValidDB()) {
            final Record before = this.getRecord(IDENTIFIER);
            this.getRecordDao().selectById(before, new ConcurrentHashMap<>());
            failure(message(TST_OVAL));
        }
    }

    /** **/
    @Test(expected = PolicyConflictCallException.class)
    public void testT05046MselectById() throws AbstractDatabaseException {
        if (this.isValidDB()) {
            // 准备数据
            final Record before = this.getRecord(IDENTIFIER);
            final Record after = this.getRecordDao().insert(before);
            // 调用select
            final Record selectR = this.getRecordDao().selectById(after, after.idKV());
            // 循环内equals检查
            for (final String field : after.fields().keySet()) {
                final boolean equals = StringUtil.equals(after.get(field).literal(), selectR.get(field).literal());
                assertTrue(message(TST_TF, Boolean.TRUE), equals);
                // assertEquals(message(TST_EQUAL),after.get(field).getValue(),selectR.get(field).getValue());
            }
            // 检查完毕将新插入的数据删除掉
            this.getRecordDao().delete(selectR);
        }
    }

    /** **/
    @Test
    public void testT05047MselectById() throws AbstractDatabaseException {
        if (this.isValidDB()) {
            // 准备数据
            final Record before = this.getRecord(IDENTIFIER);
            final Record after = this.getRecordDao().insert(before);
            final MetaPolicy policy = after.policy();
            // 只有非COLLECTION才能调用其中一个方法
            if (MetaPolicy.COLLECTION != policy) {
                final Value<?> uniqueId = after.idKV().values().iterator().next();
                if (null != uniqueId) { // NOPMD
                    final Record selectR = this.getRecordDao().selectById(after, uniqueId);
                    // 循环内equals检查
                    for (final String field : after.fields().keySet()) {
                        final boolean equals = StringUtil.equals(after.get(field).literal(),
                                selectR.get(field).literal());
                        assertTrue(message(TST_TF, Boolean.TRUE), equals);
                    }
                    // 检查完毕
                    this.getRecordDao().delete(selectR);
                }
            }
        }
    }
    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05098Mupdate() throws AbstractDatabaseException {
        if (this.isValidDB()) {
            this.getRecordDao().update(null);
            failure(message(TST_OVAL));
        }
    }

    /** **/
    @Test
    public void testT05048Mupdate() throws AbstractDatabaseException {
        if (this.isValidDB()) {
            // 准备数据
            final Record before = this.getRecord(IDENTIFIER);
            final Record after = this.getRecordDao().insert(before);
            // 更新数据
            this.updateRecord(after);
            final Record updateR = this.getRecordDao().update(after);
            // 循环内equals检查
            for (final String field : after.fields().keySet()) {
                final boolean equals = StringUtil.equals(after.get(field).literal(), updateR.get(field).literal());
                assertTrue(message(TST_TF, Boolean.TRUE), equals);
                // assertEquals(message(TST_EQUAL),after.get(field).getValue(),selectR.get(field).getValue());
            }
            // 检查完毕将新插入的数据删除掉
            this.getRecordDao().delete(updateR);
        }
    }
    
    /** **/
    @Test
    public void testT05049MselectById() throws AbstractDatabaseException {
        if (this.isValidDB()) {
            // 准备数据
            final Record before = this.getRecord(IDENTIFIER);
            final Record selectR = this.getRecordDao().selectById(before, V_ID);
            assertNull(message(TST_NULL), selectR);
        }
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
