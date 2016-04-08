package com.prayer.facade.schema;

import java.io.Serializable;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.model.meta.database.PEField;
import com.prayer.model.meta.database.PEKey;
import com.prayer.model.meta.database.PEMeta;

/**
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.ENG_PRIVATE)
interface Metadata {
    // ~ Schema Dao ===========================================
    /**
     * 读取Meta的Id -> PEMeta的UniqueId，防止和identifier方法混淆
     * 
     * @return
     */
    @VertexApi(Api.READ)
    Serializable totem();

    /**
     * 设置Meta的Id -> PEMeta的UniqueId设置
     * 
     * @param totem
     * @return
     */
    @VertexApi(Api.WRITE)
    Serializable totem(Serializable metaId);

    /**
     * 同步Keys和Fields中的关联数据
     * 
     * @param metaId
     */
    @VertexApi(Api.WRITE)
    void synchronize(Serializable metaId);

    /**
     * 从Schema中抽取Meta
     * 
     * @return
     */

    @VertexApi(Api.READ)
    PEMeta meta();

    /**
     * 从Schema中抽取List<PEKey>
     * 
     * @return
     */
    @VertexApi(Api.READ)
    PEKey[] keys();

    /**
     * 从Schema中抽取List<PEField>
     * 
     * @return
     */
    @VertexApi(Api.READ)
    PEField[] fields();

    /**
     * 设置Schema中的Meta信息
     * 
     * @param meta
     */
    @VertexApi(Api.WRITE)
    void meta(PEMeta meta);

    /**
     * 设置Schema中的Keys信息
     * 
     * @param keys
     */
    @VertexApi(Api.WRITE)
    void keys(PEKey... keys);

    /**
     * 设置Schema中的Fields信息
     * 
     * @param fields
     */
    @VertexApi(Api.WRITE)
    void fields(PEField... fields);
}
