package com.prayer.record.meta.ibatis;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.Assistant;
import com.prayer.exception.database.OperationNotSupportException;
import com.prayer.facade.kernel.Value;
import com.prayer.facade.record.Record;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.model.type.StringType;
import com.prayer.record.meta.AbstractRecordDaoTool;

import io.vertx.core.json.JsonArray;
import net.sf.oval.exception.ConstraintsViolatedException;

/**
 * 
 * @author Lang
 *
 */
public class KeyDaoTestCase extends AbstractRecordDaoTool {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(KeyDaoTestCase.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    protected String identifier() {
        return "meta-key";
    }

    /** **/
    @Override
    protected Logger getLogger() {
        return LOGGER;
    }
    
    /** **/
    @Override
    protected ConcurrentMap<String, Value<?>> specValues() {
        final ConcurrentMap<String, Value<?>> data = new ConcurrentHashMap<>();
        data.put("category",new StringType(Assistant.randArray("PrimaryKey","ForeignKey","UniqueKey")));
        final JsonArray array = new JsonArray("[]");
        data.put("columns", new StringType(array.encode()));
        data.put("refMetaId", new StringType(null));
        return data;
    }
    // ~ Methods =============================================

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05095Minsert() throws AbstractDatabaseException {
        this.getDao().insert(null);
        failure(message(TST_OVAL));
    }

    /** **/
    @Test
    public void testT05045Minsert() throws AbstractDatabaseException {
        this.testInsert(Assert::assertTrue);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05098Mupdate() throws AbstractDatabaseException {
        this.getDao().update(null);
        failure(message(TST_OVAL));
    }

    /** **/
    @Test
    public void testT05048Mupdate() throws AbstractDatabaseException {
        this.testUpdate(Assert::assertTrue);
    }

    /**
     * 非法调用：this.getDao().selectById(before, null);
     **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05096MselectById() throws AbstractDatabaseException {
        this.getDao().selectById(null, V_ID);
        failure(message(TST_OVAL));

    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05097MselectById() throws AbstractDatabaseException {
        final Record before = this.createRecord(identifier());
        this.getDao().selectById(before, new ConcurrentHashMap<>());
        failure(message(TST_OVAL));

    }

    /** **/
    @Test(expected = OperationNotSupportException.class)
    public void testT05046MselectById() throws AbstractDatabaseException {
        this.testUnsupport(Assert::assertTrue);
    }

    /** **/
    @Test
    public void testT05047MselectById() throws AbstractDatabaseException {
        this.testSelect(Assert::assertTrue);
    }

    /** **/
    @Test
    public void testT05049MselectById() throws AbstractDatabaseException {
        this.testInvalidSelect(Assert::assertTrue);
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
