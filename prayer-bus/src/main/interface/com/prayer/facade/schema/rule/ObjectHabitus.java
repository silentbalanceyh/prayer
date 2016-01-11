package com.prayer.facade.schema.rule;

import java.util.Set;
import java.util.concurrent.ConcurrentMap;

/**
 * 当前Json节点的状态接口，用于封装读取到的JsonObject对象
 * @author Lang
 *
 */
public interface ObjectHabitus {
    /**
     * 获取当前节点中的所有属性表
     * @return
     */
    Set<String> attributes();
    /**
     * 获取当前节点中所有属性的类型表
     * @return
     */
    ConcurrentMap<String,Class<?>> typeMap();
}
