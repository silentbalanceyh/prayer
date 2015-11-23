package com.prayer.bus.script;

import static com.prayer.util.Instance.singleton;

import com.prayer.bus.deploy.oob.ConfigSevImpl;
import com.prayer.bus.std.ConfigService;
import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.ResponseCode;
import com.prayer.model.bus.ServiceResult;
import com.prayer.model.h2.script.ScriptModel;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

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
    private transient final ConfigService configSev;
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    JSEnvExtractor(){
        this.configSev = singleton(ConfigSevImpl.class);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     * @param parameters
     * @return
     */
    public String extractJSContent(@NotNull final JsonObject parameters) {
        final String scriptName = parameters.getString(Constants.PARAM.SCRIPT);
        return this.getJsByName(scriptName);
    }
    /**
     * 
     * @return
     */
    public String extractJSEnv(){
        return this.getJsByName(JS_GLOBAL_ID);
    }
    /**
     * 
     * @return
     */
    public String extractJSMetaEnv(){
        return this.getJsByName(JS_META_ID);
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    
    private String getJsByName(final String scriptName){
        final ServiceResult<ScriptModel> script = this.configSev.findScript(scriptName);
        String ret = "";
        if (ResponseCode.SUCCESS == script.getResponseCode() && null != script.getResult()) {
            ret = script.getResult().getContent();
        }
        return ret;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
