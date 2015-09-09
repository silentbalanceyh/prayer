package com.prayer.schema.db;

import java.util.List;

import com.prayer.model.h2.vx.ValidatorModel;

/**
 * 
 * @author Lang
 *
 */
public interface ValidatorMapper extends H2TMapper<ValidatorModel,String>{
	/**
	 * 
	 * @param uriId
	 * @return
	 */
	List<ValidatorModel> selectByUri(String uriId);
}
