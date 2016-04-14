package com.prayer.facade.database.mapper;

import java.io.Serializable;

import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.facade.model.entity.Entity;

/**
 * 
 * @author Lang  
 *
 */
@VertexPoint(Interface.THIRD_PART)
public interface PERuleMapper extends IBatisMapper<Entity, Serializable> {
}
