package com.prayer.facade.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.prayer.constant.SystemEnum.ComponentType;
import com.prayer.model.vertx.PERule;

/**
 * 
 * @author Lang
 *
 */
public interface RuleMapper extends H2TMapper<PERule,String>{
    /**
     * 
     * @param uriId
     * @return
     */
    List<PERule> selectByUri(@Param("refId") String refId);
    /**
     * 
     * @param uriId
     * @param type
     * @return
     */
    List<PERule> selectByUriAndCom(@Param("refId")String refUriId, @Param("type") ComponentType type);
}
