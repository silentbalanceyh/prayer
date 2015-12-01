package com.prayer.handler.standard;

import static com.prayer.assistant.WebLogger.info;
import static com.prayer.util.Instance.instance;
import static com.prayer.util.Instance.singleton;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.assistant.Extractor;
import com.prayer.assistant.Future;
import com.prayer.assistant.Interruptor;
import com.prayer.assistant.WebLogger;
import com.prayer.base.exception.AbstractWebException;
import com.prayer.bus.impl.oob.ConfigSevImpl;
import com.prayer.exception.web.ConvertorMultiException;
import com.prayer.facade.bus.ConfigService;
import com.prayer.facade.kernel.Value;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.h2.vertx.RuleModel;
import com.prayer.model.h2.vertx.UriModel;
import com.prayer.model.web.JsonKey;
import com.prayer.model.web.Requestor;
import com.prayer.uca.WebConvertor;
import com.prayer.util.cv.Constants;
import com.prayer.util.cv.SystemEnum.ResponseCode;

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
    /** 创建方法 **/
    public static ConversionHandler create() {
        return new ConversionHandler();
    }

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
        info(LOGGER, WebLogger.I_STD_HANDLER, getClass().getName(), String.valueOf(Constants.ORDER.CONVERTOR),context.request().path());
        
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
        if (ResponseCode.SUCCESS == result.getResponseCode()) {
            AbstractWebException error = null;
            boolean ret = true;
            final ConcurrentMap<String, List<RuleModel>> dataMap = result.getResult();
            // 遍历每一个字段
            try {
                final JsonObject updatedParams = new JsonObject();
                for (final String field : params.fieldNames()) {
                    final String value = params.getString(field);
                    updatedParams.put(field, value);
                    final List<RuleModel> convertors = dataMap.get(field);
                    // Convertor不可以有多个
                    if (null != convertors) {
                        if (Constants.ONE < convertors.size()) {
                            error = new ConvertorMultiException(getClass(), field); // NOPMD
                            break;
                        } else if (Constants.ONE == convertors.size()) {
                            final RuleModel convertor = convertors.get(Constants.ZERO);
                            final String cvRet = this.convertField(field, value, convertor);
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
        } else {
            // 500 Internal Error
            Future.error500(getClass(), context);
            return false;
        }
    }

    private String convertField(final String paramName, final String paramValue, final RuleModel ruleModel)
            throws AbstractWebException {
        // 1.验证Convertor是否合法
        final String convertorCls = ruleModel.getComponentClass();
        Interruptor.interruptClass(getClass(), convertorCls, "Convertor");
        Interruptor.interruptImplements(getClass(), convertorCls, WebConvertor.class);
        // 2.提取Convertor中的
        final String typeCls = ruleModel.getType().getClassName();
        final Value<?> value = instance(typeCls, paramValue);
        // 3.提取配置信息
        final JsonObject config = ruleModel.getConfig();
        // 4.执行转换
        final WebConvertor convertor = instance(convertorCls);
        final Value<?> ret = convertor.convert(paramName, value, config);
        // 5.最终返回literal，转换失败的时候使用原值
        return null == ret ? paramValue : ret.literal();
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
