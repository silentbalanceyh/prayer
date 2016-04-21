package com.prayer.metaserver.warranter;

import java.io.InputStream;

import com.prayer.exception.launcher.ReferenceFileException;
import com.prayer.facade.engine.Warranter;
import com.prayer.facade.resource.Inceptor;
import com.prayer.fantasm.exception.AbstractLauncherException;
import com.prayer.util.io.IOKit;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class FileWarranter implements Warranter {

    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void warrant(@NotNull final Inceptor inceptor, @NotNull final String... keys)
            throws AbstractLauncherException {
        /** 判断文件是否存在 **/
        for (final String key : keys) {
            final String file = inceptor.getString(key);
            final InputStream in = IOKit.getFile(file);
            if (null == in) {
                throw new ReferenceFileException(getClass(), file, inceptor.getFile());
            }
        }
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
