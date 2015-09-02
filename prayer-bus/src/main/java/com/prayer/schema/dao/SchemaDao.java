package com.prayer.schema.dao;

import com.prayer.exception.AbstractTransactionException;
import com.prayer.exception.system.DataLoadingException;
import com.prayer.kernel.model.GenericSchema;

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
	GenericSchema create(GenericSchema schema) throws AbstractTransactionException;
	/**
	 * 
	 * @param schema
	 * @return
	 * @throws DataLoadingException
	 */
	GenericSchema synchronize(GenericSchema schema) throws AbstractTransactionException;
	/**
	 * 
	 * @param identifier
	 * @return
	 */
	GenericSchema getById(String identifier);
	/**
	 * 
	 * @param identifier
	 * @return
	 * @throws DataLoadingException
	 */
	boolean deleteById(String identifier) throws AbstractTransactionException;
}
