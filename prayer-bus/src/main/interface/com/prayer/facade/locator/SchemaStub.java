package com.prayer.facade.locator;

import com.prayer.fantasm.exception.AbstractWebException;
import com.prayer.model.web.WebRequest;
import com.prayer.model.web.WebResponse;

/**
 * 
 * @author Lang
 *
 */
public interface SchemaStub {
    /**
     * Web Request，传入文件路径，将内容直接同步：Json -> Meta ( H2 ) -> Database
     * 
     * @param request
     * @return
     */
    WebResponse synchronize(WebRequest request) throws AbstractWebException;

    /**
     * Web Request，传入identifier，读取当前Schema信息
     * 
     * @param request
     * @return
     */
    WebResponse findById(WebRequest request) throws AbstractWebException;

    /**
     * Web Request，传入identifier，删除当前Schema信息
     * 
     * @param request
     * @return
     */
    WebResponse removeById(WebRequest request) throws AbstractWebException;
}
