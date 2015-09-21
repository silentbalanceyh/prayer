package com.prayer.schema.db;

import java.util.List;

import com.prayer.model.h2.vx.RuleModel;

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
    List<RuleModel> selectByUri(String uriId);
}
