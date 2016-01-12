package com.prayer.schema.json.rule;

import static com.prayer.util.debug.Log.jvmError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;
import com.prayer.constant.Resources;
import com.prayer.constant.Symbol;
import com.prayer.facade.schema.rule.Rule;
import com.prayer.util.io.IOKit;

import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class RequiredRule implements Rule {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(RequiredRule.class);

    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    private transient JsonObject config;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** 私有构造函数 **/
    @PostValidateThis
    private RequiredRule(final String rule) {
        final String content = IOKit.getContent(Resources.SYS_RULES + rule + Symbol.DOT + Constants.EXTENSION.JSON);
        if (null != content) {
            try {
                final JsonObject rules = new JsonObject(content);
                config = rules.getJsonObject(Names.REQ_RULE);
                // 必须包含Value节点
                if (!config.containsKey(R_VALUE)) {
                    config = null;
                }
            } catch (DecodeException ex) {
                jvmError(LOGGER, ex);
            }
        }
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public JsonObject getRule() {
        return config;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
