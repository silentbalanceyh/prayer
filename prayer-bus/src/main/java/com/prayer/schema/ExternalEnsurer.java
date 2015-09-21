package com.prayer.schema;

import com.fasterxml.jackson.databind.JsonNode;
import com.prayer.exception.AbstractSchemaException;

/**
 * Schema的验证接口
 * @author Lang
 * @see
 */
public interface ExternalEnsurer {
    /**
     * 从JsonMap中导入数据
     * @param rootNode
     */
    void refreshData(JsonNode rootNode);
    /**
     * 验证Schema的流程
     * @return
     */
    boolean validate();
    /**
     * 担保组件处理完成过后会得到一个完整合法的Schema对象
     * @return
     */
    JsonNode getRaw();
    /**
     * 获取Schema的Error
     * @return
     */
    AbstractSchemaException getError();
}
