package com.prayer.schema.dao;

import com.prayer.model.h2.vx.RouteModel;

/**
 * 
 * @author Lang
 *
 */
public interface RouteDao extends TemplateDao<RouteModel, String>{
	/**
	 * 
	 * @param parent
	 * @param path
	 * @return
	 */
	RouteModel getByPath(String parent, String path);
}
