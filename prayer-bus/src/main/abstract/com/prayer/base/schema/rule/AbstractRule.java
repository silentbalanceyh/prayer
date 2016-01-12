package com.prayer.base.schema.rule;

import static com.prayer.util.debug.Log.jvmError;

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
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    private transient JsonObject config;
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
        final String content = IOKit.getContent(Resources.SYS_RULES + rule + Symbol.DOT + Constants.EXTENSION.JSON);
        JsonObject config = null;
        if (null != content) {
            try {
                final JsonObject rules = new JsonObject(content);
                config = rules.getJsonObject(name);
                // 必须包含Value节点
                if (!config.containsKey(RuleConstants.R_VALUE)) {
                    config = null;
                }
            } catch (DecodeException ex) {
                jvmError(getLogger(), ex);
            }
        }
        return config;
    }

    // ~ Methods =============================================
    /**
     * 
     * @return
     */
    public JsonObject getRule(){
        return this.config;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
