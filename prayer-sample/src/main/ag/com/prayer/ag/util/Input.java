package com.prayer.ag.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    /**
     * 输入一个数字
     * @param args
     * @return
     */
    public static boolean verifyOneNumber(final String... args){
        /**
         * 只能是数字
         */
        boolean ret = true;
        final Pattern pattern = Pattern.compile("[0-9]+");
        final Matcher matcher = pattern.matcher(args[0]);
        if (!matcher.matches()) {
            ret = false;
        }
        return ret;
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
