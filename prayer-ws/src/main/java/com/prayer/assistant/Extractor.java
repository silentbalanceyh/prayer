package com.prayer.assistant;

import com.prayer.constant.Constants;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.h2.vx.UriModel;
import com.prayer.model.web.Requestor;

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
        Integer integer = Integer.valueOf(Constants.RANGE);
        try {
            // 过滤null值
            if (config.containsKey(key)) {
                integer = config.getInteger(key);
            }
        } catch (ClassCastException ex) {
            integer = null; // NOPMD
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
        String str = Constants.EMPTY_STR;
        try {
            if (config.containsKey(key)) {
                str = config.getString(key);
            }
        } catch (ClassCastException ex) {
            str = null; // NOPMD
        }
        return str;
    }

    /**
     * 
     * @param jsonArray
     * @return
     */
    public static String getContent(@NotNull final ServiceResult<JsonArray> jsonArray) {
        final JsonArray retArray = jsonArray.getResult();
        String str = Constants.EMPTY_JARR;
        if (Constants.ONE == retArray.size()) {
            str = retArray.getJsonObject(0).encode();
        } else {
            str = retArray.encode();
        }
        return str;
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
