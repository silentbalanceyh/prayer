package com.prayer.ag.util;

import java.util.Scanner;

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
public final class Console {
    // ~ Static Fields =======================================
    /** **/
    public static final String PREFIX = " > ";

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 读取所有行信息，开启交互式Console
     * 
     * @param prefix
     * @return
     */
    @NotNull
    @NotEmpty
    @NotBlank
    public static String prompt() {
        @SuppressWarnings("resource")
        final Scanner scanner = new Scanner(System.in);
        System.out.print(PREFIX);
        return scanner.nextLine();
    }

    /**
     * 打印最开始的头部信息
     */
    public static void start(@NotNull @NotEmpty @NotBlank final String topic) {
        System.out.println("Welcome to Big/Little Fly Samples:");
        System.out.println("Topic --> " + topic);
    }

    /**
     * 将一个String[]转换成String，辅助打印输出
     * 
     * @param args
     * @return
     */
    public static String toStr(final String... args) {
        StringBuilder builder = new StringBuilder();
        for (final String arg : args) {
            builder.append(arg).append(',');
        }
        return builder.toString();
    }
    /**
     * 
     * @param args
     * @return
     */
    public static String toStr(final int... args){
        StringBuilder builder = new StringBuilder();
        for (final int arg : args) {
            builder.append(arg).append(',');
        }
        return builder.toString();
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private Console() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
