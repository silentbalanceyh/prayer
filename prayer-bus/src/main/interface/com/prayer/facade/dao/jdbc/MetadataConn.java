package com.prayer.facade.dao.jdbc;

import java.io.InputStream;

import com.prayer.model.bus.Metadata;

/**
 * 元数据操作
 * 
 * @author Lang
 */
public interface MetadataConn {
    // ~ Constants =======================================
    // ~ Method ==========================================
    // region Metadata: Database Information
    /**
     * 获取当前数据库连接的元数据
     * 
     * @return
     */
    Metadata getMetadata();

    /**
     * 导入SQL文件
     * 
     * @param sqlFile
     * @return
     */
    boolean loadSqlFile(InputStream sqlFile);

    /**
     * H2 Database中创建对应的表结构
     * 
     * @return
     */
    boolean initMeta(InputStream sqlFile);

    // endregion
}
