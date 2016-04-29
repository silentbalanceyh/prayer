package com.prayer.facade.script;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;

/**
 * 脚本工厂，用于初始化脚本全局环境，初始化过后，可以得到所有环境中的内容，全是无参数接口
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.ENG_PUBLIC)
public interface Workshop {
    /**
     * 【Global】自己引用，创建一个新的Workshop，每个Workshop构造一个封闭的全局环境
     * 
     * @return
     */
    @VertexApi(Api.TOOL)
    Workshop build() throws ScriptException;
    /**
     * 读取Engine
     * @return
     */
    @VertexApi(Api.TOOL)
    ScriptEngine getEngine();
}
