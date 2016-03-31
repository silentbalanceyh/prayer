package com.prayer.ag.util;

import net.sf.oval.exception.ConstraintsViolatedException;

/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractTopic {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    /**
     * 重写Title
     * 
     * @return
     */
    public abstract String title();

    /**
     * 
     * @param args
     * @return
     */
    public abstract boolean verifyInput(String... args);

    /**
     * 
     * @param args
     * @return
     */
    public abstract String process(String... args);

    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 
     * @param args
     */
    public void runTool(final String... args) {
        /**
         * 1.打印Console的头部信息
         */
        Console.start(this.title());
        /**
         * 2.解析输入参数
         */
        String[] inputArgs = Input.commandArgs(Console.prompt());
        while (true) {
            try {
                /**
                 * 3.验证输入参数
                 */
                if (this.verifyInput(inputArgs)) {
                    /**
                     * 4.3.执行主逻辑，得到结果
                     */
                    final String result = this.process(inputArgs);
                    /**
                     * 4.4.打印结果集，程序执行完成
                     */
                    System.out.println("[SUCCESS] Result is : " + result);
                    System.exit(0);
                } else {
                    /**
                     * 4.2.参数验证问题
                     */
                    System.out.println("[ERROR] Arguments format is wrong. Args = " + Console.toStr(inputArgs));
                    inputArgs = Input.commandArgs(Console.prompt());
                    continue;
                }
            } catch (ConstraintsViolatedException ex) {
                /**
                 * 4.1.参数长度验证未通过，长度必须是2
                 */
                System.out.println("[ERROR] Input Arguments invalid, please check constraints for args!");
                inputArgs = Input.commandArgs(Console.prompt());
                continue;
            }
        }
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
