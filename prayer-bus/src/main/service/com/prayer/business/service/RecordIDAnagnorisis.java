package com.prayer.business.service;

import static com.prayer.util.debug.Log.jvmError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.web._400ServiceParamInvalidException;
import com.prayer.facade.business.service.IdAnagnorisis;
import com.prayer.facade.constant.Constants;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.business.behavior.ActRequest;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * Record的ID处理器
 * 
 * @author Lang
 *
 */
@Guarded
public class RecordIDAnagnorisis implements IdAnagnorisis {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(ActRequest.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    @Override
    public void identifier(@NotNull final JsonObject params) throws AbstractException {
        try {
            final String identifier = params.getString(Constants.PARAM.ID);
            final String retValue = this.parse(params, identifier);
            params.put(Constants.PARAM.ID, retValue);
        } catch (ClassCastException ex) {
            jvmError(LOGGER, ex);
            throw new _400ServiceParamInvalidException(getClass(), ex.getMessage());
        }
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    /** **/
    // identifier格式：${data.identifier}
    private String parse(final JsonObject params, final String identifier) {
        /** 1.将返回值设置成identifier **/
        String retValue = identifier;
        if (identifier.trim().startsWith("${")) {
            /** 2.表达式解析 **/
            final int start = identifier.indexOf('{');
            final int end = identifier.indexOf('}');
            final String[] paths = identifier.substring(start + 1, end).split("\\.");
            /** 3.从根节点开始搜索 **/
            JsonObject lookup = params.copy();
            for (int idx = 0; idx < paths.length - 1; idx++) {
                lookup = lookup.getJsonObject(paths[idx]);
            }
            /** 4.找到最后一个节点 **/
            retValue = lookup.getString(paths[paths.length - 1]);
            if (null == retValue) {
                retValue = identifier;
            }
        }
        return retValue;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
