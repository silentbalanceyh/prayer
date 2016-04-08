package com.prayer.facade.schema.verifier;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.fantasm.exception.AbstractSchemaException;

import io.vertx.core.json.JsonObject;

/**
 * Schema的新验证接口，主要使用VertX里面提供的JsonObject以及JsonArray
 * 
 * @author Lang
 * 
 */
@VertexPoint(Interface.ENG_PUBLIC)
public interface Verifier {
    /**
     * 验证构造Schema的元数据对象是否有错
     * 
     * @param path
     * @return
     */
    @VertexApi(Api.TOOL)
    AbstractSchemaException verify(JsonObject data);
}
