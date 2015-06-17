package com.prayer.db.conn;

import org.springframework.jdbc.core.RowMapper;

/**
 * Fixed jdbc interface for Lyra
 *
 * @author Lang
 * @package com.prayer.db.conn
 * @name JdbcContext
 * @class com.prayer.db.conn.JdbcContext
 * @date Dec 9, 2014 6:05:11 PM
 * @see This interface is different from DataContext
 */
public interface JdbcContext {
	/**
	 * Publish interface of jdbc template
	 *
	 * @param sql
	 * @param values
	 * @param types
	 */
	int update(final String sql, final Object[] values, final int[] types);

	/**
	 * Publish interface of jdbc template
	 *
	 * @param sql
	 * @param params
	 * @return
	 */
	int update(final String sql, final Object... params);

	/**
	 * Select data from database table
	 *
	 * @param sql
	 * @param params
	 * @return
	 */
	<T> T select(final String sql, final RowMapper<T> rowMapper,
			final Object... params);
}
