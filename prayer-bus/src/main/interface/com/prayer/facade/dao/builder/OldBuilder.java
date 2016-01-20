package com.prayer.facade.dao.builder;

import com.prayer.base.exception.AbstractDatabaseException;
import com.prayer.constant.Symbol;
import com.prayer.facade.schema.Schema;

/**
 * 元数据构造表的接口
 * 
 * @author Lang
 *
 */
@Deprecated
public interface OldBuilder extends Symbol {
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
    boolean syncTable(Schema schema);

    /**
     * 删除数据表
     * 
     * @param schema
     */
    boolean purgeTable();

    /**
     * 获取执行步骤中的Exception
     * 
     * @return
     */
    AbstractDatabaseException getError();
}
