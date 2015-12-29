package com.prayer.handler.standard;    // NOPMD

import static com.prayer.util.Converter.toStr;
import static com.prayer.util.Instance.singleton;
import static com.prayer.util.Log.debug;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.assistant.Extractor;
import com.prayer.assistant.Future;
import com.prayer.base.exception.AbstractWebException;
import com.prayer.bus.impl.oob.ConfigSevImpl;
import com.prayer.exception.web.ConvertorMultiException;
import com.prayer.facade.bus.ConfigService;
import com.prayer.model.JsonKey;
import com.prayer.model.Requestor;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.h2.vertx.RuleModel;
import com.prayer.model.h2.vertx.UriModel;
import com.prayer.uca.assistant.UCAConvertor;
import com.prayer.util.cv.Constants;
import com.prayer.util.cv.SystemEnum.ResponseCode;
import com.prayer.util.cv.log.DebugKey;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class ConversionHandler implements Handler<RoutingContext> {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(ConversionHandler.class);
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    private transient final ConfigService service;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public ConversionHandler() {
        this.service = singleton(ConfigSevImpl.class);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================

    /** **/
    @Override
    public void handle(@NotNull final RoutingContext context) {
        debug(LOGGER, DebugKey.WEB_HANDLER, getClass().getName());

        // 1.从Context中提取参数信息
        final Requestor requestor = Extractor.requestor(context);
        final UriModel uri = Extractor.uri(context);
        // 2.查找Convertors的数据
        final ServiceResult<ConcurrentMap<String, List<RuleModel>>> result = this.service
                .findConvertors(uri.getUniqueId());
        if (this.requestDispatch(result, context, requestor)) {
            // SUCCESS -->
            context.put(Constants.KEY.CTX_REQUESTOR, requestor);
            context.next();
        }
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private boolean requestDispatch(final ServiceResult<ConcurrentMap<String, List<RuleModel>>> result,
            final RoutingContext context, final Requestor requestor) {
        final JsonObject params = requestor.getRequest().getJsonObject(JsonKey.REQUEST.PARAMS);
        if(ResponseCode.SUCCESS != result.getResponseCode()){
            // 500 Internal Error
            Future.error500(getClass(), context);
            return false;
        }
        
        AbstractWebException error = null;
        final ConcurrentMap<String, List<RuleModel>> dataMap = result.getResult();
        boolean ret = true;
        // 遍历每一个字段
        try {
            final JsonObject updatedParams = new JsonObject();
            for (final String field : params.fieldNames()) {
                // 1.在外围填充updatedParams的值，这里有替换过程
                final String value = toStr(params, field); // params.getString(field);
                updatedParams.put(field, value);
                // 2.读取这个字段拥有的Convertor的信息
                final List<RuleModel> convertors = dataMap.get(field);
                if (null != convertors) {
                    // 3.读取这个字段上所有的Convertors
                    if (Constants.ONE < convertors.size()) {
                        error = new ConvertorMultiException(getClass(), field); // NOPMD
                        break;
                    } else if (Constants.ONE == convertors.size()) {
                        // 直接通过Convertor处理
                        final RuleModel convertor = convertors.get(Constants.ZERO);
                        final String cvRet = UCAConvertor.convertField(field, value, convertor);
                        updatedParams.put(field, cvRet);
                    }
                }
            }
            // 更新参数节点
            requestor.getRequest().put(JsonKey.REQUEST.PARAMS, updatedParams);
        } catch (AbstractWebException ex) {
            error = ex;
        }
        if (null != error) {
            Future.error400(getClass(), context, error);
            ret = false;
        }
        return ret;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
