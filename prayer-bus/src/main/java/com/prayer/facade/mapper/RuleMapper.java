package com.prayer.facade.mapper;

import java.util.List;

import com.prayer.model.h2.vertx.RuleModel;
import com.prayer.util.cv.SystemEnum.ComponentType;

/**
 * 
 * @author Lang
 *
 */
public interface RuleMapper extends H2TMapper<RuleModel,String>{
    /**
     * 
     * @param uriId
     * @return
     */
    List<RuleModel> selectByUri(String refUriId);
    /**
     * 
     * @param uriId
     * @param type
     * @return
     */
    List<RuleModel> selectByUriAndCom(String refUriId, ComponentType type);
}
