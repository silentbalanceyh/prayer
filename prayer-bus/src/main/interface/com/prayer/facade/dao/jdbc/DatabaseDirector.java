package com.prayer.facade.dao.jdbc;

import com.prayer.base.exception.AbstractDatabaseException;
import com.prayer.model.bus.Metadata;

/**
 * 数据库直连接口
 * 
 * @author Lang
 *
 */
public interface DatabaseDirector {
    /**
     * 根据参数获取元数据信息
     * @param url
     * @param username
     * @param password
     * @return
     */
    Metadata getMetadata(final String url, final String username, final String password) throws AbstractDatabaseException;
}
