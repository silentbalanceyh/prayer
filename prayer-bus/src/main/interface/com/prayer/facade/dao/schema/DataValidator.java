package com.prayer.facade.dao.schema;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.fantasm.exception.AbstractSchemaException;

/**
 * 用于验证Schema的时候在Target数据库中操作数据的情况
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.ENG_PUBLIC)
public interface DataValidator {
    /**
     * 验证数据库中的Table是否存在
     * 
     * @param table
     * @return
     */
    @VertexApi(Api.TOOL)
    AbstractSchemaException verifyTable(String table);

    /**
     * 验证数据库中的Table中某列是否存在
     * 
     * @param table
     * @param column
     * @return
     */
    @VertexApi(Api.TOOL)
    AbstractSchemaException verifyColumn(String table, String column);

    /**
     * 验证数据库中的外键约束：必须Unique或者Primary Key
     * 
     * @param table
     * @param column
     * @return
     */
    @VertexApi(Api.TOOL)
    AbstractSchemaException verifyConstraint(String table, String column);

    /**
     * 验证数据库中的某一列的数据类型是否匹配
     * 
     * @param table
     * @param column
     * @param expectedType
     * @return
     */
    @VertexApi(Api.TOOL)
    AbstractSchemaException verifyColumnType(String table, String column, String expectedType);
}
