package com.prayer.facade.builder.reflector;

import java.util.List;

/**
 * 反向生成器
 * 
 * @author Lang
 *
 */
public interface Reflector {
    /**
     * 获取当前表中所有约束信息
     * 
     * @return
     */
    List<String> getConstraints(String table);

    /**
     * 获取当前表中所有的列信息
     * 
     * @param table
     * @return
     */
    List<String> getColumns(String table);
}
