package com.prayer.facade.script;

import javax.script.ScriptException;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.business.Eidolon;
import com.prayer.model.business.behavior.ActRequest;

/**
 * Region，主要用于Local，单次请求的执行环境
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.ENG_PUBLIC)
public interface Region {

    /**
     * 执行脚本，执行完成过后返回Eidolon对象，得到对应的逐层变量引用信息
     * 
     * @return
     * @throws ScriptException
     */
    @VertexApi(Api.TOOL)
    Eidolon execute(ActRequest request) throws ScriptException, AbstractException;
}
