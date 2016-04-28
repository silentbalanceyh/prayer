package com.prayer.facade.script;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

/**
 * 脚本工厂，用于初始化脚本全局环境，初始化过后，可以得到所有环境中的内容，全是无参数接口
 * 
 * @author Lang
 *
 */
public interface Workshop {
    /**
     * 【Global】自己引用，创建一个新的Workshop，每个Workshop构造一个封闭的全局环境
     * 
     * @return
     */
    Workshop build() throws ScriptException;
    /**
     * 读取Engine
     * @return
     */
    ScriptEngine getEngine();
}
