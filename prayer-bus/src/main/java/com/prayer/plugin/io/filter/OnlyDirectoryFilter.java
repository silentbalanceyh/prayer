package com.prayer.plugin.io.filter;

import java.io.File;
import java.io.FileFilter;

/**
 * 只读取目录，不读取文件
 * 
 * @author Lang
 *
 */
public class OnlyDirectoryFilter implements FileFilter {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public boolean accept(final File file) {
        return file.isDirectory();
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
