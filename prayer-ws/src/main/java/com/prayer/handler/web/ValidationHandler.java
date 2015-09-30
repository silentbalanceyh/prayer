package com.prayer.handler.web;

import static com.prayer.assistant.WebLogger.info;
import static com.prayer.util.Instance.instance;
import static com.prayer.util.Instance.singleton;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.assistant.HttpErrHandler;
import com.prayer.assistant.Interruptor;
import com.prayer.assistant.WebLogger;
import com.prayer.bus.deploy.oob.ConfigSevImpl;
import com.prayer.bus.std.ConfigService;
import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.exception.AbstractWebException;
import com.prayer.exception.web.ValidationFailureException;
import com.prayer.kernel.Value;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.h2.vx.RuleModel;
import com.prayer.model.h2.vx.UriModel;
import com.prayer.model.web.RestfulResult;
import com.prayer.uca.WebValidator;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 【Step 2】参数的第二部检查 -> Validator/Convertor
 * 
 * @author Lang
 *
 */
@Guarded
public class ValidationHandler implements Handler<RoutingContext> {

    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationHandler.class);
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    private transient final ConfigService service;

    // ~ Static Block ========================================
    /** 创建方法 **/
    public static ValidationHandler create() {
        return new ValidationHandler();
    }

    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public ValidationHandler() {
        this.service = singleton(ConfigSevImpl.class);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void handle(@NotNull final RoutingContext routingContext) {
        info(LOGGER, WebLogger.I_STD_HANDLER, getClass().getName(), Constants.ORDER.VALIDATION);
        // 1.从Context中提取参数信息
        final UriModel uri = routingContext.get(Constants.KEY.CTX_URI);
        // info(LOGGER, "1.Get URI ID from Context: uri = " + uriId);

        // 2.获取当前路径下的Validator的数据
        final ServiceResult<ConcurrentMap<String, List<RuleModel>>> result = this.service
                .findValidators(uri.getUniqueId());
        final RestfulResult webRet = RestfulResult.create();
        // info(LOGGER, "2.Found ServiceResult: result = " + result);
        // 3.如果获取到值
        final AbstractWebException error = this.requestDispatch(result, webRet, routingContext);
        // info(LOGGER, "3.Dispatched Error: error = " + error);
        if (null == error) {
            // SUCCESS -->
            routingContext.next();
        } else {
            // 触发错误信息
            routingContext.put(Constants.KEY.CTX_ERROR, webRet);
            routingContext.fail(webRet.getStatusCode().status());
        }
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private AbstractWebException requestDispatch(final ServiceResult<ConcurrentMap<String, List<RuleModel>>> result,
            final RestfulResult webRef, final RoutingContext context) {
        AbstractWebException error = null;
        final JsonObject params = context.get(Constants.KEY.CTX_PARAMS);
        if (ResponseCode.SUCCESS == result.getResponseCode()) {
            final ConcurrentMap<String, List<RuleModel>> dataMap = result.getResult();
            // 遍历每一个字段
            for (final String field : params.fieldNames()) {
                final String value = params.getString(field);
                final List<RuleModel> validators = dataMap.get(field);
                // 验证当前字段信息
                error = this.validateField(field, value, validators);
                // 400 Bad Request
                if (null != error) {
                    final RestfulResult webRet = HttpErrHandler.error400(error);
                    webRef.copyFrom(webRet);
                    break;
                }
            }
        } else {
            // 500 Internal Server
            error = HttpErrHandler.error500(webRef, getClass());
        }
        return error;
    }

    private AbstractWebException validateField(final String name, final String value,
            final List<RuleModel> validators) {
        AbstractWebException error = null;
        // Fix: Null Pointer，因为validators是从Map中取得的，所以必须判断是否为null
        if (null != validators && !validators.isEmpty()) {
            for (final RuleModel validator : validators) {
                error = this.validateField(name, value, validator);
                if (null != error) {
                    break;
                }
            }
        }
        return error;
    }

    private AbstractWebException validateField(final String paramName, final String paramValue,
            final RuleModel ruleModel) {
        AbstractWebException error = null;
        try {
            // 1.验证Validator是否存在
            final String validatorCls = ruleModel.getComponentClass();
            Interruptor.interruptClass(getClass(), validatorCls, "Validator");
            Interruptor.interruptImplements(getClass(), validatorCls, WebValidator.class);
            // 2.从value中提取值信息
            final String typeCls = ruleModel.getType().getClassName();
            final Value<?> value = instance(typeCls, paramValue);
            // 3.提取配置信息
            final JsonObject config = ruleModel.getConfig();
            // 4.验证结果
            final WebValidator validator = instance(validatorCls);
            final boolean ret = validator.validate(paramName, value, config);
            // 5.验证失败，特殊的Exception
            if (!ret) {
                error = new ValidationFailureException(ruleModel.getErrorMessage());
            }
        } catch (AbstractWebException ex) {
            error = ex;
        }
        return error;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
