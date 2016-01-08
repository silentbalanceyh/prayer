package com.prayer.facade.dao.schema;

import com.prayer.model.vertx.PEAddress;
/**
 * 
 * @author Lang
 *
 */
public interface AddressDao extends TemplateDao<PEAddress, String> {
    /**
     * 
     * @param workClass
     * @return
     */
    PEAddress getByClass(Class<?> workClass);
}
