package com.prayer.facade.mapper;

import org.apache.ibatis.annotations.Param;

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
    AddressModel selectByClass(@Param("workClass") Class<?> workClass);
}
