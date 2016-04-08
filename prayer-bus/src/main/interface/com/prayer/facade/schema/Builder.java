package com.prayer.facade.schema;

import java.util.List;
import java.util.Set;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.model.meta.database.PEField;
import com.prayer.model.meta.database.PEKey;

/**
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.ENG_PRIVATE)
interface Builder {
    // ~ OldBuilder Interface ====================================
    /**
     * 排序列出数据列，PK在最前边
     * 
     * @return
     */
    @VertexApi(Api.READ)
    Set<String> getColumns();

    /**
     * 获取主键的Schema，有可能是多个主键
     * 
     * @return
     */
    @VertexApi(Api.READ)
    List<PEField> getPrimaryKeys();

    /**
     * 获取外键的规范
     * 
     * @return
     */
    @VertexApi(Api.READ)
    List<PEField> getForeignField();

    /**
     * 获取外键定义
     * 
     * @return
     */
    @VertexApi(Api.READ)
    List<PEKey> getForeignKey();
}
