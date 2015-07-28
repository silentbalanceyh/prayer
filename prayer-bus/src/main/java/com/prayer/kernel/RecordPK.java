package com.prayer.kernel;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.SystemEnum.MetaPolicy;
import com.prayer.exception.AbstractMetadataException;
import com.prayer.model.h2.FieldModel;
/**
 * 
 * @author Lang
 *
 */
interface RecordPK {
	/**
	 * 主键的Policy
	 * 
	 * @return
	 */
	MetaPolicy policy();
	/**
	 * 生成主键键值对
	 * @return
	 */
	ConcurrentMap<String,Value<?>> idKV() throws AbstractMetadataException;
	/**
	 * 当前记录的主键Schema
	 * @return
	 */
	List<FieldModel> idschema();
}
