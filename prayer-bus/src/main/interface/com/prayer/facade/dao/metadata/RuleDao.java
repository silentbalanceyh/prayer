package com.prayer.facade.dao.metadata;

import java.util.List;

import com.prayer.constant.SystemEnum.ComponentType;
import com.prayer.facade.accessor.MetaAccessor;
import com.prayer.model.meta.vertx.PERule;

/**
 * 
 * @author Lang
 *
 */
public interface RuleDao extends MetaAccessor<PERule,String>{
    /**
     * 
     * @param uriId
     * @return
     */
    List<PERule> getByUri(String uriId);
    /**
     * 
     * @param uriId
     * @param type
     * @return
     */
    List<PERule> getByUriAndCom(String uriId, ComponentType type);
}
