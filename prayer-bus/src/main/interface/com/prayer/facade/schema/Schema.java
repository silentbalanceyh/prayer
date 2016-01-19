package com.prayer.facade.schema;

import java.io.Serializable;

/**
 * Schema节点的构造
 * 
 * @author Lang
 *
 */
public interface Schema extends SchemaBuilder, SchemaMeta, Serializable {
    // ~ Identifier相关 =====================================
    /**
     * 读取Schema的全局Id：PEMeta -> GlobalId
     * 
     * @return
     */
    String identifier();

    /**
     * 设置Schema的全局Id：PEMeta -> GlobalId
     * 
     * @param identifier
     * @return
     */
    void identifier(String identifier);
}
