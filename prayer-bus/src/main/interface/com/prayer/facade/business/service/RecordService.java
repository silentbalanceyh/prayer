package com.prayer.facade.business.service;

import javax.script.ScriptException;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.business.behavior.ActRequest;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.SERVICE)
public interface RecordService {
    /**
     * GET专用
     * @param request
     * @return
     */
    @VertexApi(Api.READ)
    JsonObject find(ActRequest request) throws ScriptException, AbstractException;
    /**
     * PAGE专用
     * @param request
     * @return
     */
    @VertexApi(Api.READ)
    JsonObject page(ActRequest request) throws ScriptException, AbstractException;
    /**
     * Save专用：PUT，POST
     * @param request
     * @return
     */
    @VertexApi(Api.WRITE)
    JsonObject save(ActRequest request) throws ScriptException, AbstractException;
    /**
     * DELETE专用：DELETE
     * @param request
     * @return
     */
    @VertexApi(Api.WRITE)
    JsonObject remove(ActRequest request) throws ScriptException, AbstractException;
}
