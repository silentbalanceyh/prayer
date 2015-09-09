package com.prayer.schema.dao;

import java.util.List;

import com.prayer.model.h2.vx.ValidatorModel;

/**
 * 
 * @author Lang
 *
 */
public interface ValidatorDao extends TemplateDao<ValidatorModel,String>{
	/**
	 * 
	 * @param uriId
	 * @return
	 */
	List<ValidatorModel> getByUri(String uriId);
}
