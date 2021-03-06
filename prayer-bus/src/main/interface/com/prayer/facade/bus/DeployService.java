package com.prayer.facade.bus;

import com.prayer.model.bus.ServiceResult;

/**
 * 
 * @author Lang
 *
 */
public interface DeployService {
    // ~ OOB Deployment =======================================
    /**
     * 用于初始化OOB数据的Deploy Service
     */
    ServiceResult<Boolean> deployMetadata(String rootFolder);
    /**
     * 初始化Database中的元数据
     * @return
     */
    ServiceResult<Boolean> initMetadata(String scriptPath);
}
