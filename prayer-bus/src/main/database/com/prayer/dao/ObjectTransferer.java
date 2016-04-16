package com.prayer.dao;

import static com.prayer.util.reflection.Instance.instance;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.Resources;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.model.crucial.Value;
import com.prayer.facade.model.crucial.Transducer.V;
import com.prayer.facade.model.entity.Entity;
import com.prayer.facade.model.record.Record;
import com.prayer.facade.util.Transferer;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.fantasm.model.AbstractEntity;
import com.prayer.model.crucial.MetaRaw;
import com.prayer.model.crucial.MetaRecord;
import com.prayer.model.type.DataType;
import com.prayer.util.business.JsonTrier;
import com.prayer.util.io.PropertyKit;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.InstanceOf;
import net.sf.oval.constraint.InstanceOfAny;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 对象序列化工具
 * 
 * @author Lang
 *
 */
@Guarded
@SuppressWarnings("unchecked")
public final class ObjectTransferer implements Transferer {
    // ~ Static Fields =======================================
    /** 元数据资源文件 **/
    private static final PropertyKit LOADER = new PropertyKit(MetaRaw.class, Resources.OOB_SCHEMA_FILE);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 只转换Entity类型，即Metadata部分
     */
    @Override
    public <T extends AbstractEntity<String>> T toEntity(@NotNull @InstanceOfAny(MetaRecord.class) final Record record)
            throws AbstractDatabaseException {
        T ret = this.extractCls(record);
        final JsonObject data = this.fromRecord(record);
        final Entity entity = ret.fromJson(data);
        if (null == entity) {
            ret = null; // NOPMD
        } else {
            ret = (T) entity;
        }
        return ret;
    }

    /**
     * 只转换Entity类型，即Metadata部分
     */
    @Override
    @InstanceOfAny(MetaRecord.class)
    public <T extends AbstractEntity<String>> Record fromEntity(@NotNull @NotBlank @NotEmpty final String identifier,
            final Entity entity) throws AbstractDatabaseException {
        Record ret = null;
        if (null != entity) {
            final JsonObject data = entity.toJson();
            ret = this.toRecord(identifier, MetaRecord.class, data);
        }
        return ret;
    }

    /**
     * 直接过滤outJson去除对应属性
     * 
     * @param outJson
     * @param filters
     */
    @Override
    public void filter(@NotNull final JsonObject outJson, @NotNull final JsonObject filters) {
        final JsonArray filterArrs = filters.getJsonArray(Constants.PARAM.ADMINICLE.FILTERS);
        final Iterator<Object> filterIt = filterArrs.iterator();
        while (filterIt.hasNext()) {
            final Object item = filterIt.next();
            if (null != item && outJson.containsKey(item.toString())) {
                outJson.remove(item.toString());
            }
        }
    }

    /**
     * 将Record转换成JsonObject
     */
    @Override
    @NotNull
    public JsonObject fromRecord(@InstanceOf(Record.class) final Record record) throws AbstractDatabaseException {
        final Set<String> fields = record.fields().keySet();
        final JsonObject data = new JsonObject();
        for (final String field : fields) {
            if (null == record.get(field)) {
                data.put(field, Constants.EMPTY_STR);
            } else {
                // Fix JsonArray为null的问题
                final String literal = record.get(field).literal();
                JsonTrier.putTried(data, field, literal);
            }
        }
        return data;
    }

    /**
     * 将JsonObject转换成Record
     */
    @Override
    public Record toRecord(@NotNull @NotBlank @NotEmpty final String identifier, @NotNull final Class<?> recordCls,
            @NotNull final JsonObject data) throws AbstractDatabaseException {
        final Record record = instance(recordCls.getName(), identifier);
        final ConcurrentMap<String, DataType> fields = record.fields();
        for (final String field : fields.keySet()) {
            final DataType type = fields.get(field);
            final Value<?> value = V.get().getValue(data, type, field);
            record.set(field, value);
        }
        return record;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private <T extends AbstractEntity<String>> T extractCls(final Record record) {
        T ret = null;
        if (null != record && null != record.identifier()) {
            final String clsName = LOADER.getString(record.identifier() + ".instance");
            ret = instance(clsName);
        }
        return ret;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
