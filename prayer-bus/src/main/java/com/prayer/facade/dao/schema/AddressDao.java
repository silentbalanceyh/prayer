package com.prayer.facade.dao.schema;

import com.prayer.model.h2.vertx.AddressModel;
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
