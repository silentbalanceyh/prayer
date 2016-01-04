package com.prayer.facade.dao;

import com.prayer.base.exception.AbstractDatabaseException;
import com.prayer.constant.Symbol;
import com.prayer.model.kernel.GenericSchema;

/**
 * 元数据构造表的接口
 * 
 * @author Lang
 *
 */
public interface Builder extends Symbol{
    /**
     * 创建数据表
     * 
     * @param schema
     */
    boolean createTable();

    /**
     * 检查表是否存在
     * 
     * @return
     */
    boolean existTable();

    /**
     * 同步数据表
     * 
     * @param schema
     */
    boolean syncTable(GenericSchema schema);

    /**
     * 删除数据表
     * 
     * @param schema
     */
    boolean purgeTable();
    /**
     * 获取执行步骤中的Exception
     * @return
     */
    AbstractDatabaseException getError();
}
