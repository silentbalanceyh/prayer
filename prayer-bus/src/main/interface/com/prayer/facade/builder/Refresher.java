package com.prayer.facade.builder;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.facade.schema.Schema;
import com.prayer.fantasm.exception.AbstractDatabaseException;

/**
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.ENG_PUBLIC)
public interface Refresher {
    /**
     * 构造更新用的SQL语句
     * 
     * @param schema
     */
    @VertexApi(Api.TOOL)
    String buildAlterSQL(Schema schema) throws AbstractDatabaseException;
}
