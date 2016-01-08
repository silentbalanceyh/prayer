package com.prayer.facade.mapper;

import org.apache.ibatis.annotations.Param;

import com.prayer.model.vertx.PEAddress;

/**
 * 
 * @author Lang
 *
 */
public interface AddressMapper extends H2TMapper<PEAddress, String> {
    /**
     * 
     * @param workClass
     * @return
     */
    PEAddress selectByClass(@Param("workClass") Class<?> workClass);
}
