package com.prayer.facade.script;

import javax.script.ScriptException;

import com.prayer.model.business.Eidolon;

/**
 * Region，主要用于Local，单次请求的执行环境
 * 
 * @author Lang
 *
 */
public interface Region {
    /**
     * 将独立环境和Workshop的全局环境连接
     * 
     * @param workshop
     * @return
     * @throws ScriptException
     */
    boolean connect(Workshop workshop, Eidolon eidolon) throws ScriptException;

    /**
     * 执行脚本，执行完成过后返回Eidolon对象，得到对应的逐层变量引用信息
     * 
     * @return
     * @throws ScriptException
     */
    Eidolon execute() throws ScriptException;
}
