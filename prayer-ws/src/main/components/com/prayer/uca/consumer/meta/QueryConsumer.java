package com.prayer.uca.consumer.meta;

import static com.prayer.util.Converter.fromStr;
import static com.prayer.util.debug.Log.debug;
import static com.prayer.util.reflection.Instance.singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.business.impl.std.MetaSevImpl;
import com.prayer.constant.log.DebugKey;
import com.prayer.exception.web.MethodNotAllowedException;
import com.prayer.facade.business.MetaService;
import com.prayer.facade.constant.Constants;
import com.prayer.model.business.behavior.ServiceResult;
import com.prayer.model.web.Responsor;
import com.prayer.model.web.StatusCode;
import com.prayer.util.web.Extractor;

import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PreValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class QueryConsumer implements Handler<Message<Object>> {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(QueryConsumer.class);
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    private transient final MetaService metaSev;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public QueryConsumer() {
        this.metaSev = singleton(MetaSevImpl.class);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    @PreValidateThis
    public void handle(@NotNull final Message<Object> event) {
        debug(LOGGER, DebugKey.WEB_UCA, "Consumer --> " + getClass().toString());
        // 1.从EventBus中接受数据
        final JsonObject params = (JsonObject) event.body();
        // 2.获取方法信息
        final HttpMethod method = fromStr(HttpMethod.class, params.getString(Constants.PARAM.METHOD));
        // Fix：移除不需要的Method参数，底层不需要该参数
        params.remove(Constants.PARAM.METHOD);
        // 3.根据不同的方法的Record
        Responsor responsor = null;
        if (HttpMethod.POST == method) {
            final ServiceResult<JsonObject> result = this.metaSev.page(params);
            responsor = Extractor.responsor(result, StatusCode.OK);
        } else {
            responsor = Responsor.failure(StatusCode.METHOD_NOT_ALLOWED,
                    new MethodNotAllowedException(getClass(), method.toString()));
        }
        event.reply(responsor.getResult());
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
