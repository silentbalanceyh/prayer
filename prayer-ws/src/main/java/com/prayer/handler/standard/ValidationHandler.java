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
import com.prayer.model.web.JsonKey;
import com.prayer.model.web.Requestor;
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
    public void handle(@NotNull final RoutingContext context) {
        info(LOGGER, WebLogger.I_STD_HANDLER, getClass().getName(), String.valueOf(Constants.ORDER.VALIDATION),context.request().path());
        
        // 1.从Context中提取参数信息
        final Requestor requestor = Extractor.requestor(context);
        final UriModel uri = Extractor.uri(context);

        // 2.获取当前路径下的Validator的数据
        final ServiceResult<ConcurrentMap<String, List<RuleModel>>> result = this.service
                .findValidators(uri.getUniqueId());
        // 3.Dispatcher
        if(this.requestDispatch(result, context, requestor)){
            // SUCCESS -->
            context.put(Constants.KEY.CTX_REQUESTOR, requestor);
            context.next();
        }
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private boolean requestDispatch(final ServiceResult<ConcurrentMap<String, List<RuleModel>>> result, final RoutingContext context, final Requestor requestor){
        if(ResponseCode.SUCCESS == result.getResponseCode()){
            final JsonObject params = requestor.getRequest().getJsonObject(JsonKey.REQUEST.PARAMS);
            AbstractWebException error = null;
            final ConcurrentMap<String, List<RuleModel>> dataMap = result.getResult();
            boolean ret = true;
            // 遍历每个字段
            for (final String field : params.fieldNames()) {
                final String value = params.getString(field);
                final List<RuleModel> validators = dataMap.get(field);
                // 验证当前字段信息
                error = this.validateField(field, value, validators);
                if(null != error){
                    ret = false;
                    Future.error400(getClass(), context, error);
                    break;
                }
            }
            return ret;
        }else{
            // 500 Internal Error
            Future.error500(getClass(), context);
            return false;
        }
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
        // TODO:
        catch (Exception ex){
            ex.printStackTrace();
        }
        return error;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
