package com.prayer.metaserver.h2;

import static com.prayer.util.reflection.Instance.instance;

import java.util.LinkedList;

import com.prayer.facade.engine.Options;
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
    public static LinkedList<Options> buildOptsList(@NotNull final Inceptor inceptor) throws AbstractLauncherException {
        final LinkedList<Options> options = new LinkedList<>();
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
