package com.prayer.schema.db;

import java.util.List;

import com.prayer.model.h2.FieldModel;

/**
 * 
 *
 * @author Lang
 * @see
 */
public interface FieldMapper extends H2TMapper<FieldModel, String> {
	/**
	 * 根据Field的metaId删除记录
	 * 
	 * @param metaId
	 * @return
	 */
	boolean deleteByMeta(String metaId);

	/**
	 * 根据Field的metaId获取Field记录
	 * 
	 * @param metaId
	 * @return
	 */
	List<FieldModel> selectByMeta(String metaId);
}
