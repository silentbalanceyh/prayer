package com.prayer.metaserver.h2;

import java.util.List;

import com.prayer.facade.engine.Options;
import com.prayer.facade.engine.metaserver.OptionsIntaker;
import com.prayer.facade.resource.Inceptor;
import com.prayer.facade.resource.Point;
import com.prayer.fantasm.exception.AbstractLauncherException;
import com.prayer.resource.InceptBus;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * H2 Database配置加载器
 * 
 * @author Lang
 *
 */
@Guarded
public class H2OptionsIntaker implements OptionsIntaker {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public Options ingest(@NotNull @NotBlank @NotEmpty final String file) throws AbstractLauncherException {
        /** 1.使用file生成DynamicInceptor **/
        final Inceptor inceptor = InceptBus.build(Point.MetaServer.class, file);
        /** 2.获取当前所有的Options **/
        final List<Options> optList = H2OptsHoder.buildOptsList(inceptor);
        for (final Options opt : optList) {
            if (null != opt && null != opt.getError()) {
                throw opt.getError();
            }
            System.out.println(opt.readOpts());
        }
        return null;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
