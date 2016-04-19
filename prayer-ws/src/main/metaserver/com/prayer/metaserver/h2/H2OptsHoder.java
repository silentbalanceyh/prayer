package com.prayer.metaserver.h2;

import static com.prayer.util.reflection.Instance.instance;

import java.util.LinkedList;

import com.prayer.facade.engine.Options;
import com.prayer.facade.resource.Inceptor;
import com.prayer.fantasm.exception.AbstractLauncherException;
import com.prayer.metaserver.h2.opts.JdbcOptions;

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
        
        return options;
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private H2OptsHoder() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
