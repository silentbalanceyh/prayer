package com.prayer.dao.schema;

import com.prayer.exception.system.JsonParserException;
import com.prayer.exception.system.ResourceIOException;
import com.prayer.facade.database.dao.schema.Importer;
import com.prayer.facade.schema.Schema;
import com.prayer.facade.schema.verifier.Verifier;
import com.prayer.fantasm.exception.AbstractSchemaException;
import com.prayer.fantasm.exception.AbstractSystemException;
import com.prayer.schema.json.SchemaVerifier;
import com.prayer.util.io.IOKit;

import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class CommuneImporter implements Importer {

    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 获取系统验证器 **/
    @NotNull
    private transient final Verifier verifier = new SchemaVerifier();

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** 从系统中读取Schema **/
    @Override
    public Schema read(@NotNull @NotEmpty @NotBlank final String path)
            throws AbstractSchemaException, AbstractSystemException {
        /** 1.从路径中读取数据 **/
        final JsonObject data = this.readData(path);
        /** 2.验证这个Schema **/
        AbstractSchemaException error = this.verifier.verify(data);
        Schema schema = null;
        if (null == error) {
            schema = SchemaBuilder.create().build(data);
        }
        /** 3.Schema的验证通过，直接构造Schema **/
        return schema;
    }
    // ~ Private Methods =====================================

    private JsonObject readData(final String file) throws AbstractSystemException {
        final String content = IOKit.getContent(file);
        if (null == content) {
            throw new ResourceIOException(getClass(), file);
        }
        JsonObject data = null;
        try {
            data = new JsonObject(content);
        } catch (DecodeException ex) {
            throw new JsonParserException(getClass(), content);
        }
        return data;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
