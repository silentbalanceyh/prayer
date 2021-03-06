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
    AbstractSchemaException verifyConstraint(String table, String column);
    /**
     * 验证数据库中的某一列的数据类型是否匹配
     * @param table
     * @param column
     * @param expectedType
     * @return
     */
    AbstractSchemaException verifyColumnType(String table, String column, String expectedType);
    /**
     * 清除当前数据库中所有的测试数据：TST开始的表结构
     */
    // TODO: 临时方法，因为DEV、TEST、PROD环境还没区分开
    void purgeTestData();
}
