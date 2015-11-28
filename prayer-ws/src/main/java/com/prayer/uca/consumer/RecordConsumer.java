package com.prayer.uca.consumer;

import static com.prayer.assistant.WebLogger.info;
import static com.prayer.util.Converter.fromStr;
import static com.prayer.util.Instance.singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.assistant.Extractor;
import com.prayer.assistant.WebLogger;
import com.prayer.bus.impl.std.RecordSevImpl;
import com.prayer.facade.bus.RecordService;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.web.Responsor;
import com.prayer.model.web.StatusCode;
import com.prayer.util.cv.Constants;

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
public final class RecordConsumer implements Handler<Message<Object>> {
    // ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(RecordConsumer.class);
    // ~ Instance Fields =====================================

    /** **/
    @NotNull
    private transient final RecordService recordSev;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public RecordConsumer() {
        this.recordSev = singleton(RecordSevImpl.class);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    @PreValidateThis
    public void handle(@NotNull final Message<Object> event) {
    	info(LOGGER,WebLogger.I_COMMON_INFO,"Consumer --> " + getClass().toString());
        // 1.从EventBus中接受数据
        final JsonObject params = (JsonObject) event.body();
        // 2.获取方法信息
        final HttpMethod method = fromStr(HttpMethod.class, params.getString(Constants.PARAM.METHOD));
        // 3.根据方法访问不同的Record方法
        Responsor responsor = null;
        switch (method) {
        case POST: {
            final ServiceResult<JsonObject> result = this.recordSev.save(params);
            responsor = Extractor.responsor(result, StatusCode.OK);
        }
            break;
        case PUT: {
            final ServiceResult<JsonObject> result = this.recordSev.modify(params);
            responsor = Extractor.responsor(result, StatusCode.OK);
        }
            break;
        case DELETE: {
            final ServiceResult<JsonObject> result = this.recordSev.remove(params);
            responsor = Extractor.responsor(result, StatusCode.OK);
        }
            break;
        default: {
            final ServiceResult<JsonArray> result = this.recordSev.find(params);
            responsor = Extractor.responsor(result, StatusCode.OK);
        }
            break;
        }
        event.reply(responsor.getResult());
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
