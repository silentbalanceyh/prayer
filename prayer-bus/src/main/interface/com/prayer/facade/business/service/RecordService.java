package com.prayer.facade.business.service;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.model.web.WebRequest;
import com.prayer.model.web.WebResponse;

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
    WebResponse find(WebRequest request);
    /**
     * PAGE专用
     * @param request
     * @return
     */
    @VertexApi(Api.READ)
    WebResponse page(WebRequest request);
    /**
     * Save专用：PUT，POST
     * @param request
     * @return
     */
    @VertexApi(Api.WRITE)
    WebResponse save(WebRequest request);
    /**
     * DELETE专用：DELETE
     * @param request
     * @return
     */
    @VertexApi(Api.WRITE)
    WebResponse remove(WebRequest request);
}
