package com.prayer.bus.script;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import javax.script.SimpleScriptContext;

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
public final class JavaScriptEngine {
	// ~ Static Fields =======================================
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
	public static JavaScriptEngine getEngine(@NotNull final JsonObject data) {
		return new JavaScriptEngine(data);
	}
	// ~ Constructors ========================================

	private JavaScriptEngine(final JsonObject data) {
		engine = new ScriptEngineManager().getEngineByName("nashorn");
		bindings = new SimpleBindings();
		data.forEach(item -> {
			bindings.put("$" + item.getKey(), item.getValue());
		});
		final ScriptContext context = new SimpleScriptContext();
		context.setBindings(bindings, ScriptContext.ENGINE_SCOPE);
		engine.setContext(context);
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	/**
	 * 
	 * @param record
	 * @param reference
	 */
	public void put(@NotNull @NotBlank @NotEmpty final String record, @NotNull final Object reference) {
		this.bindings.put("$" + record, reference);
	}

	/**
	 * 
	 * @param context
	 * @throws ScriptException
	 */
	public void execute(@NotNull @NotBlank @NotEmpty final String context) throws ScriptException {
		if (null != this.engine) {
			this.engine.eval(context);
		}
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
