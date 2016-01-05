package com.prayer.ag;
/**
 * 
 * @author Lang
 *
 */
public interface Topic {
    /**
     * 验证参数的函数
     * @return
     */
    boolean verifyInput(String... args);
    /**
     * 执行主要逻辑的函数
     * @return
     */
    String process(String... args);
    /**
     * 返回当前这个Topic的Title
     * @return
     */
    String title();
    /**
     * 运行参数
     * @param args
     */
    void runTool(final String... args);
}
