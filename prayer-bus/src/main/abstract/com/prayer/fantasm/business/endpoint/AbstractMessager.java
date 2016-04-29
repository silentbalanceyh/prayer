package com.prayer.fantasm.business.endpoint;

import static com.prayer.util.reflection.Instance.reservoir;

import javax.script.ScriptException;

import org.slf4j.Logger;

import com.prayer.business.service.RecordBehavior;
import com.prayer.exception.web.JSScriptEngineException;
import com.prayer.facade.business.service.RecordService;
import com.prayer.facade.fun.endpoint.Behavior;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.business.behavior.ActRequest;
import com.prayer.model.business.behavior.ActResponse;
import com.prayer.util.debug.Log;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 服务接口类信息，主要用于服务定位
 * 
 * @author Lang
 *
 */
@Guarded
public abstract class AbstractMessager {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /**
     * 
     */
    @NotNull
    private transient RecordService behavior;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 针对不同实体的消息内容
     * 
     * @param entityCls
     */
    public AbstractMessager(@NotNull final Class<?> entityCls) {
        this.behavior = reservoir(entityCls.getName(), RecordBehavior.class, entityCls);
    }

    // ~ Abstract Methods ====================================
    /** **/
    public abstract Logger getLogger();

    // ~ Override Methods ====================================
    /** 函数映射方式 **/
    public ActResponse put(@NotNull final JsonObject request) {
        return this.execute(request, new HttpMethod[] { HttpMethod.PUT }, this.behavior::save);
    }

    /** **/
    public ActResponse post(@NotNull final JsonObject request) {
        return this.execute(request, new HttpMethod[] { HttpMethod.POST }, this.behavior::save);
    }

    /** **/
    public ActResponse delete(@NotNull final JsonObject request) {
        return this.execute(request, new HttpMethod[] { HttpMethod.DELETE }, this.behavior::remove);
    }

    /** **/
    public ActResponse get(@NotNull final JsonObject request) {
        return this.execute(request, new HttpMethod[] { HttpMethod.GET }, this.behavior::find);
    }

    /** **/
    public ActResponse page(@NotNull final JsonObject request) {
        return this.execute(request, new HttpMethod[] { HttpMethod.POST }, this.behavior::page);
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    /** 私有函数调用 **/
    private ActResponse execute(final JsonObject requestData, final HttpMethod[] methods, final Behavior behavior) {
        ActResponse response = new ActResponse();
        try {
            ActRequest request = new ActRequest(requestData);
            if (request.success()) {
                /** 验证方法 **/
                // this.verifyMethod(request, methods, behavior);
                /** 请求合法 **/
                final JsonObject data = behavior.dispatch(request);
                response.success(data);
            } else {
                throw request.getError();
            }
        } catch (AbstractException ex) {
            Log.peError(getLogger(), ex);
            response.failure(ex);
        } catch (ScriptException ex) {
            Log.jvmError(getLogger(), ex);
            response.failure(new JSScriptEngineException(getClass(), ex.toString()));
        }
        return response;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
