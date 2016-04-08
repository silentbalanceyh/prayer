package com.prayer.facade.dao;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.util.digraph.Edges;

/**
 * 从数据库中读取元数据信息
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.ENG_PUBLIC)
public interface DatabaseDao {
    /**
     * 根据Metadata获取File
     * 
     * @return
     */
    @VertexApi(Api.READ)
    String getFile();

    /**
     * 从数据库中读取关系表
     * 
     * @return
     */
    @VertexApi(Api.READ)
    Edges getRelations();
}
