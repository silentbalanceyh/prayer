package com.prayer.dao.impl.schema;

import static com.prayer.util.reflection.Instance.singleton;

import com.prayer.base.exception.AbstractDatabaseException;
import com.prayer.base.exception.AbstractSystemException;
import com.prayer.facade.dao.schema.Serializer;
import com.prayer.facade.schema.Schema;
import com.prayer.facade.schema.verifier.Attributes;
import com.prayer.model.crucial.schema.JsonSchema;
import com.prayer.model.meta.database.PEField;
import com.prayer.model.meta.database.PEKey;
import com.prayer.model.meta.database.PEMeta;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * Schema OldBuilder
 * 
 * @author Lang
 *
 */
@Guarded
public final class SchemaBuilder {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 序列化生成器 **/
    private transient Serializer serializer;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 直接构造SchemaBuilder
     * 
     * @return
     */
    public static SchemaBuilder create() {
        return singleton(SchemaBuilder.class);
    }

    /**
     * 从一个JsonObject构造Schema
     * 
     * @param data
     * @return
     * @throws AbstractSystemException
     */
    public Schema build(@NotNull final JsonObject data) throws AbstractSystemException {
        final Schema schema = new JsonSchema();
        /** 1.构造Meta **/
        schema.meta(this.transferMeta(data));
        /** 2.构造Keys **/
        schema.keys(this.transferKeys(data));
        /** 3.构造Fields **/
        schema.fields(this.transferFields(data));
        /** 4.构造Indexes **/
        // TODO: Index的构造
        return schema;
    }

    /**
     * 通过identifier从系统中读取一个完整的Schema
     * 
     * @param identifier
     * @return
     * @throws AbstractDatabaseException
     */
    public Schema build(@NotNull @NotBlank @NotEmpty final String identifier) throws AbstractDatabaseException {
        // TODO: 等待Schema Dao
        return null;
    }

    // ~ Constructors ========================================
    private SchemaBuilder() {
        this.serializer = singleton(JsonSerializer.class);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Functional Interface ================================
    // ~ Private Methods =====================================

    private PEField[] transferFields(final JsonObject data) throws AbstractSystemException {
        PEField[] fields = new PEField[] {};
        if (data.containsKey(Attributes.R_FIELDS)) {
            fields = this.serializer.transferFields(data.getJsonArray(Attributes.R_FIELDS)).toArray(fields);
        }
        return fields;
    }

    private PEKey[] transferKeys(final JsonObject data) throws AbstractSystemException {
        PEKey[] keys = new PEKey[] {};
        if (data.containsKey(Attributes.R_KEYS)) {
            keys = this.serializer.transferKeys(data.getJsonArray(Attributes.R_KEYS)).toArray(keys);
        }
        return keys;
    }

    private PEMeta transferMeta(final JsonObject data) throws AbstractSystemException {
        final PEMeta meta = new PEMeta();
        if (data.containsKey(Attributes.R_META)) {
            meta.fromJson(data.getJsonObject(Attributes.R_META));
        }
        return meta;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
