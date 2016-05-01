package com.prayer.facade.vtx.uca;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.facade.model.crucial.Value;
import com.prayer.fantasm.exception.AbstractWebException;
import com.prayer.vertx.web.model.Envelop;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.INTERNAL)
public interface Convertor {
    /**
     * 
     * @param name
     * @param value
     * @param config
     * @return
     * @throws AbstractWebException
     */
    @VertexApi(Api.TOOL)
    Envelop convert(String name, Value<?> value, JsonObject config);
}
