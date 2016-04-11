package com.prayer.facade.model.crucial;

import java.util.List;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.model.crucial.schema.FKReferencer;

/**
 * 用于处理Foreign Key的Referencer
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.ENG_PUBLIC)
public interface Referencer {
    /**
     * 根据表名和字段名获取所有的FKReferencer
     * 
     * @param table
     * @param column
     * @return
     */
    @VertexApi(Api.TOOL)
    List<FKReferencer> getReferences(String table, String column);

    /**
     * 根据FKReferencer获取对应的DROP语句
     * 
     * @param referencer
     * @return
     */

    @VertexApi(Api.TOOL)
    List<String> prepDropSql(List<FKReferencer> refs);

    /**
     * 根据FKReferencer获取对应的ADD语句（Recovery操作）
     * 
     * @param referencer
     * @return
     */

    @VertexApi(Api.TOOL)
    List<String> prepRecoverySql(List<FKReferencer> refs);
}
