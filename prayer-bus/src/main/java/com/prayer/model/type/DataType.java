package com.prayer.model.type;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 数据类型的枚举量
 * 
 * @author Lang
 * @see
 */
public enum DataType {

	BOOLEAN("BooleanType", 0B0_0000_0000_0000),

	INT("IntType", 0B0_0000_0000_0001),

	LONG("LongType", 0B0_0000_0000_0010),

	DECIMAL("DecimalType", 0B0_0000_0000_0100),

	DATE("DateType", 0B0_0000_0000_1000),

	STRING("StringType", 0B0_0000_0001_0000),

	JSON("JsonType", 0B0_0000_0010_0000),

	XML("XmlType", 0B0_0000_0100_0000),

	SCRIPT("ScriptType", 0B0_0000_1000_0000),

	BINARY("BinaryType", 0B0_0001_0000_0000);
	/** **/
	private static final ConcurrentMap<String, DataType> DT_MAP = new ConcurrentHashMap<>();

	static {
		DT_MAP.put("BooleanType", DataType.BOOLEAN);
		DT_MAP.put("IntType", DataType.INT);
		DT_MAP.put("LongType", DataType.LONG);
		DT_MAP.put("DecimalType", DataType.DECIMAL);
		DT_MAP.put("DateType", DataType.DATE);
		DT_MAP.put("JsonType", DataType.JSON);
		DT_MAP.put("XmlType", DataType.XML);
		DT_MAP.put("ScriptType", DataType.SCRIPT);
		DT_MAP.put("BinaryType", DataType.BINARY);
		DT_MAP.put("StringType", DataType.STRING);
	}

	/** Lyra中支持的数据类型 **/
	private String dataType;

	/** 返回类型对应的Java类全名 **/
	private String className;

	/** Lyra中该类型对应的整数值 **/
	private int rawValue;

	DataType(final String dataType, final int rawValue) {
		this.dataType = dataType;
		this.rawValue = rawValue;
		this.className = "com.prayer.model.type." + dataType;
	}

	/** 返回类型的原始值 **/
	public int value() {
		return this.rawValue;
	}

	/** 返回类型全名 **/
	public String getClassName() {
		return this.className;
	}

	/** 从String转换的类 **/
	public static DataType fromString(final String storedValue) {
		return DT_MAP.get(storedValue);
	}

	/** 重写toString **/
	@Override
	public String toString() {
		return this.dataType;
	}
}