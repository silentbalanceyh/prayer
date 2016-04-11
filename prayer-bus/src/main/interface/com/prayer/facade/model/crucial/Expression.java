package com.prayer.facade.model.crucial;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;

/**
 * 表达式接口
 * @author Lang
 */
@VertexPoint(Interface.ENG_PUBLIC)
public interface Expression {
    /**
     * 生成最终的Sql表达式
     * @return
     */
    @VertexApi(Api.TOOL)
    String toSql();
    /**
     * 判断当前Expression是否复杂表达式
     * @return
     */
    @VertexApi(Api.TOOL)
    boolean isComplex();
}
