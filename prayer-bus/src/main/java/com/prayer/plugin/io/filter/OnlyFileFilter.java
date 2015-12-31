package com.prayer.plugin.io.filter;

import java.io.File;
import java.io.FileFilter;

/**
 * 读取非目录文件，不限后缀名
 * @author Lang
 *
 */
public final class OnlyFileFilter implements FileFilter{
    /**
     * 
     */
    @Override
    public boolean accept(final File file) {
        return !file.isDirectory();
    }
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
