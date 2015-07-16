package com.prayer.dao.schema;

import com.prayer.exception.system.DataLoadingException;
import com.prayer.mod.meta.GenericSchema;

/**
 * 
 * @author Lang
 *
 */
public interface SchemaDao {
	/**
	 * 
	 * @param schema
	 * @return
	 * @throws DataLoadingException
	 */
	GenericSchema buildModel(GenericSchema schema) throws DataLoadingException;
	/**
	 * 
	 * @param schema
	 * @return
	 * @throws DataLoadingException
	 */
	GenericSchema syncModel(GenericSchema schema) throws DataLoadingException;
	/**
	 * 
	 * @param globalId
	 * @return
	 */
	GenericSchema findModel(String globalId);
	/**
	 * 
	 * @param schema
	 * @return
	 * @throws DataLoadingException
	 */
	boolean removeModel(GenericSchema schema) throws DataLoadingException;
}
