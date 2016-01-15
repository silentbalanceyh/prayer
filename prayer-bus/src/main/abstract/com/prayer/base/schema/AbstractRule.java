package com.prayer.base.schema;

import static com.prayer.util.debug.Log.debug;
import static com.prayer.util.debug.Log.jvmError;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;

import com.prayer.constant.Constants;
import com.prayer.constant.Resources;
import com.prayer.constant.Symbol;
import com.prayer.facade.schema.rule.RuleConstants;
import com.prayer.util.io.IOKit;

import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 防止重写读取部分内容，可直接读写
 * 
 * @author Lang
 *
 */
@Guarded
public abstract class AbstractRule {
    // ~ Static Fields =======================================
    /** **/
    private static final ConcurrentMap<String, JsonObject> RULE_MAP = new ConcurrentHashMap<>();
    // ~ Instance Fields =====================================
    /** **/
    private transient JsonObject config;

    /** **/
    private transient String position;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param rule
     * @param name
     */
    public AbstractRule(@NotNull @NotEmpty @NotBlank final String rule, final String name) {
        this.config = this.readRule(rule, name);
    }

    // ~ Abstract Methods ====================================
    /** **/
    public abstract Logger getLogger();

    // ~ Override Methods ====================================
    /**
     * 子类用于读取Rule的专用类
     * 
     * @param rule
     * @param name
     * @return
     */
    protected JsonObject readRule(final String rule, final String name) {
        final String file = Resources.SYS_RULES + rule + Symbol.DOT + Constants.EXTENSION.JSON;
        final String content = IOKit.getContent(file);
        JsonObject config = null;
        if (null != content) {
            try {
                JsonObject rules = RULE_MAP.get(file);
                if (null == rules) {
                    rules = new JsonObject(content);
                    debug(getLogger(), "[RULE] " + file);
                    RULE_MAP.put(file, rules);
                }
                if (rules.containsKey("debug")) {
                    this.position = rules.getString("debug");
                }
                config = rules.getJsonObject(name);
                // 必须包含Value节点
                if (null != config && !config.containsKey(RuleConstants.R_VALUE)) {
                    config = null;
                }
            } catch (DecodeException ex) {
                jvmError(getLogger(), ex);
            }
        }
        if (null == config) {
            debug(getLogger(), " Rule: " + name + " does not exist in content: " + content);
        }
        return config;
    }

    // ~ Methods =============================================
    /**
     * 
     * @return
     */
    public JsonObject getRule() {
        return this.config;
    }

    /**
     * 
     * @return
     */
    public String position() {
        return this.position;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
