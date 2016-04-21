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
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class NodeOptions implements Options {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(NodeOptions.class);
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    private transient final Inceptor inceptor;
    /** **/
    @NotNull
    private transient final String node;
    /** **/
    private transient AbstractLauncherException error;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public NodeOptions(@NotNull final Inceptor inceptor, @NotNull @NotBlank @NotEmpty final String node) {
        this.inceptor = inceptor;
        this.node = node;
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
        data.put("name", this.node);
        data.put("baseDir", this.inceptor.getString(this.node + ".baseDir"));
        data.put("tcp.port", this.inceptor.getInt(this.node + ".tcp.port"));
        /** 如果出现Host参数 **/
        if (this.inceptor.contains(this.node + ".host")) {
            data.put("host", this.inceptor.getString(this.node + ".host"));
        }
        return data;
    }

    /** **/
    @Override
    public AbstractLauncherException getError() {
        return error;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private void warrantNumeric() {
        if (null == this.error) {
            final Warranter vWter = singleton(NumericWarranter.class);
            final String[] params = new String[] { this.node + ".tcp.port" };
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
            final String[] params = new String[] { this.node + ".baseDir", this.node + ".tcp.port" };
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
