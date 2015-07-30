package com.prayer.bus;

import java.util.List;

import com.prayer.kernel.Record;
import com.prayer.model.bus.ServiceResult;

/**
 * Record的接口
 * @author Lang
 *
 */
public interface RecordService {
	/**
	 * 添加和更新Record的接口，参数为请求数据
	 * @param jsonContent
	 * @return
	 */
	ServiceResult<Record> saveRecord(String jsonContent);
	/**
	 * 移除Record的接口，参数为请求数据
	 * @param jsonContent
	 * @return
	 */
	ServiceResult<Record> removeRecord(String jsonContent);
	/**
	 * 查询Record的接口，参数为请求数据
	 * @param jsonContent
	 * @return
	 */
	ServiceResult<List<Record>> queryRecord(String jsonContent);
}
