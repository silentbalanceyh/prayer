package com.prayer.metaserver.h2.opts;

import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.engine.Warranter;
import com.prayer.facade.engine.opts.Options;
import com.prayer.facade.resource.Inceptor;
import com.prayer.fantasm.exception.AbstractLauncherException;
import com.prayer.util.warranter.NumericWarranter;
import com.prayer.util.warranter.ValueWarranter;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;

/**
 * 
 * @author Lang
 *
 */
public class SingleOptions implements Options {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(SingleOptions.class);
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    private transient final Inceptor inceptor;
    /** **/
    private transient AbstractLauncherException error;
    /** **/
    @NotNull
    private transient final String INSTANCE;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    private SingleOptions(@NotNull final Inceptor inceptor) {
        this.inceptor = inceptor;
        this.INSTANCE = this.inceptor.getString("meta.server.instance");
        /** 构造：1.必须参数 **/
        this.warrantRequired();
        /** 构造：2.数字格式 **/
        this.warrantNumeric();
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public JsonObject readOpts() {
        final JsonObject data = new JsonObject();
        data.put("tcp.port", this.inceptor.getInt(this.INSTANCE + ".tcp.port"));
        data.put("host", this.inceptor.getString(this.INSTANCE + ".host"));
        return data;
    }

    /** **/
    @Override
    public AbstractLauncherException getError() {
        return this.error;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private void warrantNumeric() {
        if (null == this.error) {
            final Warranter vWter = singleton(NumericWarranter.class);
            final String[] params = new String[] { this.INSTANCE + ".tcp.port" };
            try {
                vWter.warrant(this.inceptor, params);
            } catch (AbstractLauncherException ex) {
                peError(LOGGER, ex);
                this.error = ex;
            }
        }
    }

    private void warrantRequired() {
        if (null == this.error) {
            final Warranter vWter = singleton(ValueWarranter.class);
            final String[] params = new String[] { this.INSTANCE + ".host", this.INSTANCE + ".tcp.port" };
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
