package com.prayer.facade.engine.opts;

import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.fantasm.exception.AbstractException;
/**
 * 配置读取器
 * @author Lang
 *
 */
@VertexPoint(Interface.INTERNAL)
public interface Intaker<T> {
    /**
     * 不提供路径读取Intaker
     * @param files
     * @return
     */
    T ingest() throws AbstractException;
}
