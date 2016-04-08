package com.prayer.facade.dao.schema;

import java.util.List;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.facade.schema.Schema;
import com.prayer.fantasm.exception.AbstractTransactionException;

/**
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.ENG_PUBLIC)
public interface SchemaDao {
    /**
     * 创建以及更新Schema信息
     * 
     * @param schema
     * @return
     * @throws AbstractTransactionException
     */
    @VertexApi(Api.WRITE)
    Schema save(Schema schema) throws AbstractTransactionException;

    /**
     * 根据identifier删除Schema信息
     * 
     * @param identifier
     * @return
     * @throws AbstractTransactionException
     */
    @VertexApi(Api.WRITE)
    boolean delete(String identifier) throws AbstractTransactionException;

    /**
     * 将元数据数据库中的Meta相关信息全部删除
     * 
     * @return
     * @throws AbstractTransactionException
     */
    @VertexApi(Api.WRITE)
    List<String> purge() throws AbstractTransactionException;

    /**
     * 根据identifier读取Schema信息
     * 
     * @param identifier
     * @return
     * @throws AbstractTransactionException
     */
    @VertexApi(Api.READ)
    Schema get(String identifier) throws AbstractTransactionException;
}
