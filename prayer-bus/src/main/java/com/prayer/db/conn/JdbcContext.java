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
     * 单列读取
     * 
     * @param sql
     * @param column
     * @return
     */
    List<String> select(String sql, String column);

    // ~ 带参数语句
    // ==================================================================
    /**
     * 带参数执行
     * @param sql
     * @param values
     * @return
     */
    int execute(String sql, List<Value<?>> values);
    /**
     * 带参数的聚集函数
     * @param sql
     * @param values
     * @return
     */
    Long count(String sql);
    /**
     * 带参数查询
     * 
     * @param sql
     * @param values
     * @param columns
     * @return
     */
    List<ConcurrentMap<String, Value<?>>> select(String sql, List<Value<?>> values, ConcurrentMap<String,DataType> columnMap, String... columns);
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
