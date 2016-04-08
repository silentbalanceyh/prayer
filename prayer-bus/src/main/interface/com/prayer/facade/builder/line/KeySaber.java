package com.prayer.facade.builder.line;

import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.model.crucial.schema.FKReferencer;
import com.prayer.model.meta.database.PEField;
import com.prayer.model.meta.database.PEKey;

/**
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.ENG_PUBLIC)
public interface KeySaber {
    /**
     * PK, UK
     * 
     * @param key
     * @return
     */
    @VertexApi(Api.TOOL)
    String buildLine(PEKey key);

    /**
     * PK, UK, FK
     * 
     * @param key
     * @param field
     * @return
     */

    @VertexApi(Api.TOOL)
    String buildLine(PEKey key, ConcurrentMap<String, PEField> fieldMap);

    /**
     * FK另外一种模式
     * 
     * @param name
     * @param columns
     * @param toTable
     * @param toColumn
     * @return
     */

    @VertexApi(Api.TOOL)
    String buildLine(FKReferencer ref);
}
