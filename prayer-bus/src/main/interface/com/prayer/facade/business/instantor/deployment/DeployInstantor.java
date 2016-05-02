package com.prayer.facade.business.instantor.deployment;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.fantasm.exception.AbstractException;

/**
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.DIRECT)
public interface DeployInstantor {
    /**
     * 1.执行元数据初始化工作，针对SQL模式（H2）生成基础表结构
     * 
     * @param sqlfile
     * @return
     */
    @VertexApi(Api.WRITE)
    boolean initialize(String sqlfile) throws AbstractException;

    /**
     * 2.执行Deploy操作，将目录下边所有的元数据导入到Metadata中，并且在数据库中生成对应Database表
     * 
     * @param folder
     * @param schema 是否处理Schema的元数据
     * @return
     */
    @VertexApi(Api.WRITE)
    boolean manoeuvre(String folder,boolean schema) throws AbstractException;
    /**
     * 3.执行Purge操作，必须删除SQL数据库中的表信息
     * @param schema 是否处理Schema的元数据
     * @return
     */
    @VertexApi(Api.WRITE)
    boolean purge(boolean schema) throws AbstractException;
}
