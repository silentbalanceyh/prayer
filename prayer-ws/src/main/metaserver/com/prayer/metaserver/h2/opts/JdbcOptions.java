package com.prayer.metaserver.h2.opts;

import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.engine.Options;
import com.prayer.facade.engine.metaserver.Warranter;
import com.prayer.facade.resource.Inceptor;
import com.prayer.fantasm.exception.AbstractLauncherException;
import com.prayer.metaserver.h2.util.JdbcResolver;
import com.prayer.metaserver.warranter.FileWarranter;
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
public class JdbcOptions implements Options {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcOptions.class);
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    private transient Inceptor inceptor;
    /** **/
    private transient AbstractLauncherException error;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param inceptor
     */
    private JdbcOptions(@NotNull final Inceptor inceptor) {
        this.inceptor = inceptor;
        /** 构造：1.必须参数检查 **/
        this.warrantRequired();
        /** 构造：2.检查File参数 **/
        this.warrantRefFile();
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public JsonObject readOpts() {
        return JdbcResolver.resolve(this.inceptor);
    }

    /** **/
    @Override
    public AbstractLauncherException getError() {
        return this.error;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private void warrantRefFile() {
        if (null == this.error) {
            final Warranter fWter = singleton(FileWarranter.class);
            final String[] params = new String[] { "meta.server.external" };
            try {
                fWter.warrant(this.inceptor, params);
            } catch (AbstractLauncherException ex) {
                peError(LOGGER, ex);
                this.error = ex;
            }
        }
    }

    private void warrantRequired() {
        if (null == this.error) {
            final Warranter vWter = singleton(ValueWarranter.class);
            final String[] params = new String[] { "meta.server.external", "meta.server.username",
                    "meta.server.password", "meta.server.database", "meta.server.instance" };
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
