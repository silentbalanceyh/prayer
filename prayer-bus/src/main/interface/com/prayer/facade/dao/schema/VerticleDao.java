package com.prayer.facade.dao.schema;

import java.util.List;

import com.prayer.base.exception.AbstractTransactionException;
import com.prayer.exception.database.DataAccessException;
import com.prayer.model.vertx.PEVerticle;

/**
 * 
 * @author Lang
 *
 */
public interface VerticleDao extends TemplateDao<PEVerticle,String>{
    /**
     * 删除配置
     * 
     * @param name
     * @return
     * @throws DataAccessException
     */
    boolean deleteByName(Class<?> name) throws AbstractTransactionException;

    /**
     * 按照组名读取系统中所有的VerticleModel，返回的是一个VerticleChain对象
     * 
     * @param group
     * @return
     */
    List<PEVerticle> getByGroup(String group);

    /**
     * 按照类名读取单个VerticleModel
     * 
     * @param clazz
     * @return
     */
    PEVerticle getByClass(Class<?> clazz);
}
