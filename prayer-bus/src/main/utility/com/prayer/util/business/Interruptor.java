package com.prayer.util.business;

import static com.prayer.util.debug.Log.jvmError;
import static com.prayer.util.debug.Log.peError;

import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.web.PrimaryKeyMissingException;
import com.prayer.exception.web.ServiceParamInvalidException;
import com.prayer.exception.web.ServiceParamMissingException;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.kernel.Value;
import com.prayer.facade.record.Record;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.util.string.StringKit;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.InstanceOf;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/** **/
@Guarded
@Deprecated
public final class Interruptor { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(Interruptor.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    /**
     * 
     * @param clazz
     * @param jsonObject
     * @return
     */
    public static AbstractException interruptPageParams(@NotNull final Class<?> clazz,
            @NotNull final JsonObject jsonObject) {
        AbstractException error = null;
        try {
            final JsonObject data = jsonObject.getJsonObject(Constants.PARAM.DATA);
            if (data.containsKey(Constants.PARAM.PAGE.NAME)) {
                final JsonObject page = data.getJsonObject(Constants.PARAM.PAGE.NAME);
                if (!(page.containsKey(Constants.PARAM.PAGE.PAGE_INDEX)
                        && page.containsKey(Constants.PARAM.PAGE.PAGE_SIZE))) {
                    if (!page.containsKey(Constants.PARAM.PAGE.PAGE_INDEX)) { // NOPMD
                        error = new ServiceParamMissingException(clazz,
                                Constants.PARAM.PAGE.NAME + "->" + Constants.PARAM.PAGE.PAGE_INDEX);
                    } else if (!page.containsKey(Constants.PARAM.PAGE.PAGE_SIZE)) {
                        error = new ServiceParamMissingException(clazz,
                                Constants.PARAM.PAGE.NAME + "->" + Constants.PARAM.PAGE.PAGE_SIZE);
                    }
                }
                page.getInteger(Constants.PARAM.PAGE.PAGE_INDEX);
                page.getInteger(Constants.PARAM.PAGE.PAGE_SIZE);
            } else {
                error = new ServiceParamMissingException(clazz, Constants.PARAM.PAGE.NAME);
            }
        } catch (ClassCastException ex) {
            jvmError(LOGGER, ex);
            error = new ServiceParamInvalidException(clazz, ex.toString());
        }
        return error;
    }

    /**
     * 
     * @param clazz
     * @param jsonObject
     * @return
     */
    public static AbstractException interruptJdbcParams(@NotNull final Class<?> clazz, // NOPMD
            @NotNull final JsonObject jsonObject) {
        AbstractException error = null;
        try {
            if (!(jsonObject.containsKey(Constants.CMD.STATUS.JDBC_URL)
                    && jsonObject.containsKey(Constants.CMD.STATUS.USERNAME)
                    && jsonObject.containsKey(Constants.CMD.STATUS.PASSWORD))) {
                if (!jsonObject.containsKey(Constants.CMD.STATUS.JDBC_URL)) { // NOPMD
                    error = new ServiceParamMissingException(clazz, Constants.CMD.STATUS.JDBC_URL);
                } else if (!jsonObject.containsKey(Constants.CMD.STATUS.USERNAME)) { // NOPMD
                    error = new ServiceParamMissingException(clazz, Constants.CMD.STATUS.USERNAME);
                } else if (!jsonObject.containsKey(Constants.CMD.STATUS.PASSWORD)) { // NOPMD
                    error = new ServiceParamMissingException(clazz, Constants.CMD.STATUS.PASSWORD);
                }
            }
            // Exception
            jsonObject.getString(Constants.CMD.STATUS.JDBC_URL);
            jsonObject.getString(Constants.CMD.STATUS.USERNAME);
            jsonObject.getString(Constants.CMD.STATUS.PASSWORD);
        } catch (ClassCastException ex) {
            error = new ServiceParamInvalidException(clazz, ex.getMessage());
        }
        return error;
    }

    /**
     * 
     * @param jsonObject
     * @return
     */
    public static AbstractException interruptRecordParams(@NotNull final Class<?> clazz, // NOPMD
            @NotNull final JsonObject jsonObject) {
        AbstractException error = null;
        try {
            if (!(jsonObject.containsKey(Constants.PARAM.ID) && jsonObject.containsKey(Constants.PARAM.DATA)
                    && jsonObject.containsKey(Constants.PARAM.SCRIPT))) {
                if (!jsonObject.containsKey(Constants.PARAM.ID)) { // NOPMD
                    error = new ServiceParamMissingException(clazz, Constants.PARAM.ID);
                } else if (!jsonObject.containsKey(Constants.PARAM.DATA)) { // NOPMD
                    error = new ServiceParamMissingException(clazz, Constants.PARAM.DATA);
                } else if (!jsonObject.containsKey(Constants.PARAM.SCRIPT)) { // NOPMD
                    error = new ServiceParamMissingException(clazz, Constants.PARAM.SCRIPT);
                }
            }
            // Global ID Replaced，针对data中包含了identifier定义的情况
            {
                final String identifier = jsonObject.getString(Constants.PARAM.ID);
                if (StringKit.isNonNil(identifier) && identifier.startsWith("RP")) {
                    final String[] rpId = identifier.split("\\$");
                    if (Constants.ONE < rpId.length) {
                        final String idAttr = rpId[1];
                        final String idVal = jsonObject.getJsonObject(Constants.PARAM.DATA).getString(idAttr);
                        jsonObject.put(Constants.PARAM.ID, idVal);
                    }
                }
            }
            // Exception
            jsonObject.getString(Constants.PARAM.ID);
            jsonObject.getJsonObject(Constants.PARAM.DATA);
            jsonObject.getString(Constants.PARAM.SCRIPT);
        } catch (ClassCastException ex) {
            error = new ServiceParamInvalidException(clazz, ex.getMessage());
        }
        return error;
    }

    /**
     * 
     * @param record
     * @return
     */
    public static boolean isUpdate(@NotNull @InstanceOf(Record.class) final Record record) {
        boolean isUpdate = true;
        try {
            final ConcurrentMap<String, Value<?>> idKV = record.idKV();
            for (final String id : idKV.keySet()) {
                final Value<?> value = idKV.get(id);
                if (null == value.getValue()) {
                    isUpdate = false;
                    break;
                }
            }
        } catch (AbstractException ex) {
            peError(LOGGER, ex);
        }
        return isUpdate;
    }

    /**
     * 
     * @param record
     * @return
     */
    public static AbstractException interruptPK(@NotNull @InstanceOf(Record.class) final Record record) {
        AbstractException error = null;
        try {
            final ConcurrentMap<String, Value<?>> idKV = record.idKV();
            for (final String id : idKV.keySet()) {
                final Value<?> value = idKV.get(id);
                if (StringKit.isNil(value.literal())) {
                    error = new PrimaryKeyMissingException(Interruptor.class, id); // NOPMD
                }
            }
        } catch (AbstractException ex) {
            peError(LOGGER, ex);
            error = ex;
        }
        return error;
    }

    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private Interruptor() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
