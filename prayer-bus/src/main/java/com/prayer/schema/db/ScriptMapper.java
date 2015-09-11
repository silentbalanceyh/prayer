package com.prayer.schema.db;

import com.prayer.model.h2.script.ScriptModel;

/**
 * 
 * @author Lang
 *
 */
public interface ScriptMapper extends H2TMapper<ScriptModel,String>{
	/**
	 * 
	 * @param name
	 * @return
	 */
	ScriptModel selectByName(String name);
}
