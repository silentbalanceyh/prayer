package com.prayer.facade.fun.entity;

import com.prayer.fantasm.model.AbstractEntity;

/**
 * 
 * @author Lang
 *
 */
@FunctionalInterface
public interface Entitier {
    /**
     * 生成新实例
     * @return
     */
    <T extends AbstractEntity<String>> T instance();
}
