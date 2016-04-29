package com.prayer.script.js;

import static com.prayer.util.debug.Log.info;
import static com.prayer.util.debug.Log.jvmError;

import java.text.MessageFormat;
import java.util.List;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.constant.Constants;
import com.prayer.facade.script.Workshop;
import com.prayer.util.io.IOKit;
import com.prayer.util.string.StringKit;

import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class JSWorkshop implements Workshop {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(JSWorkshop.class);
    /** **/
    private static final String MSG_START = "( {0} ) Script Engine start to initilize...";
    /** **/
    private static final String MSG_INIT = "( {0} ) Initilizing JavaScript File -> {1} ";
    /** **/
    private static final String MSG_END = "( {0} ) Script Engine has been initilized successfully!";
    /** 初始化一个 **/
    private static Workshop WORKSHOP;
    // ~ Instance Fields =====================================
    /** 初始化一个脚本引擎，和JSWorkshop一致 **/
    private final ScriptEngine ENGINE = new ScriptEngineManager().getEngineByName(Constants.SCRIPT_ENGINE);
    /** 全局上下文环境 **/
    private final ScriptContext CTX;
    /** 全局绑定信息 **/
    private final Bindings BIND;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** 私有构造函数 **/
    private JSWorkshop() {
        /** 1.BIND是全局上下文 **/
        BIND = ENGINE.getBindings(ScriptContext.GLOBAL_SCOPE);
        /** 2.初始化全局上下文环境 **/
        CTX = new SimpleScriptContext();
        ENGINE.setContext(CTX);
        /** 3.将全局上下文绑定连接 **/
        CTX.setBindings(BIND, ScriptContext.GLOBAL_SCOPE);
        /** 4.读取脚本文件，并且在全局环境中执行 **/
        this.initEnv();
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public Workshop build() throws ScriptException {
        if (null == WORKSHOP) {
            WORKSHOP = new JSWorkshop();
        }
        return WORKSHOP;
    }

    /** **/
    @Override
    public ScriptEngine getEngine() {
        return ENGINE;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private void initEnv() {
        /** 1.读取所有的文件 **/
        info(LOGGER, MessageFormat.format(MSG_START, getClass().getSimpleName()));
        final List<String> files = JSReader.readScripts();
        for (final String file : files) {
            info(LOGGER, MessageFormat.format(MSG_INIT, getClass().getSimpleName(), file));
            final String content = IOKit.getContent(file);
            if (StringKit.isNonNil(content)) {
                /** 2.执行脚本内容 **/
                try {
                    ENGINE.eval(content, this.CTX);
                } catch (ScriptException ex) {
                    jvmError(LOGGER, ex);
                    ex.printStackTrace();
                }
            }
        }
        info(LOGGER, MessageFormat.format(MSG_END, getClass().getSimpleName()));
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
