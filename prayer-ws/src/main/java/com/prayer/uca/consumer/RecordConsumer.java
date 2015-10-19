package com.prayer.uca.consumer;

import static com.prayer.util.Converter.fromStr;
import static com.prayer.util.Instance.singleton;

import com.prayer.assistant.Extractor;
import com.prayer.bus.std.RecordService;
import com.prayer.bus.std.impl.RecordSevImpl;
import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.model.bus.ServiceResult;

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
        // 1.从EventBus中接受数据
        final JsonObject params = (JsonObject) event.body();
        // 2.获取方法信息
        final HttpMethod method = fromStr(HttpMethod.class, params.getString(Constants.PARAM.METHOD));
        // 3.根据方法访问不同的Record方法
        String content = null;
        switch (method) {
        case POST: {
            content = this.save(params);
        }
            break;
        case PUT: {
            content = this.modify(params);
        }
            break;
        case DELETE: {
            final ServiceResult<JsonObject> result = this.recordSev.remove(params);
            content = result.getResult().encode();
        }
            break;
        default: {
            content = this.find(params);
        }
            break;
        }
        event.reply(content);
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    
    private String find(final JsonObject params){
        final ServiceResult<JsonArray> result = this.recordSev.find(params);
        return Extractor.getContent(result);
    }
    
    private String save(final JsonObject params){
        final ServiceResult<JsonObject> result = this.recordSev.save(params);
        String content = Constants.EMPTY_JOBJ;
        if (ResponseCode.SUCCESS == result.getResponseCode()) {
            final JsonObject ret = result.getResult();
            if (null != ret) {
                content = ret.encode();
            }
        }
        return content;
    }

    private String modify(final JsonObject params) {
        final ServiceResult<JsonObject> result = this.recordSev.modify(params);
        String content = Constants.EMPTY_JOBJ;
        if (ResponseCode.SUCCESS == result.getResponseCode()) {
            final JsonObject ret = result.getResult();
            if (null != ret) {
                content = ret.encode();
            }
        }
        return content;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
