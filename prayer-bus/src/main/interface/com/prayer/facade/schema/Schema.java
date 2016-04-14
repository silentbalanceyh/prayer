package com.prayer.facade.schema;

import java.io.Serializable;
import java.util.Set;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.constant.SystemEnum.MetaPolicy;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.model.meta.database.PEField;

/**
 * Schema节点的构造
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.ENG_PUBLIC)
public interface Schema extends Constraints, Metadata, Serializable {
    // ~ Identifier相关 =====================================
    /**
     * 获取所有字段集合
     * 
     * @return
     */
    @VertexApi(Api.READ)
    Set<String> fieldNames();

    /**
     * 读取Schema的全局Id：PEMeta -> GlobalId
     * 
     * @return
     */
    @VertexApi(Api.READ)
    String identifier();

    /**
     * 获取表信息
     * 
     * @return
     */
    @VertexApi(Api.READ)
    String getTable();

    /**
     * 获取主键的Policy
     * 
     * @return
     */
    @VertexApi(Api.READ)
    MetaPolicy getPolicy();

    /**
     * 根据列名获取PEField
     * 
     * @param column
     * @return
     */
    @VertexApi(Api.READ)
    PEField getColumn(String column);

    /**
     * 根据字段名获取PEField
     * 
     * @param field
     * @return
     */
    @VertexApi(Api.READ)
    PEField getField(String field);
}
