package com.prayer.facade.dao.schema;

import com.prayer.model.vertx.AddressModel;
/**
 * 
 * @author Lang
 *
 */
public interface AddressDao extends TemplateDao<AddressModel, String> {
    /**
     * 
     * @param workClass
     * @return
     */
    AddressModel getByClass(String workClass);
}
