package com.prayer.facade.business.endpoint;

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
@VertexPoint(Interface.RESTFUL)
public interface SchemaStubor {
    /**
     * Web Request，传入文件路径，将内容直接同步：Json -> Meta ( H2 ) -> Database
     * 
     * @param request
     * @return
     */
    @VertexApi(Api.WRITE)
    WebResponse synchronize(WebRequest request);

    /**
     * Web Request，传入identifier，读取当前Schema信息
     * 
     * @param request
     * @return
     */
    @VertexApi(Api.READ)
    WebResponse findById(WebRequest request);

    /**
     * Web Request，传入identifier，删除当前Schema信息
     * 
     * @param request
     * @return
     */
    @VertexApi(Api.WRITE)
    WebResponse removeById(WebRequest request);
}
