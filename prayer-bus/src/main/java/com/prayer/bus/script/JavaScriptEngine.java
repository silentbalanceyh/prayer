package com.prayer.bus.script;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import javax.script.SimpleScriptContext;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.constraint.NotNull;
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
	private transient final ScriptEngine ENGINE;
	/** **/
	@NotNull
	private transient final Bindings BINDINGS;
	/** **/
	@NotNull
	private transient final ScriptContext CONTEXT;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	/**
	 * 
	 * @return
	 */
	public static JavaScriptEngine getEngine() {
		return new JavaScriptEngine();
	}
	// ~ Constructors ========================================

	private JavaScriptEngine() {
		ENGINE = new ScriptEngineManager().getEngineByName("nashorn");
		BINDINGS = new SimpleBindings();
		CONTEXT = new SimpleScriptContext();
	}
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	
	public void execute(@NotNull @NotBlank @NotEmpty final String context){
		if(null != this.ENGINE){
			try{
				this.ENGINE.eval(context, this.CONTEXT);
			}catch(ScriptException ex){
				
			}
		}
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
