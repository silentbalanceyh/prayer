package com.prayer.model.type;

import java.lang.reflect.Type;
import java.util.Date;

import jodd.typeconverter.Convert;

import com.prayer.constant.Constants;
import com.prayer.kernel.Value;

/**
 * 类型：时间/日期类型
 *
 * @author Lang
 * @see
 */
public class DateType implements Value<Date> {
	// ~ Instance Fields =====================================
	/** **/
	private Date value = new Date();

	// ~ Constructors ========================================
	/** **/
	public DateType() {
		this(new Date());
	}

	/** **/
	public DateType(final Long value) {
		this.value = new Date(value);
	}

	/** **/
	public DateType(final String value) {
		this.value = Convert.toDate(value, new Date());
	}

	/** **/
	public DateType(final Date value) {
		this.value = value;
	}

	// ~ Override Methods ====================================
	/** **/
	@Override
	public Date getValue() {
		return this.value;
	}

	/** **/
	@Override
	public void setValue(final Date value) {
		this.value = value;
	}

	/** **/
	@Override
	public Type getType() {
		return Date.class;
	}

	/** **/
	@Override
	public DataType getDataType() {
		return DataType.DATE;
	}
	/** **/
	@Override
	public String literal(){
		return this.value.toString();
	}

	// ~ Methods =============================================
	/** **/
	public void setValue(final Long value) {
		this.value = new Date(value);
	}

	/** **/
	public void setValue(final String value) {
		this.value = Convert.toDate(value, new Date());
	}

	// ~ hashCode,equals,toString ============================
	/** **/
	@Override
	public String toString() {
		return "DateType [value=" + value + "]";
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
		final DateType other = (DateType) obj;
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
