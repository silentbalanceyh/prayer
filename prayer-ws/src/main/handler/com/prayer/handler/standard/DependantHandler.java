package com.prayer.handler.standard;

import static com.prayer.util.Converter.toStr;
import static com.prayer.util.debug.Log.debug;
import static com.prayer.util.reflection.Instance.singleton;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.business.configuration.impl.ConfigBllor;
import com.prayer.constant.log.DebugKey;
import com.prayer.exception.web.DependantMultiException;
import com.prayer.facade.business.instantor.configuration.ConfigInstantor;
import com.prayer.facade.constant.Constants;
import com.prayer.fantasm.exception.AbstractWebException;
import com.prayer.model.meta.vertx.PERule;
import com.prayer.model.meta.vertx.PEUri;
import com.prayer.model.web.JsonKey;
import com.prayer.model.web.Requestor;
import com.prayer.uca.assistant.UCADependant;
import com.prayer.util.web.Extractor;
import com.prayer.util.web.Future;

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
    private transient final ConfigInstantor service;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public DependantHandler() {
        this.service = singleton(ConfigBllor.class);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void handle(@NotNull final RoutingContext context) {
        debug(LOGGER, DebugKey.WEB_HANDLER, getClass().getName());
        // 1.从Context中提取参数信息
        final Requestor requestor = Extractor.requestor(context);
        final PEUri uri = Extractor.uri(context);
        // 2.查找Dependant的组件数据
        final ConcurrentMap<String, List<PERule>> result = this.service.dependants(uri.getUniqueId());
        if (this.requestDispatch(result, context, requestor)) {
            // SUCCESS ->
            context.put(Constants.KEY.CTX_REQUESTOR, requestor);
            context.next();
        }
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private boolean requestDispatch(final ConcurrentMap<String, List<PERule>> dataMap, final RoutingContext context,
            final Requestor requestor) {
        final JsonObject inParams = requestor.getRequest().getJsonObject(JsonKey.REQUEST.PARAMS);
        // 遍历每一个字段
        boolean ret = true;
        AbstractWebException error = null;
        try {
            // Convertor中需要使用的更新后的参数
            final JsonObject outParams = new JsonObject();
            for (final String field : inParams.fieldNames()) {
                // 1.在outParams中填充值
                final String value = toStr(inParams, field);
                outParams.put(field, value);
                // 2.读取所有的dependant组件
                final List<PERule> dependants = dataMap.get(field);
                // 3.Dependants对一个字段而言不可以有多个，这个规则和Convertor转换器一样
                if (null != dependants) {
                    if (Constants.ONE < dependants.size()) {
                        throw new DependantMultiException(getClass(), field); // NOPMD
                    } else if (Constants.ONE == dependants.size()) {
                        final PERule dependant = dependants.get(Constants.ZERO);
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
