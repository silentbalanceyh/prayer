package com.prayer.model.type;

import java.lang.reflect.Type;

import jodd.mutable.MutableLong;
import jodd.typeconverter.Convert;

import com.prayer.facade.constant.Constants;
import com.prayer.facade.kernel.Value;

/**
 * 类型：长整类型
 *
 * @author Lang
 * @see
 */
public class LongType implements Value<Long> {
    // ~ Instance Fields =====================================
    /** **/
    private final MutableLong value = new MutableLong(0L);

    // ~ Constructors ========================================
    /** **/
    public LongType() {
        this(0L);
    }

    /** **/
    public LongType(final Integer value) {
        this.init(value);
    }

    /** **/
    public LongType(final Long value) {
        this.value.setValue(value);
    }

    /** **/
    public LongType(final String value) {
        this.init(value);
    }

    // ~ Override Methods ====================================
    /** **/
    @Override
    public Long getValue() {
        return this.value.getValue();
    }

    /** **/
    @Override
    public void setValue(final Long value) {
        this.value.setValue(value);
    }

    /** **/
    @Override
    public Type getType() {
        return Long.class;
    }

    /** **/
    @Override
    public DataType getDataType() {
        return DataType.LONG;
    }
    /** **/
    @Override
    public String literal(){
        return String.valueOf(this.value.getValue());
    }

    // ~ Methods =============================================
    /** **/
    public void setValue(final Integer value) {
        this.init(value);
    }

    /** **/
    public void setValue(final String value) {
        this.init(value);
    }

    // ~ Private Methods =====================================
    /**
     * 
     * @param value
     */
    private void init(final Object value) {
        this.value.setValue(Convert.toLongValue(value, 0L));
    }

    // ~ hashCode,equals,toString ============================
    /** **/
    @Override
    public String toString() {
        return "LongType [value=" + value + "]";
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
        if (this == obj){
            return true;    
        }
        if (obj == null){
            return false;    
        }
        if (getClass() != obj.getClass()){
            return false;    
        }
        final LongType other = (LongType) obj;
        if (value == null) {
            if (other.value != null){
                return false;    
            }
        } else if (!value.equals(other.value)){
            return false;    
        }
        return true;
    }
}
