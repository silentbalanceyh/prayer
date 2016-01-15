package com.prayer.facade.fun.schema;

/**
 * 
 * @author Lang
 *
 */
@FunctionalInterface
public interface Extractor<T> {
    /**
     * 
     * @param value
     * @return
     */
    T extract(Object value);
}
