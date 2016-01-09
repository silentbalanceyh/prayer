package com.prayer.facade.pool;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcOperations;

import com.prayer.util.io.PropertyKit;

/**
 * 连接池的接口，默认使用BoneCP连接池
 * @author Lang
 *
 */
public interface JdbcPool {
    /**
     * 使用Spring中的Executor，可初始化JdbcTemplate
     * @return
     */
    JdbcOperations getExecutor();
    /**
     * 返回连接池数据源信息
     * @return
     */
    DataSource getDataSource();
    /**
     * 获取JDBC数据库类型
     * @return
     */
    String getCategory();
    /**
     * 获取属性加载器，可以从属性加载器中获取
     * @return
     */
    PropertyKit getLoader();
}
