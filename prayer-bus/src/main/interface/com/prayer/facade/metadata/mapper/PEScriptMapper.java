package com.prayer.facade.metadata.mapper;

import java.io.Serializable;

import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.facade.entity.Entity;

/**
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.THIRD_PART)
public interface PEScriptMapper extends IBatisMapper<Entity, Serializable> {

}
