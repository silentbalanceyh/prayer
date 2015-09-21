package com.prayer.schema;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.prayer.exception.system.SerializationException;
import com.prayer.model.h2.FieldModel;
import com.prayer.model.h2.KeyModel;
import com.prayer.model.h2.MetaModel;

/**
 * 
 * @author Lang
 *
 */
public interface Serializer {
    /**
     * 将Json格式的__meta__转换成MetaModel对象
     * 
     * @param metaNode
     * @return
     * @throws SerializationException
     */
    MetaModel readMeta(JsonNode metaNode) throws SerializationException;

    /**
     * 将Json格式的__keys__节点转换成KeyModel对象List
     * 
     * @param keysNodes
     * @return
     * @throws SerializationException
     */
    List<KeyModel> readKeys(ArrayNode keysNodes) throws SerializationException;

    /**
     * 将Json格式的__fields__节点转换成FieldModel对象List
     * 
     * @param fieldsNodes
     * @return
     * @throws SerializationException
     */
    List<FieldModel> readFields(ArrayNode fieldsNodes) throws SerializationException;
}
