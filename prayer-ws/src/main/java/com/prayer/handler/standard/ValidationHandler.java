package com.prayer.handler.standard;

import static com.prayer.util.Converter.toStr;
import static com.prayer.util.Instance.instance;
import static com.prayer.util.Instance.singleton;
import static com.prayer.util.Log.debug;
import static com.prayer.util.Log.jvmError;
import static com.prayer.util.Log.peError;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.assistant.Extractor;
import com.prayer.assistant.Future;
import com.prayer.assistant.Interruptor;
import com.prayer.base.exception.AbstractDatabaseException;
import com.prayer.base.exception.AbstractWebException;
import com.prayer.bus.impl.oob.ConfigSevImpl;
import com.prayer.exception.web.SpecialDataTypeException;
import com.prayer.exception.web.ValidationFailureException;
import com.prayer.facade.bus.ConfigService;
import com.prayer.facade.kernel.Value;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.h2.vertx.RuleModel;
import com.prayer.model.h2.vertx.UriModel;
import com.prayer.model.type.DataType;
import com.prayer.model.type.JsonType;
import com.prayer.model.type.ScriptType;
import com.prayer.model.type.XmlType;
import com.prayer.model.web.JsonKey;
import com.prayer.model.web.Requestor;
import com.prayer.uca.WebValidator;
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
        try {
            debug(LOGGER, DebugKey.WEB_HANDLER, getClass().getName());
            // 1.从Context中提取参数信息
            final Requestor requestor = Extractor.requestor(context);
            final UriModel uri = Extractor.uri(context);

            // 2.获取当前路径下的Validator的数据
            final ServiceResult<ConcurrentMap<String, List<RuleModel>>> result = this.service
                    .findValidators(uri.getUniqueId());
            // 3.Dispatcher
            if (this.requestDispatch(result, context, requestor)) {
                // SUCCESS -->
                context.put(Constants.KEY.CTX_REQUESTOR, requestor);
                context.next();
            }
        } catch (Exception ex) {
            jvmError(LOGGER, ex);
        }
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private boolean requestDispatch(final ServiceResult<ConcurrentMap<String, List<RuleModel>>> result,
            final RoutingContext context, final Requestor requestor) {
        if (ResponseCode.SUCCESS == result.getResponseCode()) {
            final JsonObject params = requestor.getRequest().getJsonObject(JsonKey.REQUEST.PARAMS);
            AbstractWebException error = null;
            final ConcurrentMap<String, List<RuleModel>> dataMap = result.getResult();
            boolean ret = true;
            // 遍历每个字段
            for (final String field : params.fieldNames()) {
                // 1.从系统里读取validators
                final List<RuleModel> validators = dataMap.get(field);
                // 2.该字段存在validators的时候就执行
                if (!validators.isEmpty()) {
                    // 3.能够读取到字段对应的Validator
                    final String value = toStr(params, field);
                    error = this.validateField(field, value, validators);
                    if (null != error) {
                        ret = false;
                        Future.error400(getClass(), context, error);
                        break;
                    }
                }
            }
            return ret;
        } else {
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
            Value<?> value = null;
            if (DataType.JSON == ruleModel.getType() || DataType.XML == ruleModel.getType()
                    || DataType.SCRIPT == ruleModel.getType()) {
                // 抛出AbstractDatabaseException
                switch (ruleModel.getType()) {
                case JSON:
                    value = new JsonType(paramValue);
                    break;
                case XML:
                    value = new XmlType(paramValue);
                    break;
                case SCRIPT:
                    value = new ScriptType(paramValue);
                    break;
                default:
                    value = null;
                    break;
                }
            } else {
                final String typeCls = ruleModel.getType().getClassName();
                value = instance(typeCls, paramValue);
            }
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
            peError(LOGGER,ex);
            error = ex;
        } catch (AbstractDatabaseException ex) {
            peError(LOGGER,ex);
            // 三种复杂基础类型的数据格式问题
            error = new SpecialDataTypeException(getClass(), ruleModel.getType(), paramValue);
        }
        return error;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
