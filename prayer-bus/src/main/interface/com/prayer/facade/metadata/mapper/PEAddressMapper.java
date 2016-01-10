package com.prayer.facade.metadata.mapper;

import org.apache.ibatis.annotations.Param;

import com.prayer.model.vertx.PEAddress;

/**
 * 
 * @author Lang  
 *
 */
public interface PEAddressMapper extends IBatisMapper<PEAddress, String> {
    /**
     * 
     * @param workClass
     * @return
     */
    PEAddress selectByClass(@Param("workClass") Class<?> workClass);
}
