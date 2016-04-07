package com.prayer.facade.fun.deploy;

import com.prayer.fantasm.exception.AbstractException;

/**
 * Boolean接口
 * @author Lang
 *
 */
@FunctionalInterface
public interface AcusPoster {
    /**
     * 执行接口
     * @param params
     * @return
     * @throws AbstractException
     */
    boolean execute(String params) throws AbstractException;
}
