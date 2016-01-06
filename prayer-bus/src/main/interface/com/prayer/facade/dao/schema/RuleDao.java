package com.prayer.facade.dao.schema;

import java.util.List;

import com.prayer.constant.SystemEnum.ComponentType;
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
    /**
     * 
     * @param uriId
     * @param type
     * @return
     */
    List<RuleModel> getByUriAndCom(String uriId, ComponentType type);
}
