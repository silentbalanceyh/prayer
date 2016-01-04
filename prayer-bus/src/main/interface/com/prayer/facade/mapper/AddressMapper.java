package com.prayer.facade.mapper;

import com.prayer.model.h2.vertx.AddressModel;

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
