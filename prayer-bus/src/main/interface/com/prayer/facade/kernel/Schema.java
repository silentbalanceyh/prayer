package com.prayer.facade.kernel;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.prayer.model.meta.database.PEField;
import com.prayer.model.meta.database.PEKey;

/**
 * Schema节点的构造
 * 
 * @author Lang
 *
 */
public interface Schema extends Serializable {
    /**
     * 排序列出数据列，PK在最前边
     * 
     * @return
     */
    Set<String> getColumns();

    /**
     * 根据列名获取PEField
     * 
     * @param column
     * @return
     */
    PEField getColumn(String column);

    /**
     * 获取主键的Schema，有可能是多个主键
     * 
     * @return
     */
    List<PEField> getPrimaryKeys();

    /**
     * 获取外键的规范
     * 
     * @return
     */
    List<PEField> getForeignField();

    /**
     * 获取外键定义
     * 
     * @return
     */
    List<PEKey> getForeignKey();
}
