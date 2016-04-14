package com.prayer.dao.schema;

import java.util.ArrayList;
import java.util.List;

import com.prayer.exception.system.SerializationException;
import com.prayer.facade.database.dao.schema.Serializer;
import com.prayer.model.meta.database.PEField;
import com.prayer.model.meta.database.PEIndex;
import com.prayer.model.meta.database.PEKey;
import com.prayer.model.meta.database.PEMeta;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class JsonSerializer implements Serializer {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public PEMeta transferMeta(@NotNull final JsonObject meta) throws SerializationException {
        return new PEMeta(meta);
    }

    /** **/
    @Override
    public List<PEKey> transferKeys(@NotNull final JsonArray keys) throws SerializationException {
        final List<PEKey> entities = new ArrayList<>();
        for (final Object key : keys) {
            if (null != key && JsonObject.class == key.getClass()) {
                final JsonObject item = (JsonObject) key;
                final PEKey entity = new PEKey(item);
                entities.add(entity);
            }
        }
        return entities;
    }

    /** **/
    @Override
    public List<PEField> transferFields(@NotNull final JsonArray fields) throws SerializationException {
        final List<PEField> entities = new ArrayList<>();
        for (final Object field : fields) {
            if (null != field && JsonObject.class == field.getClass()) {
                final JsonObject item = (JsonObject) field;
                final PEField entity = new PEField(item);
                entities.add(entity);
            }
        }
        return entities;
    }

    /** **/
    @Override
    public List<PEIndex> transferIndexes(@NotNull final JsonArray indexes) throws SerializationException {
        // TODO Auto-generated method stub
        return null;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
