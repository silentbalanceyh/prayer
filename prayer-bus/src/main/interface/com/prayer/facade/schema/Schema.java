package com.prayer.facade.schema;

import java.io.Serializable;
import java.util.Set;

import com.prayer.constant.SystemEnum.MetaPolicy;
import com.prayer.model.meta.database.PEField;

/**
 * Schema节点的构造
 * 
 * @author Lang
 *
 */
public interface Schema extends SchemaBuilder, SchemaMeta, Serializable {
    // ~ Identifier相关 =====================================
    /**
     * 获取所有字段集合
     * 
     * @return
     */
    Set<String> fieldNames();

    /**
     * 读取Schema的全局Id：PEMeta -> GlobalId
     * 
     * @return
     */
    String identifier();

    /**
     * 获取表信息
     * 
     * @return
     */
    String getTable();

    /**
     * 获取主键的Policy
     * 
     * @return
     */
    MetaPolicy getPolicy();

    /**
     * 根据列名获取PEField
     * 
     * @param column
     * @return
     */
    PEField getColumn(String column);

    /**
     * 根据字段名获取PEField
     * 
     * @param field
     * @return
     */
    PEField getField(String field);
}
