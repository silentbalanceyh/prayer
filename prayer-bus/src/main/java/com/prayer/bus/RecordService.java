package com.prayer.bus;

import java.util.List;

import com.prayer.kernel.Record;
import com.prayer.model.bus.ServiceResult;

import io.vertx.core.json.JsonObject;

/**
 * Record的接口
 * @author Lang
 *
 */
public interface RecordService {
	/**
	 * 添加和更新Record的接口，参数为请求数据
	 * @param jsonObject
	 * @return
	 */
	ServiceResult<Record> saveRecord(JsonObject jsonObject);
	/**
	 * 移除Record的接口，参数为请求数据
	 * @param jsonObject
	 * @return
	 */
	ServiceResult<Record> removeRecord(JsonObject jsonObject);
	/**
	 * 查询Record的接口，参数为请求数据
	 * @param jsonObject
	 * @return
	 */
	ServiceResult<List<Record>> queryRecord(JsonObject jsonObject);
}
