package com.prayer.facade.engine.fun;

import com.prayer.vertx.web.model.Envelop;

/**
 * 函数接口方法
 * @author Lang
 *
 */
@FunctionalInterface
public interface ErrorHub {
    /** 函数接口用于Error的构建 **/
    Envelop build(Class<?> clazz, Object... params);
}
