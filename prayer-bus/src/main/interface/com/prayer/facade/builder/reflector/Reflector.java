package com.prayer.facade.builder.reflector;

import java.util.List;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;

/**
 * 反向生成器
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.ENG_PUBLIC)
public interface Reflector {
    /**
     * 获取当前表中所有约束信息
     * 
     * @return
     */
    @VertexApi(Api.TOOL)
    List<String> getConstraints(String table);

    /**
     * 获取当前表中所有的列信息
     * 
     * @param table
     * @return
     */
    @VertexApi(Api.TOOL)
    List<String> getColumns(String table);
}
