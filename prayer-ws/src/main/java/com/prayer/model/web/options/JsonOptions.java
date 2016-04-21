package com.prayer.model.web.options;

import com.prayer.facade.engine.opts.Options;
import com.prayer.fantasm.exception.AbstractLauncherException;

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
public class JsonOptions implements Options {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 最终的Options **/
    @NotNull
    private transient JsonObject data;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param data
     */
    @PostValidateThis
    public JsonOptions(@NotNull final JsonObject data) {
        this.data = data;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public JsonObject readOpts() {
        return this.data;
    }

    /** **/
    @Override
    public AbstractLauncherException getError(){
        return null;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
