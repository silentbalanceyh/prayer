package com.prayer.facade.dao.schema;

import com.prayer.facade.accessor.MetaAccessor;
import com.prayer.model.meta.vertx.PEScript;

/**
 * 
 * @author Lang
 *
 */
public interface ScriptDao extends MetaAccessor<PEScript, String>{
    /**
     * 
     * @param name
     * @return
     */
    PEScript getByName(String name);
}
