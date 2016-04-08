package com.prayer.facade.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import com.prayer.constant.SystemEnum.Interface;

/**
 * 注释用的接口，用于表示当前接口的类型，方便接口扫描
 * 
 * @author Lang
 *
 */
@Target(ElementType.TYPE)
public @interface VertexPoint {
    /**
     * 使用Interface枚举类型
     * 
     * @return
     */
    Interface value();
}
