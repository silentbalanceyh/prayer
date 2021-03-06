package com.prayer.facade.dao.schema;

import com.prayer.model.vertx.ScriptModel;

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
