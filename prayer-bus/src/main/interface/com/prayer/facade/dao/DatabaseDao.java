package com.prayer.facade.dao;

import com.prayer.util.digraph.Edges;

/**
 * 从数据库中读取元数据信息
 * @author Lang
 *
 */
public interface DatabaseDao {
    /**
     * 根据Metadata获取File
     * @return
     */
    String getFile();
    /**
     * 从数据库中读取关系表
     * @return
     */
    Edges getRelations();
}
