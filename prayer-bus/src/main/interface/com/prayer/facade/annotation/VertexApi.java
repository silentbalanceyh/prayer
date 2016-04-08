package com.prayer.facade.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import com.prayer.constant.SystemEnum.Api;
/**
 * 读取用的API
 * @author Lang
 *
 */
@Target(ElementType.METHOD)
public @interface VertexApi {
    /** 当前API的类型 **/
    Api value();
}
