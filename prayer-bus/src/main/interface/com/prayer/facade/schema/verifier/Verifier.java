package com.prayer.facade.schema.verifier;

import com.prayer.base.exception.AbstractSchemaException;

import io.vertx.core.json.JsonObject;

/**
 * Schema的新验证接口，主要使用VertX里面提供的JsonObject以及JsonArray
 * 
 * @author Lang
 * 
 */
public interface Verifier {
    /**
     * 验证构造Schema的元数据对象是否有错
     * 
     * @param path
     * @return
     */
    AbstractSchemaException verify(JsonObject data);
}
