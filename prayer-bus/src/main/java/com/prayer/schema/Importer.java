package com.prayer.schema;

import com.prayer.exception.AbstractSchemaException;
import com.prayer.exception.AbstractSystemException;
import com.prayer.exception.AbstractTransactionException;
import com.prayer.exception.system.DataLoadingException;
import com.prayer.exception.system.SerializationException;
import com.prayer.kernel.model.GenericSchema;

/**
 * 导入接口流程
 * 
 * @author Lang
 * @see
 */
public interface Importer {
    /**
     * 1. 2XXXX Error，是否抛出系统异常，读写文件
     * 
     * @param filePath
     * @throws AbstractSystemException
     */
    void readSchema() throws AbstractSystemException;

    /**
     * 2. 1XXXX Error，验证JsonSchema文件
     * 
     * @throws AbstractSchemaException
     */
    void ensureSchema() throws AbstractSchemaException;

    /**
     * 3. 20004 Error，序列化JsonNode转换成节点类型
     * @throws SerializationException
     */
    GenericSchema transformSchema() throws SerializationException;
    /**
     * 4. 20005 Error, 导入数据异常
     * @param schema
     * @return
     * @throws DataLoadingException
     */
    boolean syncSchema(GenericSchema schema) throws AbstractTransactionException;
    /**
     * 刷新数据设置新的filePath
     * @param schema
     */
    void refreshSchema(String filePath);
    /**
     * 直接获取当前Importer执行的最新Schema
     * @return
     */
    GenericSchema getSchema();
    /**
     * 
     * @return
     */
    ExternalEnsurer getEnsurer();
}
