package com.prayer.schema.db;

import com.prayer.model.h2.vx.AddressModel;

/**
 * 
 * @author Lang
 *
 */
public interface AddressMapper extends H2TMapper<AddressModel, String> {
	/**
	 * 
	 * @param workClass
	 * @return
	 */
	AddressModel selectByClass(final String workClass);
}
