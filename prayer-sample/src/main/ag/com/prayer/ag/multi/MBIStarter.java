package com.prayer.ag.multi;

import com.prayer.ag.Topic;

/**
 * 
 * @author Lang
 *
 */
public class MBIStarter {
    /**
     * 
     * @param args
     */
    public static void main(final String args[]) {
        final Topic driver = new MultiBigInteger();
        // 入口函数
        driver.runTool(args);
    }

}
