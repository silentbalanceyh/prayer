package com.prayer.metaserver.h2.opts;

import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.engine.Options;
import com.prayer.facade.engine.Warranter;
import com.prayer.facade.resource.Inceptor;
import com.prayer.fantasm.exception.AbstractLauncherException;
import com.prayer.metaserver.warranter.ValueWarranter;

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
    private transient final Inceptor inceptor;
    /** **/
    @NotNull
    private transient final String INSTANCE;
    /** **/
    private transient AbstractLauncherException error;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    private ExtOptions(@NotNull final Inceptor inceptor) {
        this.inceptor = inceptor;
        this.INSTANCE = this.inceptor.getString("meta.server.instance");
        /** 构造：1.必须参数 **/
        this.warrantRequired();
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public JsonObject readOpts() {
        final JsonObject data = new JsonObject();
        data.put("encryption", this.inceptor.getBoolean(this.INSTANCE + ".encryption.enabled"));
        data.put("web.port", this.inceptor.getInt(this.INSTANCE + ".web.port"));
        data.put("web.allow.others", this.inceptor.getBoolean(this.INSTANCE + ".web.allow.others"));
        data.put("tcp.allow.others", this.inceptor.getBoolean(this.INSTANCE + ".tcp.allow.others"));
        data.put("shell", this.inceptor.getBoolean(this.INSTANCE + ".shell.enabled"));
        return data;
    }
    /** **/
    @Override
    public AbstractLauncherException getError() {
        return this.error;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private void warrantRequired() {
        if (null == this.error) {
            final Warranter vWter = singleton(ValueWarranter.class);
            final String[] params = new String[] { this.INSTANCE + ".encryption.enabled", this.INSTANCE + ".web.port",
                    this.INSTANCE + ".web.allow.others", this.INSTANCE + ".tcp.allow.others",
                    this.INSTANCE + ".shell.enabled" };
            try {
                vWter.warrant(this.inceptor, params);
            } catch (AbstractLauncherException ex) {
                peError(LOGGER, ex);
                this.error = ex;
            }
        }
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
