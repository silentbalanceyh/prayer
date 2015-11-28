package com.prayer.model.type;

import java.lang.reflect.Type;
import java.math.BigDecimal;

import jodd.typeconverter.Convert;

import com.prayer.facade.kernel.Value;
import com.prayer.util.cv.Constants;

/**
 * 类型：浮点类型
 *
 * @author Lang
 * @see
 */
public class DecimalType implements Value<BigDecimal> {
    // ~ Instance Fields =====================================
    /** **/
    private BigDecimal value = DEFAULT;
    /** **/
    private static final BigDecimal DEFAULT = BigDecimal.valueOf(0.00);

    // ~ Constructors ========================================
    /** **/
    public DecimalType() {
        this(DEFAULT);
    }

    /** **/
    public DecimalType(final BigDecimal value) {
        this.value = value;
    }

    /** **/
    public DecimalType(final String value) {
        this.value = Convert.toBigDecimal(value, DEFAULT);
    }

    // ~ Override Methods ====================================
    /** **/
    @Override
    public BigDecimal getValue() {
        return this.value;
    }

    /** **/
    @Override
    public void setValue(final BigDecimal value) {
        this.value = value;
    }

    /** **/
    @Override
    public Type getType() {
        return BigDecimal.class;
    }

    /** **/
    @Override
    public DataType getDataType() {
        return DataType.DECIMAL;
    }
    /** **/
    @Override
    public String literal(){
        return this.value.toString();
    }

    // ~ Methods =============================================
    /** **/
    public void setValue(final String value) {
        this.value = Convert.toBigDecimal(value, DEFAULT);
    }

    // ~ hashCode,equals,toString ============================
    /** **/
    @Override
    public String toString() {
        return "DecimalType [value=" + value + "]";
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
        final DecimalType other = (DecimalType) obj;
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
