package com.prayer.db.conn;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

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
	 * 读取所有Columns中的数据
	 * @param sql
	 * @return
	 */
	List<ConcurrentMap<String,String>> select(String sql, String... columns);
	/**
	 * 单列读取
	 * @param sql
	 * @param column
	 * @return
	 */
	List<String> select(String sql, String column);
}
