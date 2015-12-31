package com.prayer;

import static com.prayer.util.Instance.instance;
import static com.prayer.util.Instance.singleton;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.junit.Test;
import org.slf4j.Logger;

import com.prayer.base.exception.AbstractDatabaseException;
import com.prayer.constant.Constants;
import com.prayer.dao.impl.std.meta.MetaDaoImpl;
import com.prayer.exception.database.OperationNotSupportException;
import com.prayer.facade.dao.RecordDao;
import com.prayer.facade.kernel.Record;
import com.prayer.facade.kernel.Value;
import com.prayer.model.h2.schema.FieldModel;
import com.prayer.model.kernel.MetaRecord;
import com.prayer.model.type.StringType;
import com.prayer.util.bus.RecordKit;

import net.sf.oval.exception.ConstraintsViolatedException;

/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractMDaoTestTool extends AbstractTestTool {
    // ~ Static Fields =======================================
    /** **/
    private static final Value<?> V_ID = new StringType("ID");
    // ~ Instance Fields =====================================
    /** **/
    private final transient RecordDao dao;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public AbstractMDaoTestTool() {
        super();
        this.dao = singleton(MetaDaoImpl.class);
    }

    // ~ Abstract Methods ====================================

    /** **/
    protected abstract Logger getLogger();

    /** **/
    protected abstract String identifier();

    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** **/
    protected void updateRecord(final Record record) {
        // ID不可更新
        final List<FieldModel> pkeys = record.idschema();
        final Set<String> ids = new HashSet<>();
        for (final FieldModel pkey : pkeys) {
            ids.add(pkey.getName());
        }
        // 和添加不一样，这里面存在一个判断
        for (final String field : record.fields().keySet()) {
            try {
                if (!ids.contains(field)) {
                    record.set(field, Assistant.generate(record.fields().get(field), true));
                }
            } catch (AbstractDatabaseException ex) {
                info(getLogger(), ex.getErrorMessage(), ex);
            }
        }
    }
    /** **/
    protected Record getRecord(final String identifier) {
        final Record record = instance(MetaRecord.class.getName(), identifier);
        for (final String field : record.fields().keySet()) {
            try {
                record.set(field, Assistant.generate(record.fields().get(field), false));
            } catch (AbstractDatabaseException ex) {
                info(getLogger(), ex.getErrorMessage(), ex);
            }
        }
        return record;
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testEInsert001() throws AbstractDatabaseException {
        this.getRecordDao().insert(null);
        failure(message(TST_OVAL));
    }

    /** **/
    @Test
    public void testTInsert002() throws AbstractDatabaseException {
        final Record before = this.getRecord(this.identifier());
        final Record after = this.getRecordDao().insert(before);
        final boolean ret = RecordKit.equal(before, after);
        assertTrue(message(TST_TF, Boolean.TRUE), ret);
        // 删除刚刚传入的数据
        this.getRecordDao().delete(after);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testESelectById001() throws AbstractDatabaseException {
        this.getRecordDao().selectById(null, V_ID);
        failure(message(TST_OVAL));
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testESelectById002() throws AbstractDatabaseException {
        final Record before = this.getRecord(this.identifier());
        this.getRecordDao().selectById(before, new ConcurrentHashMap<>());
        failure(message(TST_OVAL));
    }

    /** **/
    @Test(expected = OperationNotSupportException.class)
    public void testESelectById003() throws AbstractDatabaseException {
        final Record before = this.getRecord(this.identifier());
        final ConcurrentMap<String, Value<?>> ids = new ConcurrentHashMap<>();
        ids.put("uniqueId", V_ID);
        this.getRecordDao().selectById(before, ids);
        failure(message(TST_OVAL));
    }

    /** **/
    @Test
    public void testTSelectById004() throws AbstractDatabaseException {
        // 准备数据
        final Record before = this.getRecord(this.identifier());
        final Record after = this.getRecordDao().insert(before);
        // 调用select
        final String uniqueId = after.idschema().get(Constants.ZERO).getName();
        final Record selectR = this.getRecordDao().selectById(after, after.get(uniqueId));
        // 判断equals
        final boolean ret = RecordKit.equal(after, selectR);
        assertTrue(message(TST_TF, Boolean.TRUE), ret);
        // 检查完过后将新插入的数据删除
        this.getRecordDao().delete(after);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testEUpdate001() throws AbstractDatabaseException {
        this.getRecordDao().update(null);
        failure(message(TST_OVAL));
    }
    /** **/
    @Test
    public void testTUpdate002() throws AbstractDatabaseException{
        // 准备数据
        final Record before = this.getRecord(this.identifier());
        final Record after = this.getRecordDao().insert(before);
        // 更新数据
        this.updateRecord(after);
        final Record updateR = this.getRecordDao().update(after);
        // 循环内equals检查
        final boolean ret = RecordKit.equal(after, updateR);
        assertTrue(message(TST_TF, Boolean.TRUE), ret);
        // 检查完毕将新插入的数据删除掉
        this.getRecordDao().delete(updateR);
    }
    // ~ Private Methods =====================================

    private RecordDao getRecordDao() {
        return this.dao;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
