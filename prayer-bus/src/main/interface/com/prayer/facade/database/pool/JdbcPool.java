package com.prayer.facade.database.pool;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcOperations;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.facade.resource.Inceptor;

/**
 * 连接池的接口，默认使用BoneCP连接池
 * @author Lang
 *
 */
@VertexPoint(Interface.ENG_PUBLIC)
public interface JdbcPool {
    /**
     * 使用Spring中的Executor，可初始化JdbcTemplate
     * @return
     */
    @VertexApi(Api.READ)
    JdbcOperations getExecutor();
    /**
     * 返回连接池数据源信息
     * @return
     */
    @VertexApi(Api.READ)
    DataSource getDataSource();
    /**
     * 获取JDBC数据库类型
     * @return
     */
    @VertexApi(Api.READ)
    String getCategory();
    /**
     * 获取属性加载器，可以从属性加载器中获取
     * @return
     */
    @VertexApi(Api.READ)
    Inceptor getLoader();
}
