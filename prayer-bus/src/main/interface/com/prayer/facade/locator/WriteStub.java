package com.prayer.facade.locator;

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
@VertexPoint(Interface.ENG_PRIVATE)
interface WriteStub {
    /**
     * PUT请求专用方法
     * @param request
     * @return
     */
    @VertexApi(Api.WRITE)
    WebResponse put(WebRequest request);
    /**
     * POST请求专用方法
     * @param request
     * @return
     */
    @VertexApi(Api.WRITE)
    WebResponse post(WebRequest request);
    /**
     * DELETE请求专用方法
     * @param request
     * @return
     */
    @VertexApi(Api.WRITE)
    WebResponse delete(WebRequest request);
}
