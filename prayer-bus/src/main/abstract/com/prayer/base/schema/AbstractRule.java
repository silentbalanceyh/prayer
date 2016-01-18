package com.prayer.base.schema;

import static com.prayer.util.debug.Log.debug;
import static com.prayer.util.debug.Log.jvmError;

import org.slf4j.Logger;

import com.prayer.constant.Constants;
import com.prayer.constant.Resources;
import com.prayer.constant.Symbol;
import com.prayer.facade.schema.rule.RuleConstants;
import com.prayer.util.io.IOKit;

import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.InstanceOfAny;
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
                JsonObject rules = new JsonObject(content);
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
     * 读取节点中的R_VALUE的值
     * 
     * @return
     */
    @InstanceOfAny({ JsonObject.class, JsonArray.class })
    @NotNull
    @SuppressWarnings("unchecked")
    public <T> T getRule() {
        T rule = null;
        if (null != this.config && this.config.containsKey(RuleConstants.R_VALUE)) {
            final Object value = this.config.getValue(RuleConstants.R_VALUE);
            if (null != value) {
                if (JsonObject.class == value.getClass()) {
                    rule = (T) this.config.getJsonObject(RuleConstants.R_VALUE);
                } else if (JsonArray.class == value.getClass()) {
                    rule = (T) this.config.getJsonArray(RuleConstants.R_VALUE);
                }
            }
        }
        return rule;
    }

    /**
     * 读取节点中的Error或Errors信息
     * 
     * @return
     */
    @InstanceOfAny({ JsonObject.class })
    public JsonObject getError(final String field) {
        JsonObject errors = new JsonObject();
        /**
         * 1.先从系统中读取Error信息
         */
        errors = this.config.getJsonObject(RuleConstants.R_ERROR);
        /**
         * 2.如果没有读取到Error配置，则尝试从Errros中读取
         */
        if (null == errors) {
            errors = this.config.getJsonObject(RuleConstants.R_ERRORS);
            if (null != errors) {
                errors = errors.getJsonObject(field);
            }
        }
        return errors;
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
