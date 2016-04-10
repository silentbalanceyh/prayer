package com.prayer.util.business;

import static com.prayer.util.Planar.flat;
import static com.prayer.util.reflection.Instance.instance;

import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import com.prayer.facade.constant.Constants;
import com.prayer.facade.kernel.Transducer.V;
import com.prayer.facade.kernel.Value;
import com.prayer.facade.record.Record;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.model.type.DataType;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.InstanceOf;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

/**
 * Record转换器
 * 
 * @author Lang
 *
 */
public final class Commutator {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 将JsonObject转换成Record
     **/
    public Record enclose(@NotNull @NotBlank @NotEmpty final String identifier, @NotNull final Class<?> entityCls,
            @NotNull final JsonObject data) throws AbstractDatabaseException {
        final Record record = instance(entityCls, identifier);
        final ConcurrentMap<String, DataType> fields = record.fields();
        for (final String field : fields.keySet()) {
            final DataType type = fields.get(field);
            final Value<?> value = V.get().getValue(data, type, field);
            record.set(field, value);
        }
        return record;
    }

    /**
     * 将Record转换成JsonObject
     * 
     * @param record
     * @return
     * @throws AbstractDatabaseException
     */
    public JsonObject extract(@InstanceOf(Record.class) final Record record) throws AbstractDatabaseException {
        final Set<String> fields = record.fields().keySet();
        final JsonObject data = new JsonObject();
        for (final String field : fields) {
            final Value<?> value = record.get(field);
            data.put(field, flat(null == value, Constants.EMPTY_STR, value.literal()));
        }
        return data;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
