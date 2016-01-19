package com.prayer.facade.kernel;

import java.util.List;

import com.prayer.model.crucial.schema.FKReferencer;

/**
 * 用于处理Foreign Key的Referencer
 * 
 * @author Lang
 *
 */
public interface Referencer {
    /**
     * 根据表名和字段名获取所有的FKReferencer
     * 
     * @param table
     * @param column
     * @return
     */
    List<FKReferencer> getReferences(String table, String column);

    /**
     * 根据FKReferencer获取对应的DROP语句
     * 
     * @param referencer
     * @return
     */
    List<String> prepDropSql(List<FKReferencer> refs);

    /**
     * 根据FKReferencer获取对应的ADD语句（Recovery操作）
     * 
     * @param referencer
     * @return
     */
    List<String> prepRecoverySql(List<FKReferencer> refs);
}
