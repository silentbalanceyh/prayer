package com.prayer.meta.schema;

import java.util.concurrent.ConcurrentMap;

import com.fasterxml.jackson.databind.JsonNode;
import com.prayer.exception.AbstractSchemaException;
import com.prayer.mod.sys.GenericSchema;

/**
 * Schema的验证接口
 * @author Lang
 * @see
 */
public interface Ensurer {
	/**
	 * 从JsonMap中导入数据
	 * @param jsonMap
	 */
	void refreshData(ConcurrentMap<String,JsonNode> jsonMap);
	/**
	 * 验证Schema的流程
	 * @return
	 */
	boolean validate();
	/**
	 * 担保组件处理完成过后会得到一个完整合法的Schema对象
	 * @return
	 */
	GenericSchema getResult();
	/**
	 * 获取Schema的Error
	 * @return
	 */
	AbstractSchemaException getError();
}
