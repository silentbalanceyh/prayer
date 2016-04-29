package com.prayer.facade.script;

import javax.script.ScriptException;

import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.business.Eidolon;
import com.prayer.model.business.behavior.ActRequest;

/**
 * Region，主要用于Local，单次请求的执行环境
 * 
 * @author Lang
 *
 */
public interface Region {

    /**
     * 执行脚本，执行完成过后返回Eidolon对象，得到对应的逐层变量引用信息
     * 
     * @return
     * @throws ScriptException
     */
    Eidolon execute(ActRequest request) throws ScriptException, AbstractException;
}
