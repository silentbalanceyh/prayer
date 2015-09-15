package com.prayer.bus.script;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import javax.script.SimpleScriptContext;

import com.prayer.constant.Constants;
import com.prayer.constant.Symbol;
import com.prayer.util.StringKit;

import io.vertx.core.json.JsonObject;
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
	 * @return
	 */
	public static JSEngine getEngine(@NotNull final JsonObject data) throws ScriptException {
		return new JSEngine(data);
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

	/**
	 * 
	 * @param context
	 * @throws ScriptException
	 */
	public void execute(final String context) throws ScriptException {
		if (null != this.engine && StringKit.isNonNil(context)) {
			this.engine.eval(context);
		}
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
