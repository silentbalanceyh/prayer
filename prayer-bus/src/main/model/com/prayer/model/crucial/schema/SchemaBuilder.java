package com.prayer.model.crucial.schema;

import static com.prayer.util.reflection.Instance.singleton;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.base.exception.AbstractDatabaseException;
import com.prayer.base.exception.AbstractSystemException;
import com.prayer.facade.kernel.Schema;
import com.prayer.facade.schema.Serializer;
import com.prayer.facade.schema.verifier.Attributes;
import com.prayer.model.meta.database.PEField;
import com.prayer.model.meta.database.PEKey;
import com.prayer.model.meta.database.PEMeta;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * Schema Builder
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
        final JsonSchema schema = new JsonSchema();
        /** 1.构造Meta **/
        schema.setMeta(this.transferMeta(data));
        /** 2.构造Keys **/
        schema.setKeys(this.transferKeys(data));
        /** 3.构造Fields **/
        schema.setFields(this.transferFields(data));
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

    private ConcurrentMap<String, PEField> transferFields(final JsonObject data) throws AbstractSystemException {
        final ConcurrentMap<String, PEField> retMap = new ConcurrentHashMap<>();
        if (data.containsKey(Attributes.R_FIELDS)) {
            for (final PEField field : this.serializer.transferFields(data.getJsonArray(Attributes.R_FIELDS))) {
                retMap.put(field.getName(), field);
            }
        }
        return retMap;
    }

    private ConcurrentMap<String, PEKey> transferKeys(final JsonObject data) throws AbstractSystemException {
        final ConcurrentMap<String, PEKey> retMap = new ConcurrentHashMap<>();
        if (data.containsKey(Attributes.R_KEYS)) {
            for (final PEKey key : this.serializer.transferKeys(data.getJsonArray(Attributes.R_KEYS))) {
                retMap.put(key.getName(), key);
            }
        }
        return retMap;
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
