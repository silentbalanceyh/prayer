package com.prayer.model.kernel;

import static com.prayer.util.debug.Log.info;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.AbstractRDaoTestTool;
import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.database.FieldModel;
import com.prayer.model.database.KeyModel;

/**
 * 
 * @author Lang
 *
 */
public class SchemaExpander02TestCase extends AbstractRDaoTestTool { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(SchemaExpander02TestCase.class);
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
            final ServiceResult<GenericSchema> ret = this.syncMetadata("MsSqlP001TestDAO2.json", "tst.mod.dao2");
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
    public void testT05029MtoKeysMap() {
        final ConcurrentMap<String, KeyModel> keys = SchemaExpander
                .toKeysMap(Arrays.asList(this.schema.getKeys().values().toArray(new KeyModel[] {})));
        assertEquals(message(TST_EQUAL), 4, keys.size());
    }

    /** **/
    @Test
    public void testT05030MtoKeysMap() {
        final ConcurrentMap<String, KeyModel> keys = SchemaExpander
                .toKeysMap(Arrays.asList(this.schema.getKeys().values().toArray(new KeyModel[] {})));
        final List<String> actualArr = Arrays.asList(keys.keySet().toArray(Constants.T_STR_ARR));
        final List<String> expectedArr = Arrays.asList(this.schema.getKeys().keySet().toArray(Constants.T_STR_ARR));
        Collections.sort(actualArr);
        Collections.sort(expectedArr);
        assertArrayEquals(expectedArr.toArray(), actualArr.toArray());
    }

    /** **/
    @Test
    public void testT05031MtoFieldsMap() {
        final ConcurrentMap<String, FieldModel> fields = SchemaExpander
                .toFieldsMap(Arrays.asList(this.schema.getFields().values().toArray(new FieldModel[] {})));
        assertEquals(message(TST_EQUAL), 5, fields.size());
    }

    /** **/
    @Test
    public void testT05032MtoFieldsMap() {
        final ConcurrentMap<String, FieldModel> fields = SchemaExpander
                .toFieldsMap(Arrays.asList(this.schema.getFields().values().toArray(new FieldModel[] {})));
        final List<String> actualArr = Arrays.asList(fields.keySet().toArray(Constants.T_STR_ARR));
        final List<String> expectedArr = Arrays.asList(this.schema.getFields().keySet().toArray(Constants.T_STR_ARR));
        Collections.sort(actualArr);
        Collections.sort(expectedArr);
        assertArrayEquals(expectedArr.toArray(), actualArr.toArray());
    }

    /** **/
    @Test
    public void testT05033MgetColumns() {
        final List<String> expectedArr = Arrays.asList(this.schema.getColumns().toArray(Constants.T_STR_ARR));
        final List<String> actualArr = Arrays
                .asList(SchemaExpander.getColumns(this.schema.getFields()).toArray(Constants.T_STR_ARR));
        Collections.sort(actualArr);
        Collections.sort(expectedArr);
        assertArrayEquals(expectedArr.toArray(), actualArr.toArray());
    }

    /** **/
    @Test
    public void testT05034MgetColumn() {
        final FieldModel expected = this.schema.getColumn("T_UK1");
        final FieldModel actual = SchemaExpander.getColumn(this.schema.getFields(), "T_UK1");
        final boolean ret = expected.equals(actual);
        assertTrue(message(TST_TF, Boolean.TRUE), ret);
    }

    /** **/
    @Test
    public void testT05035MgetPrimaryKeys() {
        final List<FieldModel> expectedArr = this.schema.getPrimaryKeys();
        final List<FieldModel> actualArr = SchemaExpander.getPrimaryKeys(this.schema.getFields());
        assertEquals(message(TST_EQUAL), expectedArr.size(), actualArr.size());
    }

    /** **/
    @Test
    public void testT05036MgetForeignKey() {
        final List<KeyModel> expecteds = this.schema.getForeignKey();
        final List<KeyModel> actuals = SchemaExpander.getForeignKey(this.schema.getKeys());
        for (int idx = 0; idx < expecteds.size(); idx++) {
            final KeyModel expected = expecteds.get(idx);
            final KeyModel actual = actuals.get(idx);
            final boolean ret = expected.equals(actual);
            assertTrue(message(TST_TF, Boolean.TRUE), ret);
        }
    }

    /** **/
    @Test
    public void testT05037MgetForeignKey() {
        final ServiceResult<GenericSchema> ret = this.syncMetadata("MsSqlP001TestDAO1.json", "tst.mod.dao1");
        if (ResponseCode.SUCCESS == ret.getResponseCode()) {
            final GenericSchema schema = ret.getResult();
            final List<KeyModel> actual = SchemaExpander.getForeignKey(schema.getKeys());
            assertEquals(message(TST_NULL), Constants.ZERO, actual.size());
        } else {
            info(getLogger(), "[T] ==> " + ret.getErrorMessage());
        }
    }

    /** **/
    @Test
    public void testT05038MgetForeignField() {
        final List<FieldModel> expecteds = this.schema.getForeignField();
        final List<FieldModel> actuals = SchemaExpander.getForeignField(this.schema.getFields());
        for (int idx = 0; idx < expecteds.size(); idx++) {
            final FieldModel expected = expecteds.get(idx);
            final FieldModel actual = actuals.get(idx);
            final boolean ret = expected.equals(actual);
            assertTrue(message(TST_TF, Boolean.TRUE), ret);
        }
    }

    /** **/
    @Test
    public void testT05039MgetForeignField() {
        final ServiceResult<GenericSchema> ret = this.syncMetadata("MsSqlP001TestDAO1.json", "tst.mod.dao1");
        if (ResponseCode.SUCCESS == ret.getResponseCode()) {
            final GenericSchema schema = ret.getResult();
            final List<FieldModel> actual = SchemaExpander.getForeignField(schema.getFields());
            assertEquals(message(TST_NULL), Constants.ZERO, actual.size());
        } else {
            info(getLogger(), "[T] ==> " + ret.getErrorMessage());
        }
    }
    // ~ Private Methods =====================================

    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
