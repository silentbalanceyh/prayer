package com.prayer.facade.bus.deploy;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.prayer.model.bus.ServiceResult;
import com.prayer.model.vertx.PERule;
import com.prayer.model.vertx.PEUri;

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
