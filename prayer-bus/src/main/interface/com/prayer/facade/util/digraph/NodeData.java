package com.prayer.facade.util.digraph;
/**
 * 用于设置图的数据结构中的标记
 * @author Lang
 *
 */
public interface NodeData {
    /**
     * 获取节点的标签信息
     * @return
     */
    String getKey();
    /**
     * 获取节点中的数据引用
     * @return
     */
    <T> T getData();
}
