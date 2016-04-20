package com.prayer.metaserver.h2.util;

import java.util.ArrayList;
import java.util.List;

import com.prayer.facade.constant.Constants;
import com.prayer.facade.constant.Symbol;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jodd.util.StringUtil;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class ParamsResolver {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /** Server List **/
    public static String resolve(final JsonArray arrs, final String host) {
        final int length = arrs.size();
        final List<String> params = new ArrayList<>();
        for (int idx = 0; idx < length; idx++) {
            final StringBuilder param = new StringBuilder();
            final JsonObject item = arrs.getJsonObject(idx);
            if (item.containsKey("host")) {
                param.append(item.getString("host"));
            } else {
                param.append(host);
            }
            param.append(Symbol.COLON);
            param.append(item.getInteger("tcp.port"));
            params.add(param.toString());
        }
        return StringUtil.join(params.toArray(Constants.T_STR_ARR), Symbol.COMMA);
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private ParamsResolver() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
