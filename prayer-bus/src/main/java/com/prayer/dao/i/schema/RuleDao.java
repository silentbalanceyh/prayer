package com.prayer.dao.i.schema;

import java.util.List;

import com.prayer.model.vertx.RuleModel;

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
