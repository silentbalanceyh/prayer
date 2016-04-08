package com.prayer.facade.dao.schema;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.facade.schema.Schema;
import com.prayer.fantasm.exception.AbstractSchemaException;
import com.prayer.fantasm.exception.AbstractSystemException;

/**
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.ENG_PUBLIC)
public interface Importer {
    /**
     * 从路径中读取Schema
     * 
     * @param path
     * @return
     */
    @VertexApi(Api.READ)
    Schema read(String path) throws AbstractSchemaException,AbstractSystemException;
}
