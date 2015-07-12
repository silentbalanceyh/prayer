package com.prayer.db.conn;

import org.springframework.jdbc.core.RowMapper;

/**
 * JDBC Interface
 * 
 * @author Lang
 * @see
 */
public interface JdbcContext {
	/**
	 * Publish interface of jdbc template
	 *
	 * @param sql
	 * @param values
	 * @param types
	 */
	int update(String sql, Object[] values, int... types); 

	/**
	 * Publish interface of jdbc template
	 *
	 * @param sql
	 * @param params
	 * @return
	 */
	int update(String sql, Object... params);

	/**
	 * Select data from database table
	 *
	 * @param sql
	 * @param params
	 * @return
	 */
	<T> T select(String sql, RowMapper<T> rowMapper, Object... params);
}
