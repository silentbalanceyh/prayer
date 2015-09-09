package com.prayer.bus.deploy;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.prayer.model.bus.ServiceResult;
import com.prayer.model.h2.vx.UriModel;
import com.prayer.model.h2.vx.ValidatorModel;

/**
 * 
 * @author Lang
 *
 */
public interface ValidatorDPService extends TemplateDPService<ValidatorModel,String>{
	/**
	 * 
	 * @param jsonPath
	 * @param uri
	 * @return
	 */
	ServiceResult<ConcurrentMap<String,List<ValidatorModel>>> importValidators(String jsonPath, UriModel uri);
}
