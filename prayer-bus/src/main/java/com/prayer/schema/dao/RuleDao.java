package com.prayer.schema.dao;

import java.util.List;

import com.prayer.model.h2.vx.RuleModel;

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
