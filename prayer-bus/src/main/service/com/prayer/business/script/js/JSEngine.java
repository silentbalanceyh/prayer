package com.prayer.business.script.js;

import static com.prayer.util.reflection.Instance.singleton;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import javax.script.SimpleScriptContext;

import com.prayer.facade.constant.Constants;
import com.prayer.facade.constant.Symbol;
import com.prayer.facade.model.record.Record;
import com.prayer.model.business.InquiryMarchal;
import com.prayer.model.business.behavior.ActRequest;
import com.prayer.util.string.StringKit;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.InstanceOf;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class JSEngine {
    // ~ Static Fields =======================================
    /**
     * JSEnv对象
     **/
    public static final String ENV = "env";
    /**
     * JsonObject对象
     */
    public static final String DATA = "data";
    /**
     * OrderBy对象
     */
    public static final String ORDERS = "orders";
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    private transient final ScriptEngine engine;
    /** **/
    @NotNull
    private transient final Bindings bindings;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 
     * @param jsonObject
     * @param record
     * @throws ScriptException
     */
    @NotNull
    public static InquiryMarchal initJSEnv(@NotNull final ActRequest request,
            @NotNull @InstanceOf(Record.class) final Record record) throws ScriptException {
        final JSEngine engine = new JSEngine(request.getData());
        final JSEnvExtractor extractor = singleton(JSEnvExtractor.class);
        // 1.设置变量绑定
        final InquiryMarchal env = new InquiryMarchal(record);
        env.setRecord(record);
        engine.put(JSEngine.ENV, env);
        // 2.关于OrderBy的判断，参数中包含了orders的信息
        env.setOrder(request.getOrders());
        // 3.关于Pager的注入
        env.setPager(request.getPager());
        // 4.设置全局脚本
        engine.execute(extractor.extractJSEnv());
        // 5.设置局部配置脚本
        engine.execute(extractor.extractJSContent(request.getData()));
        return env;
    }
    // ~ Constructors ========================================

    private JSEngine(final JsonObject data) throws ScriptException {
        this.engine = new ScriptEngineManager().getEngineByName(Constants.SCRIPT_ENGINE);
        this.bindings = new SimpleBindings();
        this.bindings.put(Symbol.DOLLER + DATA,
                this.engine.eval(Symbol.BRACKET_SL + data.encode() + Symbol.BRACKET_SR));
        final ScriptContext context = new SimpleScriptContext();
        context.setBindings(bindings, ScriptContext.ENGINE_SCOPE);
        this.engine.setContext(context);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 
     * @param env
     * @param reference
     */
    public void put(@NotNull @NotBlank @NotEmpty final String env, @NotNull final Object reference) {
        this.bindings.put(Symbol.DOLLER + env, reference);
    }

    // ~ Private Methods =====================================
    /**
     * 
     * @param context
     * @throws ScriptException
     */
    private void execute(final String context) throws ScriptException {
        if (null != this.engine && StringKit.isNonNil(context)) {
            this.engine.eval(context);
        }
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
