package com.prayer.facade.pool;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.facade.kernel.Value;
import com.prayer.model.business.Metadata;
import com.prayer.model.type.DataType;

/**
 * JDBC Interface
 * 
 * @author Lang
 * @see
 */
@VertexPoint(Interface.ENG_PUBLIC)
public interface JdbcConnection {

    /**
     * 单列读取【仅支持String类型】
     * 
     * @param sql
     * @param column
     * @return
     */
    @VertexApi(Api.READ)
    List<String> select(String sql, String column);

    /**
     * 多列读取【仅支持String类型】
     * 
     * @param sql
     * @param columns
     * @return
     */
    @VertexApi(Api.READ)
    List<ConcurrentMap<String, String>> select(String sql, String[] columns);

    /**
     * 带参数的聚集函数
     * 
     * @param sql
     * @param values
     * @return
     */
    @VertexApi(Api.READ)
    Long count(String sql);

    /**
     * 带参数查询
     * 
     * @param sql
     * @param values
     * @param columns
     * @return
     */
    @VertexApi(Api.READ)
    List<ConcurrentMap<String, Value<?>>> select(String sql, List<Value<?>> values,
            ConcurrentMap<String, DataType> columnMap, String... columns);

    /**
     * 返回插入过后的主键值，主要针对Increment的Policy才会使用后边的方法
     * 
     * @param sql
     * @param values
     * @param isRetKey
     * @param retType
     * @return
     */
    @VertexApi(Api.READ)
    Value<?> insert(String sql, List<Value<?>> values, boolean isRetKey, DataType retType);

    /**
     * 根据数据库类型读取数据库的Metadata
     * 
     * @param category
     * @return
     */
    @VertexApi(Api.READ)
    Metadata getMetadata(String category);
    /**
     * 带参数执行
     * 
     * @param sql
     * @param values
     * @return
     */
    @VertexApi(Api.WRITE)
    int execute(String sql, List<Value<?>> values);
    /**
     * oracle批量执行SQL语句
     * 
     * @param sql
     * @param values
     * @return
     */
    @VertexApi(Api.WRITE)
    int executeBatch(String sql);

    /**
     * 执行SQL语句
     * 
     * @param sqlFile
     * @return
     */
    @VertexApi(Api.WRITE)
    boolean executeSql(InputStream sqlFile);

}
