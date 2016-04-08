package com.prayer.facade.util.digraph;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;

/**
 * 用于设置图的数据结构中的标记
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.ENG_PUBLIC)
public interface NodeData {
    /**
     * 获取节点的标签信息
     * 
     * @return
     */
    @VertexApi(Api.READ)
    String getKey();

    /**
     * 获取节点中的数据引用
     * 
     * @return
     */
    @VertexApi(Api.READ)
    <T> T getData();

    /**
     * 设置节点中的数据
     * 
     * @param data
     */
    @VertexApi(Api.WRITE)
    <T> void setData(T data);
}
