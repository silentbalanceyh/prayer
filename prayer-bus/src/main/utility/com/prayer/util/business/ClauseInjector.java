package com.prayer.util.business;

import static com.prayer.util.debug.Log.jvmError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;
import com.prayer.facade.record.Record;
import com.prayer.model.business.OrderBy;
import com.prayer.model.business.Pager;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.InstanceOf;
import net.sf.oval.constraint.InstanceOfAny;
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
    @InstanceOfAny(Pager.class)
    public static Pager genPager(@NotNull final JsonObject jsonObject) {
        final JsonObject data = jsonObject.getJsonObject(Constants.PARAM.DATA);
        JsonObject pager = null;
        if (data.containsKey(Constants.PARAM.PAGE.NAME)) {
            try {
                pager = data.getJsonObject(Constants.PARAM.PAGE.NAME);
            } catch (ClassCastException ex) {
                jvmError(LOGGER,ex);
                pager = new JsonObject(data.getString(Constants.PARAM.PAGE.NAME));
            }
        }
        return null == pager ? null : null;// Pager.create(pager);
    }

    /**
     * 
     * @param jsonObject
     * @return
     */
    @InstanceOfAny(OrderBy.class)
    public static OrderBy genOrderBy(@NotNull final JsonObject jsonObject) {
        final JsonObject data = jsonObject.getJsonObject(Constants.PARAM.DATA);
        JsonArray orderArr = null;
        if (data.containsKey(Constants.PARAM.ORDERS)) {
            try {
                orderArr = data.getJsonArray(Constants.PARAM.ORDERS);
            } catch (ClassCastException ex) {
                jvmError(LOGGER,ex);
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
    @InstanceOfAny(OrderBy.class)
    public static OrderBy genOrderBy(@NotNull final JsonObject jsonObject,
            @NotNull @InstanceOf(Record.class) final Record record) {
        final JsonObject data = jsonObject.getJsonObject(Constants.PARAM.DATA);
        JsonArray orderArr = null;
        if (data.containsKey(Constants.PARAM.ORDERS)) {
            try {
                orderArr = data.getJsonArray(Constants.PARAM.ORDERS);
            } catch (ClassCastException ex) {
                jvmError(LOGGER,ex);
                orderArr = new JsonArray(data.getString(Constants.PARAM.ORDERS));
            }
        }
        return null == orderArr || null == record ? null : OrderBy.create(orderArr, record);    // NOPMD
    }
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private ClauseInjector(){}
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
