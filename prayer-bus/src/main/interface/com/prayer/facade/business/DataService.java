package com.prayer.facade.business;

import com.prayer.model.business.ServiceResult;

/**
 * 
 * @author Lang
 *
 */
public interface DataService {
    /**
     * 根据Global Id发布单个数据
     * @param identifier
     * @return
     */
    ServiceResult<Boolean> deployData(String rootFolder,String identifier);
    /**
     * 
     * @param identifier
     * @return
     */
    ServiceResult<Boolean> purgeData(String identifier);
    /**
     * 发布所有目录下的数据信息
     * @return
     */
    ServiceResult<Boolean> deployAllData(String rootFolder);
}
