package com.prayer.schema.db;

import com.prayer.model.h2.vx.RouteModel;

/**
 * 
 * @author Lang
 *
 */
public interface RouteMapper extends H2TMapper<RouteModel, String>{
	/**
	 * 
	 * @param parent
	 * @param path
	 * @return
	 */
	RouteModel selectByPath(String parent, String path);
}
