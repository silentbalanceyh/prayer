package com.prayer.ag.util;

import java.util.ArrayList;
import java.util.List;

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
public final class Input {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 将输入的一行字符串拆分成参数，每个参数用空白隔开，如果多个空白直接压缩
     * 
     * @param line
     * @return
     */
    public static String[] commandArgs(@NotNull @NotBlank @NotEmpty final String line) {
        final String[] args = line.split("\\s");
        final List<String> retArgs = new ArrayList<>();
        for (final String arg : args) {
            if (null != arg && !"".equals(arg.trim())) {
                retArgs.add(arg);
            }
        }
        return retArgs.toArray(new String[] {});
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private Input() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
