package com.prayer.schema.db;

import com.prayer.model.h2.vx.UriModel;

/**
 * 
 * @author Lang
 *
 */
public interface UriMapper extends H2TMapper<UriModel, String>{
	/**
	 * 
	 * @param uri
	 * @return
	 */
	UriModel selectByUri(String uri);
}
