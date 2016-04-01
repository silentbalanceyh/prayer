package com.prayer.record.initialize;

import static com.prayer.util.reflection.Instance.instance;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.AbstractCommonTool;
import com.prayer.exception.database.ColumnInvalidException;
import com.prayer.exception.database.FieldInvalidException;
import com.prayer.facade.kernel.Value;
import com.prayer.facade.record.Record;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.model.crucial.MetaRecord;
import com.prayer.model.type.StringType;

import net.sf.oval.exception.ConstraintsViolatedException;

/**
 * 
 * @author Lang
 *
 */
public class MetaRecord01TestCase extends AbstractCommonTool {

    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(MetaRecord01TestCase.class);
    /** **/
    private static final String IDENTIFIER = "meta-script";
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
        return MetaRecord.class;
    }

    // ~ Methods =============================================
    /** **/
    @Test
    public void testC050104Constructor() {
        assertNotNull(message(TST_CONS, getTarget().getName()), instance(getTarget().getName(), IDENTIFIER));
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05173MConstructor() throws AbstractDatabaseException {
        new MetaRecord(null);
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05174MConstructor() throws AbstractDatabaseException {
        new MetaRecord("");
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05175MConstructor() throws AbstractDatabaseException {
        new MetaRecord("   ");
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05176Mcolumn() throws AbstractDatabaseException {
        final Record record = new MetaRecord(IDENTIFIER);
        record.column(null);
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05177Mcolumn() throws AbstractDatabaseException {
        final Record record = new MetaRecord(IDENTIFIER);
        record.column("");
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05178Mcolumn() throws AbstractDatabaseException {
        final Record record = new MetaRecord(IDENTIFIER);
        record.column("  ");
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05179Mget() throws AbstractDatabaseException {
        final Record record = new MetaRecord(IDENTIFIER);
        record.get(null);
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05180Mget() throws AbstractDatabaseException {
        final Record record = new MetaRecord(IDENTIFIER);
        record.get("");
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05181Mget() throws AbstractDatabaseException {
        final Record record = new MetaRecord(IDENTIFIER);
        record.get("  ");
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05182Mset() throws AbstractDatabaseException {
        final Record record = new MetaRecord(IDENTIFIER);
        record.set(null, "");
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05183Mset() throws AbstractDatabaseException {
        final Record record = new MetaRecord(IDENTIFIER);
        record.set("", "");
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05184Mset() throws AbstractDatabaseException {
        final Record record = new MetaRecord(IDENTIFIER);
        record.set("   ", "");
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05185Mset() throws AbstractDatabaseException {
        final Record record = new MetaRecord(IDENTIFIER);
        record.set(null, STR_VAL);
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05186Mset() throws AbstractDatabaseException {
        final Record record = new MetaRecord(IDENTIFIER);
        record.set("", STR_VAL);
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05187Mset() throws AbstractDatabaseException {
        final Record record = new MetaRecord(IDENTIFIER);
        record.set("   ", STR_VAL);
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = FieldInvalidException.class)
    public void testE05188Mset() throws AbstractDatabaseException {
        final Record record = new MetaRecord(IDENTIFIER);
        record.set("ukxx88", STR_VAL);
        failure(TST_PR);
    }

    /** **/
    @Test(expected = FieldInvalidException.class)
    public void testE05189Mset() throws AbstractDatabaseException {
        final Record record = new MetaRecord(IDENTIFIER);
        record.set("ukxx89", "");
        failure(TST_PR);
    }

    /** **/
    @Test(expected = FieldInvalidException.class)
    public void testE05190Mget() throws AbstractDatabaseException {
        final Record record = new MetaRecord(IDENTIFIER);
        record.get("ukxx89");
        failure(TST_PR);
    }

    /** **/
    @Test(expected = ColumnInvalidException.class)
    public void testE05191Mcolumn() throws AbstractDatabaseException {
        final Record record = new MetaRecord(IDENTIFIER);
        record.column("XXX");
        failure(TST_PR);
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
