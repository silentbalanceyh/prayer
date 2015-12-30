package com.prayer.demo;
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
}
