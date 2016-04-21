package com.prayer.metaserver.h2.config;

import static com.prayer.util.reflection.Instance.instance;

import java.util.ArrayList;
import java.util.List;

import com.prayer.facade.engine.opts.Options;
import com.prayer.facade.resource.Inceptor;
import com.prayer.fantasm.exception.AbstractLauncherException;
import com.prayer.metaserver.h2.opts.ClusterOptions;
import com.prayer.metaserver.h2.opts.ExtOptions;
import com.prayer.metaserver.h2.opts.JdbcOptions;
import com.prayer.metaserver.h2.opts.SingleOptions;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * H2连接配置专用类
 * 
 * @author Lang
 *
 */
@Guarded
public final class H2OptsHoder {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /** **/
    public static List<Options> buildOptsList(@NotNull final Inceptor inceptor) throws AbstractLauncherException {
        final List<Options> options = new ArrayList<>();
        /** 1.添加Jdbc参数 **/
        options.add(instance(JdbcOptions.class, inceptor));
        /** 2.添加Ext参数 **/
        options.add(instance(ExtOptions.class, inceptor));
        /** 3.区分Cluster模式 **/
        if (isClustered(inceptor)) {
            /** 5.添加Cluster的Options **/
            options.add(instance(ClusterOptions.class, inceptor));
        } else {
            /** 4.添加Single的Options **/
            options.add(instance(SingleOptions.class, inceptor));
        }
        return options;
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private static boolean isClustered(final Inceptor inceptor) {
        final String instance = inceptor.getString("meta.server.instance");
        final String clusterKey = instance + ".cluster.enabled";
        boolean clustered = false;
        if (inceptor.contains(clusterKey)) {
            clustered = inceptor.getBoolean(clusterKey);
        }
        return clustered;
    }

    private H2OptsHoder() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
