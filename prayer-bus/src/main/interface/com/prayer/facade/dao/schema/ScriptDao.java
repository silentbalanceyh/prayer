package com.prayer.facade.dao.schema;

import com.prayer.model.vertx.PEScript;

/**
 * 
 * @author Lang
 *
 */
public interface ScriptDao extends TemplateDao<PEScript, String>{
    /**
     * 
     * @param name
     * @return
     */
    PEScript getByName(String name);
}
