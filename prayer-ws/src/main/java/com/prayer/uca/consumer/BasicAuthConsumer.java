package com.prayer.uca.consumer;

import static com.prayer.util.Converter.fromStr;
import static com.prayer.util.Instance.singleton;
import static com.prayer.assistant.WebLogger.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.assistant.Extractor;
import com.prayer.assistant.WebLogger;
import com.prayer.bus.security.BasicAuthService;
import com.prayer.bus.security.impl.BasicAuthSevImpl;
import com.prayer.constant.Constants;
import com.prayer.exception.web.MethodNotAllowedException;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.web.Responsor;
import com.prayer.model.web.StatusCode;

import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonArray;
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
public final class BasicAuthConsumer implements Handler<Message<Object>> {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(BasicAuthConsumer.class);
    // ~ Instance Fields =====================================

    /** **/
    @NotNull
    private transient final BasicAuthService authSev;

    // ~ Static Block ========================================
    /** **/
    public static BasicAuthConsumer create() {
        return new BasicAuthConsumer();
    }

    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    @PreValidateThis
    public void handle(@NotNull final Message<Object> event) {
        info(LOGGER, WebLogger.I_COMMON_INFO, "Consumer --> " + getClass().toString());
        // 1.从EventBus中接受数据
        final JsonObject params = (JsonObject) event.body();
        // 2.获取方法信息
        final HttpMethod method = fromStr(HttpMethod.class, params.getString(Constants.PARAM.METHOD));
        // 3.根据方法访问不同的Record方法
        Responsor responsor = null;
        if (HttpMethod.GET == method) {
            final ServiceResult<JsonArray> result = this.authSev.find(params);
            responsor = Extractor.responsor(result, StatusCode.OK);
        } else {
            responsor = Responsor.failure(StatusCode.METHOD_NOT_ALLOWED,
                    new MethodNotAllowedException(getClass(), method.toString()));
        }
        event.reply(responsor.getResult());
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private BasicAuthConsumer() {
        this.authSev = singleton(BasicAuthSevImpl.class);
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
