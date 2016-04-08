package com.prayer.facade.record;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.constant.SystemEnum.MetaPolicy;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.facade.kernel.Value;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.model.meta.database.PEField;

/**
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.ENG_PRIVATE)
interface RecordPrimaryKey {
    /**
     * 主键的Policy
     * 
     * @return
     */
    @VertexApi(Api.READ)
    MetaPolicy policy();

    /**
     * 生成主键键值对
     * 
     * @return
     */
    @VertexApi(Api.READ)
    ConcurrentMap<String, Value<?>> idKV() throws AbstractDatabaseException;

    /**
     * 当前记录的主键Schema
     * 
     * @return
     */
    @VertexApi(Api.READ)
    List<PEField> idschema();

    /**
     * seq name
     * 
     * @return
     */
    @VertexApi(Api.READ)
    String seqname();
}
