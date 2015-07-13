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
	 * 直接执行SQL语句
	 * @param sql
	 * @return
	 */
	int execute(String sql);
	/**
	 * 聚集函数COUNT
	 * @param sql
	 * @return
	 */
	Long count(String sql);
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
