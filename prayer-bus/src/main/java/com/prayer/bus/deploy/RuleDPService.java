package com.prayer.bus.deploy;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.prayer.model.bus.ServiceResult;
import com.prayer.model.h2.vx.UriModel;
import com.prayer.model.h2.vx.RuleModel;

/**
 * 
 * @author Lang
 *
 */
public interface RuleDPService extends TemplateDPService<RuleModel,String>{
	/**
	 * 
	 * @param jsonPath
	 * @param uri
	 * @return
	 */
	ServiceResult<ConcurrentMap<String,List<RuleModel>>> importRules(String jsonPath, UriModel uri);
}
