package com.prayer.model.kernel;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.AbstractRDaoTestTool;
import com.prayer.Assistant;
import com.prayer.exception.system.SchemaNotFoundException;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.kernel.GenericSchema;
import com.prayer.model.kernel.SchemaExpander;
import com.prayer.util.cv.SystemEnum.ResponseCode;

import net.sf.oval.exception.ConstraintsViolatedException;

/**
 * 
 * @author Lang
 *
 */
public class SchemaExpander01TestCase extends AbstractRDaoTestTool { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(SchemaExpander01TestCase.class);
    /** **/
    private static final String DB_CATEGORY = "MSSQL";

    // ~ Instance Fields =====================================
    /** **/
    private transient GenericSchema schema;

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
        return SchemaExpander.class;
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
        if (null == this.schema) {
            final ServiceResult<GenericSchema> ret = this.syncMetadata("MsSqlP001TestDAO2.json","tst.mod.dao2");
            if (ResponseCode.FAILURE == ret.getResponseCode()) {
                failure(TST_PREP, ret.getErrorMessage());
            } else {
                this.schema = ret.getResult();
            }
        }
    }

    // ~ Methods =============================================
    /** **/
    @Test
    public void testC05003Constructor() {
        assertNotNull(message(TST_CONS, getTarget().getName()), Assistant.instance(getTarget()));
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05063MtoKeysMap() throws SchemaNotFoundException {
        SchemaExpander.toKeysMap(null);
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05064MtoFieldsMap() throws SchemaNotFoundException {
        SchemaExpander.toFieldsMap(null);
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05065MgetColumn() throws SchemaNotFoundException {
        SchemaExpander.getColumn(null, "TEST");
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05066MgetColumn() throws SchemaNotFoundException {
        SchemaExpander.getColumn(this.schema.getFields(), null);
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05067MgetColumn() throws SchemaNotFoundException {
        SchemaExpander.getColumn(this.schema.getFields(), "");
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05068MgetColumn() throws SchemaNotFoundException {
        SchemaExpander.getColumn(this.schema.getFields(), "  ");
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05069MgetColumns() throws SchemaNotFoundException {
        SchemaExpander.getColumns(null);
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05070MgetPrimaryKeys() throws SchemaNotFoundException {
        SchemaExpander.getPrimaryKeys(null);
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05071MgetForeignKey() throws SchemaNotFoundException {
        SchemaExpander.getForeignKey(null);
        failure(TST_OVAL);
    }

    /** **/
    @Test(expected = ConstraintsViolatedException.class)
    public void testE05072MgetForeignField() throws SchemaNotFoundException {
        SchemaExpander.getForeignField(null);
        failure(TST_OVAL);
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
