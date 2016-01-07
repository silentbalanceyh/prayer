package com.prayer.facade.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.prayer.constant.SystemEnum.ComponentType;
import com.prayer.model.vertx.RuleModel;

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
    List<RuleModel> selectByUri(@Param("refId") String refId);
    /**
     * 
     * @param uriId
     * @param type
     * @return
     */
    List<RuleModel> selectByUriAndCom(@Param("refId")String refUriId, @Param("type") ComponentType type);
}
