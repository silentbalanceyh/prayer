package com.prayer.schema.dao;

import com.prayer.exception.AbstractTransactionException;
import com.prayer.exception.h2.DataAccessException;
import com.prayer.model.bus.VerticleChain;
import com.prayer.model.h2.vx.VerticleModel;

/**
 * 
 * @author Lang
 *
 */
public interface VerticleDao extends TemplateDao<VerticleModel,String>{
    /**
     * 删除配置
     * 
     * @param name
     * @return
     * @throws DataAccessException
     */
    boolean deleteByName(String name) throws AbstractTransactionException;

    /**
     * 按照组名读取系统中所有的VerticleModel，返回的是一个VerticleChain对象
     * 
     * @param group
     * @return
     */
    VerticleChain getByGroup(String group);

    /**
     * 按照类名读取单个VerticleModel
     * 
     * @param clazz
     * @return
     */
    VerticleModel getByClass(String clazz);
}
