package com.prayer.kernel.i;

import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.impl.ClusterSerializable;

/**
 * 
 * @author Lang
 *
 */
public interface JsonEntity extends ClusterSerializable {
    /**
     * 将当前对象转换成Json
     * @return
     */
    JsonObject toJson();
    /**
     * 从一个JsonObject得到当前的Json引用
     * @param value
     * @return
     */
    JsonEntity fromJson(JsonObject value);
}
