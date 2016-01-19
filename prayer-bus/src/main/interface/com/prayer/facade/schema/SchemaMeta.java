package com.prayer.facade.schema;

import java.io.Serializable;

import com.prayer.facade.entity.Entity;
import com.prayer.model.meta.database.PEField;
import com.prayer.model.meta.database.PEKey;
import com.prayer.model.meta.database.PEMeta;

/**
 * 
 * @author Lang
 *
 */
interface SchemaMeta {
    // ~ Schema Dao ===========================================
    /**
     * 读取Meta的Id -> PEMeta的UniqueId，防止和identifier方法混淆
     * 
     * @return
     */
    Serializable totem();

    /**
     * 设置Meta的Id -> PEMeta的UniqueId设置
     * 
     * @param totem
     * @return
     */
    Serializable totem(Serializable metaId);

    /**
     * 同步Keys和Fields中的关联数据
     * 
     * @param metaId
     */
    void synchronize(Serializable metaId);

    /**
     * 从Schema中抽取Meta
     * 
     * @return
     */
    Entity meta();

    /**
     * 从Schema中抽取List<PEKey>
     * 
     * @return
     */
    Entity[] keys();

    /**
     * 从Schema中抽取List<PEField>
     * 
     * @return
     */
    Entity[] fields();

    /**
     * 设置Schema中的Meta信息
     * 
     * @param meta
     */
    void meta(PEMeta meta);

    /**
     * 设置Schema中的Keys信息
     * 
     * @param keys
     */
    void keys(PEKey... keys);

    /**
     * 设置Schema中的Fields信息
     * 
     * @param fields
     */
    void fields(PEField... fields);
}
