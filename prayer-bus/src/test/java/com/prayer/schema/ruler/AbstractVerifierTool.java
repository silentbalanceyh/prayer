package com.prayer.schema.ruler;

import static com.prayer.util.reflection.Instance.reservoir;
import static com.prayer.util.reflection.Instance.singleton;
import static org.junit.Assert.fail;

import com.prayer.constant.Accessors;
import com.prayer.constant.DBConstants;
import com.prayer.constant.MemoryPool;
import com.prayer.constant.Resources;
import com.prayer.database.pool.impl.jdbc.JdbcConnImpl;
import com.prayer.exception.system.JsonParserException;
import com.prayer.exception.system.ResourceIOException;
import com.prayer.facade.pool.JdbcConnection;
import com.prayer.facade.schema.Schema;
import com.prayer.facade.schema.verifier.DataValidator;
import com.prayer.facade.schema.verifier.Verifier;
import com.prayer.fantasm.exception.AbstractSchemaException;
import com.prayer.fantasm.exception.AbstractSystemException;
import com.prayer.schema.json.SchemaVerifier;
import com.prayer.util.io.IOKit;

import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public class AbstractVerifierTool {
    // ~ Static Fields =======================================
    /** **/
    protected static final String SCHEMA_ROOT = "/schema/data/json/validation/";
    // ~ Instance Fields =====================================
    /** 获取验证器 **/
    private transient final Verifier verifier = new SchemaVerifier();

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** 获取当前系统验证器 **/
    protected Verifier getVerifier() {
        return this.verifier;
    }

    /** 根据文件路径获取对应的JsonObject **/
    protected JsonObject getData(final String file) throws AbstractSystemException {
        final String path = SCHEMA_ROOT + file;
        final String content = IOKit.getContent(path);
        if (null == content) {
            throw new ResourceIOException(getClass(), path);
        }
        JsonObject data = new JsonObject();
        try {
            data = new JsonObject(content);
        } catch (DecodeException ex) {
            throw new JsonParserException(getClass(), content);
        }
        return data;
    }

    /** **/
    protected Schema testImport(final String file) throws AbstractSystemException, AbstractSchemaException {
        /**
         * 1.基本验证：AbstractSystemException
         */
        final JsonObject data = this.getData(file);
        /**
         * 2.验证节点数据
         */
        final AbstractSchemaException error = this.verifier.verify(data);
        if (null != error) {
            throw error;
        }
        return null;
    }

    /** **/
    protected DataValidator validator() {
        return reservoir(MemoryPool.POOL_VALIDATOR, Resources.DB_CATEGORY, Accessors.validator());
    }

    /** **/
    protected JdbcConnection connection() {
        return singleton(JdbcConnImpl.class);
    }

    /** 删除表 **/
    protected void purgeTable(final String table) {
        // TODO: 目前只有SQL模式才检查
        if (Resources.DB_MODE.equals(DBConstants.MODE_SQL)) {
            if (null == validator().verifyTable(table)) {
                this.connection().executeBatch("DROP TABLE " + table + ";");
            }
        }
    }

    /** **/
    protected void failure(final Object object) {
        fail("[T] Failure assert : " + object);
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
