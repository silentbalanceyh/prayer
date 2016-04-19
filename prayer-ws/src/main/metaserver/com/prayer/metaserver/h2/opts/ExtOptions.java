package com.prayer.metaserver.h2.opts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.engine.Options;
import com.prayer.facade.resource.Inceptor;
import com.prayer.fantasm.exception.AbstractLauncherException;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class ExtOptions implements Options {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(ExtOptions.class);
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    private transient Inceptor inceptor;
    /** **/
    private transient AbstractLauncherException error;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    private ExtOptions(@NotNull final Inceptor inceptor) {
        this.inceptor = inceptor;
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================

    @Override
    public JsonObject readOpts() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AbstractLauncherException getError() {
        // TODO Auto-generated method stub
        return null;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
