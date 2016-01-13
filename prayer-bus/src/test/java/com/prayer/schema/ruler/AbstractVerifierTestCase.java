package com.prayer.schema.ruler;

import static org.junit.Assert.fail;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.base.exception.AbstractSystemException;
import com.prayer.exception.system.JsonParserException;
import com.prayer.exception.system.ResourceIOException;
import com.prayer.facade.kernel.Schema;
import com.prayer.facade.schema.verifier.Verifier;
import com.prayer.schema.json.SchemaVerifier;
import com.prayer.util.io.IOKit;

import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public class AbstractVerifierTestCase {
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
    protected void failure(final Object object) {
        fail("[T] Failure assert : " + object);
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
