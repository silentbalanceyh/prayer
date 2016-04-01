package com.prayer.record.initialize;

import static com.prayer.util.reflection.Instance.instance;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.database.ColumnInvalidException;
import com.prayer.exception.database.FieldInvalidException;
import com.prayer.facade.kernel.Value;
import com.prayer.facade.record.Record;
import com.prayer.facade.schema.Schema;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.model.business.ServiceResult;
import com.prayer.model.crucial.DataRecord;
import com.prayer.model.type.StringType;

import net.sf.oval.exception.ConstraintsViolatedException;

/**
 * 
 * @author Lang
 *
 */
public class DataRecord01TestCase extends AbstractMsSqlRecordTool {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(DataRecord01TestCase.class);
    /** **/
    private static final String IDENTIFIER = "tst.mod.dao3";
    /** **/
    private static final Value<?> STR_VAL = new StringType("");

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
        return DataRecord.class;
    }

    // ~ Methods =============================================
    /** **/
    @Before
    public void setUp() {
        final ServiceResult<Schema> ret = this.prepareSchema("MsSqlP001TestDAO3.json", IDENTIFIER);
        if (!ret.success()) {
            failure(TST_PREP, ret.getErrorMessage());
        }
    }
    /** **/
    @After
    public void setDown(){
        this.getService().removeById(IDENTIFIER);
    }

    /** **/
    @Test
    public void testC050004Constructor() {
        assertNotNull(message(TST_CONS, getTarget().getName()), instance(getTarget().getName(), IDENTIFIER));
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05073MConstructor() throws AbstractDatabaseException{
        new DataRecord(null);
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05074MConstructor() throws AbstractDatabaseException{
        new DataRecord("");
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05075MConstructor() throws AbstractDatabaseException{
        new DataRecord("   ");
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05076Mcolumn() throws AbstractDatabaseException {
        final Record record = new DataRecord(IDENTIFIER);
        record.column(null);
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05077Mcolumn() throws AbstractDatabaseException {
        final Record record = new DataRecord(IDENTIFIER);
        record.column("");
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05078Mcolumn() throws AbstractDatabaseException {
        final Record record = new DataRecord(IDENTIFIER);
        record.column("  ");
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05079Mget() throws AbstractDatabaseException {
        final Record record = new DataRecord(IDENTIFIER);
        record.get(null);
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05080Mget() throws AbstractDatabaseException {
        final Record record = new DataRecord(IDENTIFIER);
        record.get("");
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05081Mget() throws AbstractDatabaseException {
        final Record record = new DataRecord(IDENTIFIER);
        record.get("  ");
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05082Mset() throws AbstractDatabaseException {
        final Record record = new DataRecord(IDENTIFIER);
        record.set(null, "");
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05083Mset() throws AbstractDatabaseException {
        final Record record = new DataRecord(IDENTIFIER);
        record.set("", "");
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05084Mset() throws AbstractDatabaseException {
        final Record record = new DataRecord(IDENTIFIER);
        record.set("   ", "");
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05085Mset() throws AbstractDatabaseException {
        final Record record = new DataRecord(IDENTIFIER);
        record.set(null, STR_VAL);
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05086Mset() throws AbstractDatabaseException {
        final Record record = new DataRecord(IDENTIFIER);
        record.set("", STR_VAL);
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05087Mset() throws AbstractDatabaseException {
        final Record record = new DataRecord(IDENTIFIER);
        record.set("   ", STR_VAL);
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = FieldInvalidException.class)
    public void testE05088Mset() throws AbstractDatabaseException {
        final Record record = new DataRecord(IDENTIFIER);
        record.set("ukxx88", STR_VAL);
        failure(TST_PR);
    }

    /** **/
    @Test(expected = FieldInvalidException.class)
    public void testE05089Mset() throws AbstractDatabaseException {
        final Record record = new DataRecord(IDENTIFIER);
        record.set("ukxx89", "");
        failure(TST_PR);
    }

    /** **/
    @Test(expected = FieldInvalidException.class)
    public void testE05090Mget() throws AbstractDatabaseException {
        final Record record = new DataRecord(IDENTIFIER);
        record.get("ukxx89");
        failure(TST_PR);
    }

    /** **/
    @Test(expected = ColumnInvalidException.class)
    public void testE05091Mcolumn() throws AbstractDatabaseException {
        final Record record = new DataRecord(IDENTIFIER);
        record.column("XXX");
        failure(TST_PR);
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
