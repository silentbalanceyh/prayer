package com.prayer.bus.std.impl;

import static com.prayer.bus.util.BusLogger.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;
import com.prayer.kernel.Record;
import com.prayer.kernel.query.OrderBy;
import com.prayer.kernel.query.Pager;

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
public final class ClauseInjector {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(ClauseInjector.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 
     * @param jsonObject
     * @return
     */
    public static Pager genPager(@NotNull final JsonObject jsonObject) {
        final JsonObject data = jsonObject.getJsonObject(Constants.PARAM.DATA);
        JsonObject pager = null;
        if (data.containsKey(Constants.PARAM.PAGE.NAME)) {
            try {
                pager = data.getJsonObject(Constants.PARAM.PAGE.NAME);
            } catch (ClassCastException ex) {
                info(LOGGER, " Convert to String paring: " + ex.toString());
                pager = new JsonObject(data.getString(Constants.PARAM.PAGE.NAME));
            }
        }
        return null == pager ? null : Pager.create(pager);
    }

    /**
     * 
     * @param jsonObject
     * @return
     */
    public static OrderBy genOrderBy(@NotNull final JsonObject jsonObject) {
        final JsonObject data = jsonObject.getJsonObject(Constants.PARAM.DATA);
        JsonArray orderArr = null;
        if (data.containsKey(Constants.PARAM.ORDERS)) {
            try {
                orderArr = data.getJsonArray(Constants.PARAM.ORDERS);
            } catch (ClassCastException ex) {
                info(LOGGER, " Convert to String parsing : " + ex.toString());
                orderArr = new JsonArray(data.getString(Constants.PARAM.ORDERS));
            }
        }
        return null == orderArr ? null : OrderBy.create(orderArr);
    }

    /**
     * 
     * @param jsonObject
     * @return
     */
    public static OrderBy genOrderBy(@NotNull final JsonObject jsonObject, @NotNull final Record record) {
        final JsonObject data = jsonObject.getJsonObject(Constants.PARAM.DATA);
        JsonArray orderArr = null;
        if (data.containsKey(Constants.PARAM.ORDERS)) {
            try {
                orderArr = data.getJsonArray(Constants.PARAM.ORDERS);
            } catch (ClassCastException ex) {
                info(LOGGER, " Convert to String parsing : " + ex.toString());
                orderArr = new JsonArray(data.getString(Constants.PARAM.ORDERS));
            }
        }
        return null == orderArr || null == record ? null : OrderBy.create(orderArr, record);
    }
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
