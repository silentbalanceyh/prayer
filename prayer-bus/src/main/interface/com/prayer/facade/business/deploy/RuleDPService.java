package com.prayer.facade.business.deploy;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.prayer.model.business.ServiceResult;
import com.prayer.model.meta.vertx.PERule;
import com.prayer.model.meta.vertx.PEUri;

/**
 * 
 * @author Lang
 *
 */
public interface RuleDPService extends TemplateDPService<PERule,String>{
    /**
     * 
     * @param jsonPath
     * @param uri
     * @return
     */
    ServiceResult<ConcurrentMap<String,List<PERule>>> importRules(String jsonPath, PEUri uri);
}
