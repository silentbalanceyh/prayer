package com.prayer.facade.vtx;

import com.prayer.fantasm.exception.AbstractException;

import io.vertx.core.VertxOptions;

/**
 * 
 * @author Lang
 *
 */
public interface Promulgator {
    /**
     * 
     * @return
     * @throws AbstractException
     */
    boolean promulgate(VertxOptions options) throws AbstractException;
}
