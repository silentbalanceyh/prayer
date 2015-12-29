package com.prayer.facade.schema;

import com.prayer.base.exception.AbstractSchemaException;

/**
 * 用于验证Schema的时候在Target数据库中操作数据的情况
 * 
 * @author Lang
 *
 */
public interface DataValidator {
    /**
     * 验证数据库中的Table是否存在
     * 
     * @param table
     * @return
     */
    AbstractSchemaException verifyTable(String table);

    /**
     * 验证数据库中的Table中某列是否存在
     * 
     * @param table
     * @param column
     * @return
     */
    AbstractSchemaException verifyColumn(String table, String column);

    /**
     * 验证数据库中的外键约束：必须Unique或者Primary Key
     * 
     * @param table
     * @param column
     * @return
     */
    AbstractSchemaException verifyFKConstraint(String table, String column);
}