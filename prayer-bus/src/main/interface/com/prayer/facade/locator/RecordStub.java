package com.prayer.facade.locator;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.model.web.WebRequest;
import com.prayer.model.web.WebResponse;

/**
 * Restful服务接口
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.RESTFUL)
public interface RecordStub extends WriteStub {

    /**
     * GET请求专用方法
     * 
     * @param request
     * @return
     */
    @VertexApi(Api.READ)
    WebResponse get(WebRequest request);

    /**
     * （POST）Page请求专用方法专用方法
     * 
     * @param request
     * @return
     */

    @VertexApi(Api.READ)
    WebResponse page(WebRequest request);
}
