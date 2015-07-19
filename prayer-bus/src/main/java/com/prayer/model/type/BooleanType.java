package com.prayer.model.type;

import java.lang.reflect.Type;

import jodd.mutable.MutableBoolean;
import jodd.typeconverter.Convert;
import jodd.util.StringUtil;

import com.prayer.constant.Constants;
import com.prayer.kernel.Value;

/**
 * 类型：布尔类型
 *
 * @author Lang
 * @see
 */
public class BooleanType implements Value<Boolean> {
	// ~ Instance Fields =====================================
	/**
	 * 字符串缓冲池
	 */
	private final MutableBoolean value = new MutableBoolean();

	// ~ Constructors ========================================
	/**
	 * 构造一个默认的Boolean值
	 */
	public BooleanType() {
		this(false);
	}

	/**
	 * 根据传入的Boolean构造一个BooleanType
	 * 
	 * @param value
	 */
	public BooleanType(final Boolean value) {
		this.value.setValue(value);
	}

	/**
	 * 根据传入的String值构造一个BooleanType
	 * 
	 * @param value
	 */
	public BooleanType(final String value) {
		this.init(value);
	}

	// ~ Override Methods ====================================
	/**
	 * 获取boolean的值
	 */
	@Override
	public Boolean getValue() {
		return this.value.getValue();
	}

	/**
	 * 设置boolean的值
	 * 
	 * @param value
	 */
	@Override
	public void setValue(final Boolean value) {
		this.value.setValue(value);
	}

	/**
	 * 获取原始值的Type类型，返回java.lang.Boolean
	 * 
	 * @return
	 */
	@Override
	public Type getType() {
		return Boolean.class;
	}

	/**
	 * 获取DataType自定义的Lyra数据类型
	 */
	@Override
	public DataType getDataType() {
		return DataType.BOOLEAN;
	}

	/**
	 * 
	 */
	@Override
	public String literal() {
		return StringUtil.toLowerCase(String.valueOf(this.value.getValue()));
	}

	// ~ Methods =============================================
	/**
	 * set重载
	 * 
	 * @param value
	 */
	public void setValue(final String value) {
		this.init(value);
	}

	// ~ Private Methods =====================================
	/**
	 * 非Boolean类型初始化专用
	 **/
	private void init(final Object value) {
		this.value.setValue(Convert.toBooleanValue(value, Boolean.FALSE));
	}

	// ~ hashCode,equals,toString ============================
	/** **/
	@Override
	public String toString() {
		return "BooleanType [value=" + value + "]";
	}

	/** **/
	@Override
	public int hashCode() {
		final int prime = Constants.HASH_BASE;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	/** **/
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final BooleanType other = (BooleanType) obj;
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}
}
