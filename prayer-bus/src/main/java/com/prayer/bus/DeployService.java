package com.prayer.bus;

import com.prayer.model.bus.ServiceResult;

/**
 * 
 * @author Lang
 *
 */
public interface DeployService {
	/**
	 * 用于初始化OOB数据的Deploy Service
	 */
	ServiceResult<Boolean> deployPrayerData();
}
