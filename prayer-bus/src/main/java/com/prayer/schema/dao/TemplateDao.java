package com.prayer.schema.dao;

import java.io.Serializable;
import java.util.List;

import com.prayer.exception.AbstractTransactionException;

/**
 * 
 * @author Lang
 *
 * @param <T>
 */
@SuppressWarnings("unchecked")
public interface TemplateDao<T, ID extends Serializable> {	// NOPMD
	/**
	 * 
	 * @param entity
	 * @return
	 * @throws AbstractTransactionException
	 */
	List<T> insert(T... entity) throws AbstractTransactionException;
	/**
	 * 
	 * @param entity
	 * @return
	 * @throws AbstractTransactionException
	 */
	T update(T entity) throws AbstractTransactionException;

	/**
	 * 
	 * @param uniqueId
	 * @return
	 * @throws AbstractTransactionException
	 */
	boolean deleteById(ID uniqueId) throws AbstractTransactionException;

	/**
	 * 
	 * @param uniqueId
	 * @return
	 * @throws AbstractTransactionException
	 */
	T getById(ID uniqueId);

	/**
	 * 
	 * @return
	 */
	List<T> getAll();

	/**
	 * 
	 * @return
	 */
	boolean clear() throws AbstractTransactionException;
}
