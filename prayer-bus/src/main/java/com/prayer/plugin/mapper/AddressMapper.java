package com.prayer.plugin.mapper;

import com.prayer.model.vertx.AddressModel;

/**
 * 
 * @author Lang
 *
 */
public interface AddressMapper extends H2TMapper<AddressModel, String> {
    /**
     * 
     * @param workClass
     * @return
     */
    AddressModel selectByClass(final String workClass);
}
