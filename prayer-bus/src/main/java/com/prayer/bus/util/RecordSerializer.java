package com.prayer.bus.util;

import static com.prayer.util.Instance.instance;
import static com.prayer.util.Instance.singleton;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import com.prayer.bus.std.SchemaService;
import com.prayer.bus.std.impl.SchemaSevImpl;
import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.exception.AbstractDatabaseException;
import com.prayer.kernel.Record;
import com.prayer.kernel.Value;
import com.prayer.kernel.model.GenericSchema;
import com.prayer.kernel.model.Transducer.V;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.type.DataType;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.InstanceOf;
import net.sf.oval.constraint.InstanceOfAny;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;
import net.sf.oval.guard.Pre;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class RecordSerializer {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================

    /** Schema Service 接口 **/
    @NotNull
    private transient final SchemaService schemaSev;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    RecordSerializer() {
        this.schemaSev = singleton(SchemaSevImpl.class);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================

    /**
     * 
     * @param parameters
     * @return
     */
    @InstanceOfAny(GenericSchema.class)
    @Pre(expr = "_this.schemaSev != null", lang = Constants.LANG_GROOVY)
    public GenericSchema extractSchema(@NotNull final JsonObject parameters) {
        final String identifier = parameters.getString(Constants.PARAM.ID);
        final ServiceResult<GenericSchema> schema = this.schemaSev.findSchema(identifier);
        GenericSchema ret = null;
        if (ResponseCode.SUCCESS == schema.getResponseCode()) {
            ret = schema.getResult();
        }
        return ret;
    }

    /**
     * 提取最终的Record数据信息
     * 
     * @param record
     * @return
     * @throws AbstractDatabaseException
     */
    public JsonObject extractRecord(@InstanceOf(Record.class) final Record record) throws AbstractDatabaseException {
        final Set<String> fields = record.fields().keySet();
        final JsonObject retObj = new JsonObject();
        for (final String field : fields) {
            if (null == record.get(field)) {
                retObj.put(field, Constants.EMPTY_STR);
            } else {
                retObj.put(field, record.get(field).literal());
            }
        }
        return retObj;
    }

    /**
     * 
     * @param identifier
     * @param data
     * @return
     */
    public Record encloseRecord(@NotNull @NotBlank @NotEmpty final String identifier, @NotNull final Class<?> recordCls,
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

    /**
     * 直接过滤retJson去除掉对应属性中的信息
     * 
     * @param retJson
     * @param filters
     */
    public void filterRecord(@NotNull final JsonObject retJson, @NotNull final JsonObject inputJson) {
        final JsonArray jsonFilters = inputJson.getJsonArray(Constants.PARAM.FILTERS);
        final Iterator<Object> filterIt = jsonFilters.iterator();
        while (filterIt.hasNext()) {
            final Object item = filterIt.next();
            if (null != item && retJson.containsKey(item.toString())) {
                retJson.remove(item.toString());
            }
        }
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
