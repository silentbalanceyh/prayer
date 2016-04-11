package com.prayer.facade.configuration.fetch;

import java.util.List;

import com.prayer.configuration.impl.AndEqer;
import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.facade.entity.Entity;
import com.prayer.fantasm.exception.AbstractException;

/**
 * 
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.ENG_PUBLIC)
public interface ConfigFetcher {
    /**
     * 根据传入的条件读取唯一的配置
     * 
     * @param filter
     * @return
     */
    @VertexApi(Api.READ)
    <T extends Entity> T inquiry(AndEqer whereClause) throws AbstractException;

    /**
     * 根据传入的条件读取一个List的配置
     * 
     * @param whereClause
     * @return
     */
    @VertexApi(Api.READ)
    <T extends Entity> List<T> inquiryList(AndEqer whereClause) throws AbstractException;

    /**
     * 不带传入的条件，直接读取所有List的配置
     * 
     * @return
     */
    <T extends Entity> List<T> inquiryList() throws AbstractException;
}
