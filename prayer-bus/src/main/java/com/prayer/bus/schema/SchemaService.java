package com.prayer.bus.schema;

import com.prayer.exception.system.DataLoadingException;
import com.prayer.mod.meta.GenericSchema;

/**
 * 
 * @author Lang
 *
 */
public interface SchemaService {
	/**
	 * 
	 * @param schema
	 * @return
	 * @throws DataLoadingException
	 */
	GenericSchema buildModel(GenericSchema schema) throws DataLoadingException;

	/**
	 * 
	 * @param namespace
	 * @param name
	 * @return
	 * @throws DataLoadingException
	 */
	GenericSchema findModel(String namespace, String name);
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
