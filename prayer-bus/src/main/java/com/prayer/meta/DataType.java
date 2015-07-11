package com.prayer.meta;

/**
 * 数据类型的枚举量
 * 
 * @author Lang
 * @see
 */
public enum DataType { // NOPMD

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

	/** Lyra中支持的数据类型 **/
	private String dataType;

	/** 返回类型对应的Java类全名 **/
	private String className;

	/** Lyra中该类型对应的整数值 **/
	private int rawValue;

	DataType(final String dataType, final int rawValue) {
		this.dataType = dataType;
		this.rawValue = rawValue;
		this.className = "com.lyra.meta.data.type." + dataType;
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
	public static DataType fromString(final String storedValue) { // NOPMD
		DataType retValue;
		switch (storedValue) {
		case "BooleanType":
			retValue = DataType.BOOLEAN;
			break;
		case "IntType":
			retValue = DataType.INT;
			break;
		case "LongType":
			retValue = DataType.LONG;
			break;
		case "DecimalType":
			retValue = DataType.DECIMAL;
			break;
		case "DateType":
			retValue = DataType.DATE;
			break;
		case "JsonType":
			retValue = DataType.JSON;
			break;
		case "XmlType":
			retValue = DataType.XML;
			break;
		case "ScriptType":
			retValue = DataType.SCRIPT;
			break;
		case "BinaryType":
			retValue = DataType.BINARY;
			break;
		default:
			retValue = DataType.STRING;
			break;
		}
		return retValue;
	}

	/** 重写toString **/
	@Override
	public String toString() {
		return this.dataType;
	}
}