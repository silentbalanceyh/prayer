package com.prayer.handler.standard;

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
import com.prayer.exception.web.DependantMultiException;
import com.prayer.facade.bus.ConfigService;
import com.prayer.model.JsonKey;
import com.prayer.model.Requestor;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.h2.vertx.RuleModel;
import com.prayer.model.h2.vertx.UriModel;
import com.prayer.uca.assistant.UCADependant;
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
public class DependantHandler implements Handler<RoutingContext> {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(DependantHandler.class);
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
        debug(LOGGER, DebugKey.WEB_HANDLER, getClass().getName());
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
        final JsonObject inParams = requestor.getRequest().getJsonObject(JsonKey.REQUEST.PARAMS);
        if (ResponseCode.SUCCESS != result.getResponseCode()) {
            // 500 Internal Error
            Future.error500(getClass(), context);
            return false;
        }
        AbstractWebException error = null;
        final ConcurrentMap<String, List<RuleModel>> dataMap = result.getResult();
        // 遍历每一个字段
        boolean ret = true;
        try {
            // Convertor中需要使用的更新后的参数
            final JsonObject outParams = new JsonObject();
            for (final String field : inParams.fieldNames()) {
                // 1.在outParams中填充值
                final String value = toStr(inParams, field);
                outParams.put(field, value);
                // 2.读取所有的dependant组件
                final List<RuleModel> dependants = dataMap.get(field);
                // 3.Dependants对一个字段而言不可以有多个，这个规则和Convertor转换器一样
                if (null != dependants) {
                    if (Constants.ONE < dependants.size()) {
                        throw new DependantMultiException(getClass(), field); // NOPMD
                    } else if (Constants.ONE == dependants.size()) {
                        final RuleModel dependant = dependants.get(Constants.ZERO);
                        UCADependant.dependField(field, value, dependant, inParams, outParams);
                    }
                }
            }
            // 更新参数节点
            requestor.getRequest().put(JsonKey.REQUEST.PARAMS, outParams);
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
