package com.prayer.facade.business.deploy;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.prayer.model.business.ServiceResult;

/**
 * 
 * @author Lang
 *
 */
public interface TemplateDPService<T, ID extends Serializable> { // NOPMD
    /**
     * 
     * @param jsonPath
     * @return
     */
    ServiceResult<List<T>> importToList(String jsonPath);

    /**
     * 
     * @param jsonPath
     * @param field
     * @return
     */
    ServiceResult<ConcurrentMap<String, T>> importToMap(String jsonPath, String field);

    /**
     * 
     * @param jsonPath
     * @param field
     * @return
     */
    ServiceResult<ConcurrentMap<String, List<T>>> importToMapList(String jsonPath, String field);

    /**
     * 
     * @return
     */
    ServiceResult<Boolean> purgeData();
}
