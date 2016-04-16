package com.prayer.facade.business.service;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.model.business.behavior.ActRequest;
import com.prayer.model.business.behavior.ActResponse;

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
    ActResponse find(ActRequest request);
    /**
     * PAGE专用
     * @param request
     * @return
     */
    @VertexApi(Api.READ)
    ActResponse page(ActRequest request);
    /**
     * Save专用：PUT，POST
     * @param request
     * @return
     */
    @VertexApi(Api.WRITE)
    ActResponse save(ActRequest request);
    /**
     * DELETE专用：DELETE
     * @param request
     * @return
     */
    @VertexApi(Api.WRITE)
    ActResponse remove(ActRequest request);
}
