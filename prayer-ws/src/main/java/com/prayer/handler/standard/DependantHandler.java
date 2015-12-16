package com.prayer.handler.standard;

import static com.prayer.assistant.WebLogger.info;
import static com.prayer.util.Converter.fromStr;
import static com.prayer.util.Converter.toStr;
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
import com.prayer.exception.web.DependParameterInvalidException;
import com.prayer.exception.web.DependParamsMissingException;
import com.prayer.exception.web.DependRuleInvalidException;
import com.prayer.exception.web.DependantMultiException;
import com.prayer.facade.bus.ConfigService;
import com.prayer.facade.kernel.Value;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.h2.vertx.RuleModel;
import com.prayer.model.h2.vertx.UriModel;
import com.prayer.model.web.JsonKey;
import com.prayer.model.web.Requestor;
import com.prayer.uca.WebDependant;
import com.prayer.util.cv.Constants;
import com.prayer.util.cv.SystemEnum.DependRule;
import com.prayer.util.cv.SystemEnum.ResponseCode;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
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
public class DependantHandler implements Handler<RoutingContext> {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(DependantHandler.class);
    /** **/
    private static final String[] REQ_PARAM = new String[] { "rule", "parameter", "query" };
    // ~ Instance Fields =====================================
    /** Config Service 接口 **/
    private transient final ConfigService service;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public DependantHandler() {
        this.service = singleton(ConfigSevImpl.class);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void handle(@NotNull final RoutingContext context) {
        info(LOGGER, WebLogger.I_CFG_HANDLER, getClass().getName(), context.request().path());
        // 1.从Context中提取参数信息
        final Requestor requestor = Extractor.requestor(context);
        final UriModel uri = Extractor.uri(context);
        // 2.查找Dependant的组件数据
        final ServiceResult<ConcurrentMap<String, List<RuleModel>>> result = this.service
                .findDependants(uri.getUniqueId());
        if (this.requestDispatch(result, context, requestor)) {
            // SUCCESS ->
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
                // Convertor中需要使用的更新后的参数
                final JsonObject updatedParams = new JsonObject();
                for (final String field : params.fieldNames()) {
                    final String value = toStr(params, field);
                    updatedParams.put(field, value);
                    // Extract Component
                    final List<RuleModel> dependants = dataMap.get(field);
                    // Dependants对一个字段而言不可以有多个，这个规则和Convertor转换器一样
                    if (null != dependants) {
                        if (Constants.ONE < dependants.size()) {
                            error = new DependantMultiException(getClass(), field); // NOPMD
                        } else if (Constants.ONE == dependants.size()) {
                            final RuleModel dependant = dependants.get(Constants.ZERO);
                            this.dependField(field, value, dependant, updatedParams);
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

    /**
     * 这个方法的二义性太多，所以必须在此说明 <code>1.如果是出现CONVERT情况那么updatedParams会被更新成新的值；</code>
     * <code>2.CONVERT失败的时候抛出Exception</code>
     * <code>3.检查失败的时候同样抛出Exception</code> <code>4.验证失败的时候也抛出Exception</code>
     * <code>5.通过条件就是Validate成功并且根据关联数据Convert也成功，那么就继续请求</code>
     * 
     * @param paramName
     * @param paramValue
     * @param ruleModel
     * @throws AbstractWebException
     */
    private void dependField(final String paramName, final String paramValue, final RuleModel ruleModel,
            final JsonObject updatedParams) throws AbstractWebException {
        // 1.验证Dependant是否合法
        final String componentCls = ruleModel.getComponentClass();
        Interruptor.interruptClass(getClass(), componentCls, "Dependant");
        Interruptor.interruptImplements(getClass(), componentCls, WebDependant.class);
        // 2.提取Dependant中处理的类型信息
        final String typeCls = ruleModel.getType().getClassName();
        final Value<?> value = instance(typeCls, paramValue);
        // 3.验证config的三个特殊参数以及数据类型
        this.verifyConfig(ruleModel);
    }

    /**
     * 
     * @param ruleModel
     */
    private void verifyConfig(final RuleModel ruleModel) throws AbstractWebException {
        // 3.提取配置信息，验证config的必须属性
        final JsonObject config = ruleModel.getConfig();
        if (null != config) {
            for (final String required : REQ_PARAM) {
                if (!config.containsKey(required)) {
                    throw new DependParamsMissingException(getClass(), required, ruleModel.getName());
                }
            }
            // 4.rule格式
            final String ruleStr = config.getString(REQ_PARAM[Constants.ZERO]);
            DependRule rule = null;
            if(null != ruleStr){
                rule = fromStr(DependRule.class,ruleStr);
            }
            if(null == rule){
                throw new DependRuleInvalidException(getClass(),ruleStr);
            }
            // 5.parameter格式验证
            final Object paramObj = config.getValue(REQ_PARAM[Constants.ONE]);
            if(null != paramObj && JsonArray.class != paramObj.getClass()){
                throw new DependParameterInvalidException(getClass(),paramObj.getClass());
            }
            // 6.query格式验证
            final Object queryStr = config.getValue(REQ_PARAM[Constants.TWO]);
            if(null != queryStr && String.class == queryStr.getClass()){
                
            }
        }
    }

    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
