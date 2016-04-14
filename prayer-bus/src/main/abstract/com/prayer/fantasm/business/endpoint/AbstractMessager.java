package com.prayer.fantasm.business.endpoint;

import static com.prayer.util.reflection.Instance.reservoir;

import javax.script.ScriptException;

import org.slf4j.Logger;

import com.prayer.business.service.RecordBehavior;
import com.prayer.exception.web.JSScriptEngineException;
import com.prayer.facade.business.service.RecordService;
import com.prayer.facade.fun.endpoint.Behavior;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.web.WebRequest;
import com.prayer.model.web.WebResponse;
import com.prayer.util.debug.Log;

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
    public JsonObject put(@NotNull final JsonObject request) {
        return this.execute(request, this.behavior::save);
    }

    /** **/
    public JsonObject post(@NotNull final JsonObject request) {
        return this.execute(request, this.behavior::save);
    }

    /** **/
    public JsonObject delete(@NotNull final JsonObject request) {
        return this.execute(request, this.behavior::remove);
    }

    /** **/
    public JsonObject get(@NotNull final JsonObject request) {
        return this.execute(request, this.behavior::find);
    }

    /** **/
    public JsonObject page(@NotNull final JsonObject request) {
        return this.execute(request, this.behavior::page);
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    /** 私有函数调用 **/
    private JsonObject execute(final JsonObject requestData, final Behavior behavior) {
        WebResponse response = new WebResponse();
        try {
            WebRequest request = new WebRequest(requestData);
            if (null == request.getError()) {
                /** 请求合法 **/
                response = behavior.dispatch(request);
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
        return response.getResult();
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
