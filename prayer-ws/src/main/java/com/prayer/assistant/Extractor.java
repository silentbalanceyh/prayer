package com.prayer.assistant;

import com.prayer.model.bus.ServiceResult;
import com.prayer.model.h2.vertx.UriModel;
import com.prayer.model.web.JsonKey;
import com.prayer.model.web.Requestor;
import com.prayer.model.web.Responsor;
import com.prayer.model.web.StatusCode;
import com.prayer.util.cv.Constants;
import com.prayer.util.cv.SystemEnum.ResponseCode;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
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
public final class Extractor {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    /** **/
    public static Integer getNumber(@NotNull final JsonObject config, @NotNull @NotBlank @NotEmpty final String key) {
        Integer integer = null;
        final Object value = config.getValue(key);
        if (null != value) {
            final Class<?> type = value.getClass();
            if (Integer.class == type) {
                integer = config.getInteger(key);
            }
        }
        return integer;
    }

    /**
     * 
     * @param config
     * @param key
     * @return
     */
    public static String getString(@NotNull final JsonObject config, @NotNull @NotBlank @NotEmpty final String key) {
        String str = null;
        final Object value = config.getValue(key);
        if (null != value) {
            final Class<?> type = value.getClass();
            if (String.class == type) {
                str = config.getString(key);
            }
        }
        return str;
    }

    /**
     * 
     * @param jsonArray
     * @return
     */
    public static String getContent(@NotNull final ServiceResult<JsonArray> jsonArray) {
        String str = Constants.EMPTY_JARR;
        if (jsonArray.getResponseCode() == ResponseCode.SUCCESS) {
            final JsonArray retArray = jsonArray.getResult();
            if (null != retArray) {
                if (Constants.ONE == retArray.size()) {
                    str = retArray.getJsonObject(0).encode();
                } else {
                    str = retArray.encode();
                }
            }
        } else {
            // Business Service Error
            final JsonObject ret = new JsonObject();
            ret.put(JsonKey.RESPONSOR.ERROR.CODE, jsonArray.getErrorCode());
            ret.put(JsonKey.RESPONSOR.ERROR.MESSAGE, jsonArray.getErrorMessage());
            ret.put(JsonKey.RESPONSOR.ERROR.DISPLAY, jsonArray.getErrorMessage());
            // Error Node
            final JsonObject error = new JsonObject();
            error.put(JsonKey.RESPONSOR.ERROR.NAME, ret);
            str = error.encode();
        }
        return str;
    }

    /**
     * 
     * @param sevRets
     * @return
     */
    public static Responsor responsor(@NotNull final ServiceResult<?> sevRets, @NotNull final StatusCode status) {
        Responsor ret = null;
        if (ResponseCode.SUCCESS == sevRets.getResponseCode()) {
            if (null != sevRets.getResult()) {
                if (sevRets.getResult() instanceof JsonObject) {
                    ret = Responsor.success((JsonObject) sevRets.getResult());
                } else if (sevRets.getResult() instanceof JsonArray) {
                    ret = Responsor.success((JsonArray) sevRets.getResult());
                }
            }
        } else if (ResponseCode.FAILURE == sevRets.getResponseCode()) {
            ret = Responsor.failure(status, sevRets.getServiceError());
        } else {
            ret = Responsor.error(sevRets.getServiceError());
        }
        return ret;
    }

    /**
     * 
     * @param context
     * @return
     */
    public static Requestor requestor(@NotNull final RoutingContext context) {
        Requestor requestor = context.get(Constants.KEY.CTX_REQUESTOR);
        if (null == requestor) {
            requestor = Requestor.create(context);
        }
        return requestor;
    }

    /**
     * 
     * @param requestor
     * @return
     */
    public static UriModel uri(@NotNull final RoutingContext context) {
        final UriModel ret = context.get(Constants.KEY.CTX_URI);
        return ret;
    }

    /**
     * 
     * @param context
     * @return
     */
    public static String path(@NotNull final RoutingContext context) {
        return null == context.get(Constants.KEY.CTX_FINAL_URL) ? context.request().path()
                : context.get(Constants.KEY.CTX_FINAL_URL);
    }

    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private Extractor() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
