package com.prayer.facade.business.instantor.schema;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.facade.schema.Schema;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.fantasm.exception.AbstractException;

/**
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.DIRECT)
public interface EnvInstantor {
    /**
     * 构造Schema的容器环境，将所有系统需要使用的Schema读取到Cache中
     * @return
     * @throws AbstractException
     */
    @VertexApi(Api.READ)
    boolean buildMilieu() throws AbstractException;
    /**
     * 
     * @param identifier
     * @return
     * @throws AbstractDatabaseException
     */
    Schema get(String identifier) throws AbstractDatabaseException;
}
