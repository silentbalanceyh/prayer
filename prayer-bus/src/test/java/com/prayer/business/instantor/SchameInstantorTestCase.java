package com.prayer.business.instantor;

import static com.prayer.util.debug.Log.peError;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.business.AbstractInstantor;
import com.prayer.exception.database.SchemaNotFoundException;
import com.prayer.facade.business.instantor.schema.SchemaInstantor;
import com.prayer.facade.schema.Schema;
import com.prayer.fantasm.exception.AbstractException;

/**
 * 
 * @author Lang
 *
 */
public class SchameInstantorTestCase extends AbstractInstantor {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(SchameInstantorTestCase.class);
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    // ~ Methods =============================================
    /**
     * 验证Schema的导入，成功导入过后，系统中应该有对应的表
     * 
     * @throws AbstractException
     */
    @Test
    public void testSchemaFlow() {
        boolean ret = false;
        final SchemaInstantor instantor = this.getSchemaItor();
        String expId = null;
        try {
            /** 1.导入Schema信息 **/
            final Schema schema = instantor.importSchema(path("instantor1-schema-flow.json"));
            /** 1.1.抽取identifier **/
            expId = schema.identifier();

            final Schema synced = instantor.syncMetadata(schema);
            /** 2.验证Database中元数据是否导入成功 **/
            AbstractException error = this.validator().verifyTable(synced.getTable());
            assertNull(error);
            /** 3.查询H2中数据库是否成功 **/
            final Schema queried = instantor.findById(schema.identifier());
            assertNotNull(queried);
            /** 4.比较同步和读取出来的Schema **/
            assertTrue(synced.equals(queried));
            /** 5.比较成功则直接删除 **/
            if (null != queried) {
                /** 6.删除当前Metadata **/
                instantor.removeById(schema.identifier());
                /** 7.删除后验证 **/
                error = this.validator().verifyTable(schema.getTable());
                assertNotNull(error);
                /** 8.删除后H2验证 **/
                Schema deleted = null;
                try {
                    deleted = instantor.findById(schema.identifier());
                } catch (SchemaNotFoundException ex) {
                    peError(getLogger(), ex);
                }
                assertNull(deleted);
            }
            /** 9.整个Schema Flow的测试完成 **/
            ret = true;
        } catch (AbstractException ex) {
            peError(getLogger(), ex);
            if (null != expId) {
                /** 如果expId不为空产生异常，则直接移除 **/
                try {
                    instantor.removeById(expId);
                } catch (AbstractException exp) {
                    peError(getLogger(), exp);
                }
            }
            ret = false;
        }
        assertTrue(ret);
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
