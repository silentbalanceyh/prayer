package com.prayer.schema.dao;

import com.prayer.model.h2.script.ScriptModel;

/**
 * 
 * @author Lang
 *
 */
public interface ScriptDao extends TemplateDao<ScriptModel, String>{
	/**
	 * 
	 * @param name
	 * @return
	 */
	ScriptModel getByName(String name);
}
