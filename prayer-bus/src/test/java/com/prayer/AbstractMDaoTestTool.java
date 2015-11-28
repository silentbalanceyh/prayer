package com.prayer;

import static com.prayer.util.Error.info;
import static com.prayer.util.Instance.instance;
import static com.prayer.util.Instance.singleton;

import org.junit.Test;
import org.slf4j.Logger;

import com.prayer.dao.impl.meta.MetaDaoImpl;
import com.prayer.exception.AbstractDatabaseException;
import com.prayer.facade.dao.RecordDao;
import com.prayer.facade.kernel.Record;
import com.prayer.kernel.model.MetaRecord;

/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractMDaoTestTool {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    private transient RecordDao dao;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public AbstractMDaoTestTool() {
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
    @Test
    public void testInsert() throws AbstractDatabaseException {
        final Record before = this.getRecord(this.identifier());
        final Record after = this.getRecordDao().insert(before);
        System.out.println(after);
    }
    // ~ Private Methods =====================================

    private RecordDao getRecordDao() {
        return this.dao;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
