package com.prayer.facade.dao.schema;

import java.util.List;

import com.prayer.model.h2.vertx.RuleModel;

/**
 * 
 * @author Lang
 *
 */
public interface RuleDao extends TemplateDao<RuleModel,String>{
    /**
     * 
     * @param uriId
     * @return
     */
    List<RuleModel> getByUri(String uriId);
}