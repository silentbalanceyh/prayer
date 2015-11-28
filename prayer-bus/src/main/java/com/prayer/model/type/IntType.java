package com.prayer.model.type;

import java.lang.reflect.Type;

import jodd.mutable.MutableInteger;
import jodd.typeconverter.Convert;

import com.prayer.kernel.i.Value;
import com.prayer.util.cv.Constants;

/**
 * 类型：整数类型
 *
 * @author Lang
 * @see
 */
public class IntType implements Value<Integer> {
    // ~ Instance Fields =====================================
    /**
     * 数值
     */
    private final MutableInteger value = new MutableInteger(0);

    // ~ Constructors ========================================
    /**
     * 构造一个默认的Int值
     */
    public IntType() {
        this(0);
    }

    /**
     * 根据传入的Integer构造一个IntType
     * 
     * @param value
     */
    public IntType(final Integer value) {
        this.value.setValue(value);
    }

    /**
     * 根据传入的String值构造一个BooleanType
     * 
     * @param value
     */
    public IntType(final String value) {
        this.init(value);
    }

    /**
     * 根据传入的Long值构造一个IntType
     * 
     * @param value
     */
    public IntType(final Long value) {
        this.init(value);
    }

    // ~ Override Methods ====================================
    /**
     * 获取Integer的值
     */
    @Override
    public Integer getValue() {
        return this.value.getValue();
    }

    /**
     * 设置Integer的值
     */
    @Override
    public void setValue(final Integer value) {
        this.value.setValue(value);
    }

    /**
     * 获取原始值的Type类型，返回java.lang.Boolean
     */
    @Override
    public Type getType() {
        return Integer.class;
    }

    /**
     * 获取DataType自定义的Lyra数据类型
     */
    @Override
    public DataType getDataType() {
        return DataType.INT;
    }
    /**
     * 
     */
    @Override
    public String literal(){
        return String.valueOf(this.value.getValue());
    }

    // ~ Methods =============================================
    /**
     * set重载
     * 
     * @param value
     */
    public void setValue(final Long value) {
        this.init(value);
    }

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
     * 
     * @param value
     */
    private void init(final Object value) {
        this.value.setValue(Convert.toIntValue(value, 0));
    }

    // ~ hashCode,equals,toString ============================
    /** **/
    @Override
    public String toString() {
        return "IntType [value=" + value + "]";
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
        final IntType other = (IntType) obj;
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
