package com.test.db.mybatis;

import com.prayer.constant.SystemEnum.FieldDatetime;
import com.prayer.constant.SystemEnum.KeyCategory;
import com.prayer.constant.SystemEnum.MetaCategory;
import com.prayer.constant.SystemEnum.MetaMapping;
import com.prayer.constant.SystemEnum.MetaPolicy;
import com.prayer.model.type.DataType;
/**
 * 
 * @author Lang
 *
 */
public interface EnumArray {	// NOPMD
	/** **/
	KeyCategory[] KEY_CATEGORIES = new KeyCategory[] {
			KeyCategory.ForeignKey, KeyCategory.PrimaryKey,
			KeyCategory.UniqueKey };
	/** **/
	DataType[] DATA_TYPES = new DataType[] {
			DataType.BOOLEAN, DataType.INT, DataType.LONG, DataType.DECIMAL,
			DataType.DATE, DataType.STRING, DataType.JSON, DataType.XML,
			DataType.SCRIPT, DataType.BINARY };
	/** **/
	FieldDatetime[] FIELD_DATETIME = new FieldDatetime[]{
			FieldDatetime.STRING,
			FieldDatetime.TIMER
	};
	/** **/
	MetaCategory[] META_CATEGORIES = new MetaCategory[]{
			MetaCategory.ENTITY,
			MetaCategory.RELATION
	};
	/** **/
	MetaMapping[] META_MAPPINGS = new MetaMapping[]{
			MetaMapping.COMBINATED,
			MetaMapping.DIRECT,
			MetaMapping.PARTIAL
	};
	/** **/
	MetaPolicy[] META_POLICIES = new MetaPolicy[]{
			MetaPolicy.ASSIGNED,
			MetaPolicy.INCREMENT,
			MetaPolicy.GUID,
			MetaPolicy.COLLECTION
	};
}
