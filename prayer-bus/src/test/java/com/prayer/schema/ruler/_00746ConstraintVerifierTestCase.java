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
import com.prayer.exception.schema.BKeyConstraintInvalidException;
import com.prayer.facade.pool.JdbcConnection;
import com.prayer.facade.schema.verifier.DataValidator;
import com.prayer.pool.impl.jdbc.RecordConnImpl;

/**
 * 
 * @author Lang
 *
 */
public class _00746ConstraintVerifierTestCase extends AbstractVerifierTestCase {
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
    public _00746ConstraintVerifierTestCase() {
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
        if (null == verifier.verifyTable("TST_SUB_ROLE1")) {
            this.connection.executeBatch("DROP TABLE TST_SUB_ROLE1;");
        }
    }

    /**
     * 
     * @throws AbstractSchemaException
     */
    @Test(expected = BKeyConstraintInvalidException.class)
    public void testP00746Meta10029ECombinatedConstraints() throws AbstractException {
        // TODO: 目前只有SQL模式才检查
        if (Resources.DB_MODE.equals(DBConstants.MODE_SQL)) {
            int ret = Constants.RC_FAILURE;
            // 创建临时子表
            if (null != verifier.verifyTable("TST_SUB_ROLE1")) {
                ret = this.connection.executeBatch("CREATE TABLE TST_SUB_ROLE1( K_ID VARCHAR(236) );");
            }
            if (Constants.RC_SUCCESS == ret) {
                testImport("P00746meta-mappingE-COMBINATED10029-1.json");
                failure("[E10029] Database Constraints Error for subtable, subkey ! ");
            }
        }
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
