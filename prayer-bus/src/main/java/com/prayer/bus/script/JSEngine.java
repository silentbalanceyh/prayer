package com.prayer.bus.script;

import static com.prayer.util.Instance.singleton;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import javax.script.SimpleScriptContext;

import com.prayer.bus.util.ClauseInjector;
import com.prayer.constant.Constants;
import com.prayer.constant.Symbol;
import com.prayer.kernel.Record;
import com.prayer.util.StringKit;

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
    public static JSEnv initJSEnv(@NotNull final JsonObject jsonObject,
            @NotNull @InstanceOf(Record.class) final Record record) throws ScriptException {
        final JSEngine engine = new JSEngine(jsonObject.getJsonObject(Constants.PARAM.DATA));
        final JSEnvExtractor extractor = singleton(JSEnvExtractor.class);
        final JSEnv env = new JSEnv();
        // 1.设置变量绑定
        env.setRecord(record);
        engine.put(JSEngine.ENV, env);
        // 2.关于OrderBy的判断，参数中包含了orders的信息
        env.setOrder(ClauseInjector.genOrderBy(jsonObject, record));
        // 3.关于Pager的注入
        env.setPager(ClauseInjector.genPager(jsonObject));
        // 4.设置全局脚本
        engine.execute(extractor.extractJSEnv());
        // 5.设置局部配置脚本
        engine.execute(extractor.extractJSContent(jsonObject));
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
