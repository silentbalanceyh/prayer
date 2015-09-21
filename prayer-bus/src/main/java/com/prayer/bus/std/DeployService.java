package com.prayer.bus.std;

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
    ServiceResult<Boolean> deployPrayerData();
    /**
     * 初始化H2 Database中的元数据
     * @return
     */
    ServiceResult<Boolean> initH2Database(String scriptPath);
}
