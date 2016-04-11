package com.prayer.model.type;

import java.lang.reflect.Type;

import com.prayer.exception.system.TypeInitException;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.model.crucial.Value;
import com.prayer.fantasm.exception.AbstractSystemException;

/**
 * ee
 */
public class StringType implements Value<String> {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /**
     * 字符串缓冲池
     */
    protected StringBuilder value = new StringBuilder(16); // NOPMD
    /**
     * Runtime异常
     */
    private transient TypeInitException exp = null; // NOPMD

    // ~ Constructors ========================================
    /**
     * 构造一个新的空字符串
     */
    public StringType() {
        this("");
    }

    /**
     * 清空缓冲池，重新构造
     * 
     * @param value
     */
    public StringType(final String value) {
        this.init(value);
    }

    // ~ Override Methods ====================================
    /**
     * 获取字符串的值
     */
    @Override
    public String getValue() {
        String ret = null;
        if (null != this.value) {
            ret = this.value.toString();
        }
        return ret;
    }

    /**
     * 重新设置字符串的值，防止新建对象
     */
    @Override
    public void setValue(final String value) {
        this.init(value);
    }

    /**
     * 获取原始值的java类型，返回java.lang.String
     */
    @Override
    public Type getType() {
        return String.class;
    }

    /**
     * 获取DataType自定义的Lyra数据类型
     */
    @Override
    public DataType getDataType() {
        return DataType.STRING;
    }

    /**
     * 获取字面量
     */
    @Override
    public String literal() {
        return null == this.value ? "null" : this.value.toString();
    }

    // ~ Methods =============================================
    /** **/
    public AbstractSystemException getError() {
        return this.exp;
    }

    // ~ Private Methods =====================================
    /**
     * 内部初始化函数
     * 
     * @param value
     */
    private void init(final String value) {
        if (null == value) {
            this.value = null; // NOPMD
        } else {
            this.value.delete(0, this.value.length());
            this.value.append(value);
        }
    }

    // ~ hashCode,equals,toString ============================
    /** **/
    @Override
    public String toString() {
        return "StringType [value=" + value + "]";
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
        final StringType other = (StringType) obj;
        if (value == null) {
            if (other.value != null) {
                return false;
            }
        } else if (other.value == null) {
            if (this.value != null) {
                return false;
            }
        } else if (!value.toString().equals(other.value.toString())) {
            return false;
        }
        return true;
    }
}