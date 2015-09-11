package com.prayer.schema.dao;

import com.prayer.model.h2.vx.AddressModel;
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
