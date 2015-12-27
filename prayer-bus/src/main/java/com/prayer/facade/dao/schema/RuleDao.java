package com.prayer.facade.dao.schema;

import java.util.List;

import com.prayer.model.h2.vertx.RuleModel;
import com.prayer.util.cv.SystemEnum.ComponentType;

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
