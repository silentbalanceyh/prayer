package com.prayer.facade.deployment;

import com.prayer.model.business.ServiceResult;

/**
 * 
 * @author Lang
 *
 */
public interface DeployService {
    /**
     * 1.执行元数据初始化工作，针对SQL模式（H2）生成基础表结构
     * 
     * @param sqlfile
     * @return
     */
    ServiceResult<Boolean> initialize(String sqlfile);

    /**
     * 2.执行Deploy操作，将目录下边所有的元数据导入到Metadata中，并且在数据库中生成对应Database表
     * 
     * @param folder
     * @return
     */
    ServiceResult<Boolean> manoeuvre(String folder);

    /**
     * 3.执行Purge操作，必须删除SQL数据库中的表信息
     * 
     * @return
     */
    ServiceResult<Boolean> purge();
}
