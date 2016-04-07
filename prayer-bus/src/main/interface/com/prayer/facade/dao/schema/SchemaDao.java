package com.prayer.facade.dao.schema;

import java.util.List;

import com.prayer.facade.schema.Schema;
import com.prayer.fantasm.exception.AbstractTransactionException;

/**
 * 
 * @author Lang
 *
 */
public interface SchemaDao {
    /**
     * 创建以及更新Schema信息
     * 
     * @param schema
     * @return
     * @throws AbstractTransactionException
     */
    Schema save(Schema schema) throws AbstractTransactionException;

    /**
     * 根据identifier读取Schema信息
     * 
     * @param identifier
     * @return
     * @throws AbstractTransactionException
     */
    Schema get(String identifier) throws AbstractTransactionException;

    /**
     * 根据identifier删除Schema信息
     * 
     * @param identifier
     * @return
     * @throws AbstractTransactionException
     */
    boolean delete(String identifier) throws AbstractTransactionException;
    /**
     * 将元数据数据库中的Meta相关信息全部删除
     * @return
     * @throws AbstractTransactionException
     */
    List<String> purge() throws AbstractTransactionException;
}
