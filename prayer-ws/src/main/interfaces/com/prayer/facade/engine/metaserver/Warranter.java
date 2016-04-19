package com.prayer.facade.engine.metaserver;

import com.prayer.facade.resource.Inceptor;
import com.prayer.fantasm.exception.AbstractLauncherException;

/**
 * 
 * @author Lang
 *
 */
public interface Warranter {
    /**
     * 保证
     * @param inceptor
     * @param key
     */
    void warrant(Inceptor inceptor, String... keys) throws AbstractLauncherException;
}
