package com.prayer.schema.ruler;

import static com.prayer.constant.Accessors.validator;
import static com.prayer.util.reflection.Instance.reservoir;
import static com.prayer.util.reflection.Instance.singleton;

import org.junit.After;
import org.junit.Test;

import com.prayer.base.exception.AbstractException;
import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.constant.Constants;
import com.prayer.constant.DBConstants;
import com.prayer.constant.MemoryPool;
import com.prayer.constant.Resources;
import com.prayer.exception.schema.BTColumnNotExistingException;
import com.prayer.facade.pool.JdbcConnection;
import com.prayer.facade.schema.verifier.DataValidator;
import com.prayer.pool.impl.jdbc.RecordConnImpl;

/**
 * 
 * @author Lang
 *
 */
public class _00745ColumnVerifierTestCase extends AbstractVerifierTestCase {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    private transient final DataValidator verifier;

    /** **/
    protected transient JdbcConnection connection;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     */
    public _00745ColumnVerifierTestCase() {
        this.verifier = reservoir(MemoryPool.POOL_VALIDATOR, Resources.DB_CATEGORY, validator());
        this.connection = singleton(RecordConnImpl.class);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    @After
    public void setDown() {
        // For testP019Subtable1Rel10013();
        // 如果存在该表就删除
        if (null == verifier.verifyTable("TST_SUB_ROLE")) {
            this.connection.executeBatch("DROP TABLE TST_SUB_ROLE;");
        }
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = BTColumnNotExistingException.class)
    public void testP00745Meta10028ECombinatedColumn() throws AbstractException {
        // TODO: 目前只有SQL模式才检查
        if (Resources.DB_MODE.equals(DBConstants.MODE_SQL)) {
            int ret = Constants.RC_FAILURE;
            // 创建临时子表
            if (null != verifier.verifyTable("TST_SUB_ROLE")) {
                ret = this.connection.executeBatch("CREATE TABLE TST_SUB_ROLE( K_ID VARCHAR(236) );");
            }
            if (Constants.RC_SUCCESS == ret) {
                importFile("P00745meta-mappingE-COMBINATED10028-1.json");
                failure("[E10028] Column of table does not exist! ");
            }
        }
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
