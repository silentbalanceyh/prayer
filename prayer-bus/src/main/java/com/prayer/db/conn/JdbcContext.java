package com.prayer.db.conn;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.prayer.kernel.Value;
import com.prayer.model.type.DataType;

/**
 * JDBC Interface
 * 
 * @author Lang
 * @see
 */
public interface JdbcContext {
	/**
	 * 直接执行SQL语句
	 * 
	 * @param sql
	 * @return
	 */
	int execute(String sql);

	/**
	 * 聚集函数COUNT
	 * 
	 * @param sql
	 * @return
	 */
	Long count(String sql);

	/**
	 * 读取所有Columns中的数据
	 * 
	 * @param sql
	 * @return
	 */
	List<ConcurrentMap<String, String>> select(String sql, String... columns);

	/**
	 * 单列读取
	 * 
	 * @param sql
	 * @param column
	 * @return
	 */
	List<String> select(String sql, String column);

	// ~ 插入语句 ==================================================================
	/**
	 * 返回插入过后的主键值，主要针对Increment的Policy才会使用后边的方法
	 * 
	 * @param sql
	 * @param values
	 * @param isRetKey
	 * @param retType
	 * @return
	 */
	Value<?> insert(String sql, List<Value<?>> values, boolean isRetKey, DataType retType);
}
