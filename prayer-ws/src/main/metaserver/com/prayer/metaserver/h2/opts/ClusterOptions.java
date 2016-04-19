package com.prayer.metaserver.h2.opts;

import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.instance;
import static com.prayer.util.reflection.Instance.singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.engine.Options;
import com.prayer.facade.engine.metaserver.Warranter;
import com.prayer.facade.resource.Inceptor;
import com.prayer.fantasm.exception.AbstractLauncherException;
import com.prayer.metaserver.warranter.ValueWarranter;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;

/**
 * 
 * @author Lang
 *
 */
public class ClusterOptions implements Options {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(ClusterOptions.class);
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    private transient final Inceptor inceptor;
    /** **/
    private transient AbstractLauncherException error;
    /** **/
    @NotNull
    private transient final String INSTANCE;
    /** **/
    private transient JsonArray target = new JsonArray();
    /** **/
    private transient JsonObject source = new JsonObject();

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    private ClusterOptions(@NotNull final Inceptor inceptor) {
        this.inceptor = inceptor;
        this.INSTANCE = this.inceptor.getString("meta.server.instance");
        /** 构造：1.必须参数 **/
        this.warrantRequired();
        /** 2.处理Source节点 **/
        this.warrantSource();
        /** 3.处理Target节点 **/
        this.warrantTarget();
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public JsonObject readOpts() {
        final JsonObject data = new JsonObject();
        data.put("cluster", this.buildCluster());
        data.put("nodes", this.buildNodes());
        return data;
    }

    /** **/
    @Override
    public AbstractLauncherException getError() {
        return this.error;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private JsonObject buildCluster() {
        final JsonObject data = new JsonObject();
        data.put("source", this.inceptor.getString(this.INSTANCE + ".cluster.source"));
        final String[] nodes = this.inceptor.getArray(this.INSTANCE + ".cluster.target");
        final JsonArray targets = new JsonArray();
        for (final String node : nodes) {
            targets.add(node);
        }
        data.put("target", targets);
        data.put("host", this.inceptor.getString(this.INSTANCE + ".host"));
        return data;
    }

    private JsonObject buildNodes() {
        final JsonObject data = new JsonObject();
        data.put("source", this.source);
        data.put("target", this.target);
        return data;
    }

    private void warrantSource() {
        if (null == this.error) {
            final String nodeName = this.inceptor.getString(this.INSTANCE + ".cluster.source");
            final Options options = instance(NodeOptions.class, this.inceptor, nodeName);
            if (null != options && null != options.getError()) {
                this.error = options.getError();
            } else {
                this.source = options.readOpts();
            }
        }
    }

    private void warrantTarget() {
        if (null == this.error) {
            final String[] nodes = this.inceptor.getArray(this.INSTANCE + ".cluster.target");
            for (final String node : nodes) {
                final Options options = instance(NodeOptions.class, this.inceptor, node);
                if (null != options && null != options.getError()) {
                    this.error = options.getError();
                    break;
                } else {
                    this.target.add(options.readOpts());
                }
            }
        }
    }

    private void warrantRequired() {
        if (null == this.error) {
            final Warranter vWter = singleton(ValueWarranter.class);
            final String[] params = new String[] { this.INSTANCE + ".cluster.source", this.INSTANCE + ".cluster.target",
                    this.INSTANCE + ".host" };
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
