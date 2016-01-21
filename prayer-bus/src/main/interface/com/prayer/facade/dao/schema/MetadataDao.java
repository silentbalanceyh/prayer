package com.prayer.facade.dao.schema;

/**
 * 从数据库中读取元数据信息
 * @author Lang
 *
 */
public interface MetadataDao {
    /**
     * 根据Metadata获取File
     * @return
     */
    String getFile();
}
