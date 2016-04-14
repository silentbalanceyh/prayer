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
import com.prayer.facade.business.instantor.configuration.ConfigInstantor;
import com.prayer.facade.constant.Constants;
import com.prayer.fantasm.exception.AbstractWebException;
import com.prayer.model.meta.vertx.PERule;
import com.prayer.model.meta.vertx.PEUri;
import com.prayer.model.web.JsonKey;
import com.prayer.model.web.Requestor;
import com.prayer.uca.assistant.UCAValidator;
import com.prayer.util.web.Extractor;
import com.prayer.util.web.Future;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 【Step 2】参数的第二部检查 -> Ruler/UCAConvertor
 * 
 * @author Lang
 *
 */
@Guarded
public class ValidationHandler implements Handler<RoutingContext> { // NOPMD

    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationHandler.class);
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    private transient final ConfigInstantor service;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public ValidationHandler() {
        this.service = singleton(ConfigBllor.class);
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
            final PEUri uri = Extractor.uri(context);

            // 2.获取当前路径下的Validator的数据
            ConcurrentMap<String, List<PERule>> result = this.service.validators(uri.getUniqueId());
            // 3.Dispatcher
            if (this.requestDispatch(result, context, requestor)) {
                // SUCCESS -->
                context.put(Constants.KEY.CTX_REQUESTOR, requestor);
                context.next();
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private boolean requestDispatch(final ConcurrentMap<String, List<PERule>> dataMap,
            final RoutingContext context, final Requestor requestor) {
//        // 1.内部500 Error
//        if (ResponseCode.SUCCESS != result.getResponseCode()) {
//            // 500 Internal Error
//            Future.error500(getClass(), context);
//            return false;
//        }
        // 2.特殊参数错
        final JsonObject params = requestor.getRequest().getJsonObject(JsonKey.REQUEST.PARAMS);
        AbstractWebException error = null;
        boolean ret = true;
        // 遍历每个字段
        for (final String field : params.fieldNames()) {
            // 1.从系统里读取validators
            final List<PERule> validators = dataMap.get(field);
            // 2.该字段存在validators的时候就执行
            if (!validators.isEmpty()) {
                // 3.能够读取到字段对应的Validator
                final String value = toStr(params, field);
                error = UCAValidator.verifyField(field, value, validators);
                if (null != error) {
                    ret = false;
                    Future.error400(getClass(), context, error);
                    break;
                }
            }
        }
        return ret;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
