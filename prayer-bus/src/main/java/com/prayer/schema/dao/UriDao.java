package com.prayer.schema.dao;

import com.prayer.model.h2.vx.UriModel;

/**
 * 
 * @author Lang
 *
 */
public interface UriDao extends TemplateDao<UriModel, String>{
	/**
	 * 
	 * @param uri
	 * @return
	 */
	UriModel getByUri(String uri);
}