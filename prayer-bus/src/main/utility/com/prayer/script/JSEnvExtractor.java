package com.prayer.script;

import static com.prayer.util.reflection.Instance.singleton;

import com.prayer.configuration.impl.ConfigBllor;
import com.prayer.facade.configuration.ConfigService;
import com.prayer.facade.constant.Constants;
import com.prayer.model.meta.vertx.PEScript;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.InstanceOf;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class JSEnvExtractor {
    // ~ Static Fields =======================================
    /** **/
    private static final String JS_GLOBAL_ID = "js.global.util";
    /** **/
    private static final String JS_META_ID = "js.global.meta";
    // ~ Instance Fields =====================================
    /** Config Service 接口 **/
    @NotNull
    @InstanceOf(ConfigService.class)
    private transient ConfigService configSev; // NOPMD
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================

    @PostValidateThis
    JSEnvExtractor() {
        this.configSev = singleton(ConfigBllor.class);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     * @param parameters
     * @return
     */
    @NotNull
    @NotBlank
    @NotEmpty
    public String extractJSContent(@NotNull final JsonObject parameters) {
        final String scriptName = parameters.getString(Constants.PARAM.SCRIPT);
        return this.getJsByName(scriptName);
    }

    /**
     * 
     * @return
     */
    @NotNull
    @NotBlank
    @NotEmpty
    public String extractJSEnv() {
        return this.getJsByName(JS_GLOBAL_ID);
    }

    /**
     * 
     * @return
     */
    @NotNull
    @NotBlank
    @NotEmpty
    public String extractJSMetaEnv() {
        return this.getJsByName(JS_META_ID);
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private String getJsByName(final String scriptName) {
        final PEScript script = this.configSev.script(scriptName);
        return null == script ? Constants.EMPTY_STR : script.getContent();
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
