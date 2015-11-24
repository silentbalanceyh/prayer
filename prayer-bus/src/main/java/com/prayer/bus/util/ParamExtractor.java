package com.prayer.bus.util;

import static com.prayer.util.Instance.singleton;

import java.util.Iterator;
import java.util.Set;

import com.prayer.bus.std.SchemaService;
import com.prayer.bus.std.impl.SchemaSevImpl;
import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.exception.AbstractDatabaseException;
import com.prayer.kernel.Record;
import com.prayer.kernel.model.GenericSchema;
import com.prayer.model.bus.ServiceResult;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.InstanceOf;
import net.sf.oval.constraint.InstanceOfAny;
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
public final class ParamExtractor {
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
    ParamExtractor() {
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
