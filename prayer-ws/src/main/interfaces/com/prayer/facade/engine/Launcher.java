package com.prayer.facade.engine;

import com.prayer.fantasm.exception.AbstractException;

/**
 * 启动器
 * @author Lang
 *
 */
public interface Launcher {
    /**
     * 
     * @param options
     * @throws AbstractException
     */
    void start() throws AbstractException;
}
