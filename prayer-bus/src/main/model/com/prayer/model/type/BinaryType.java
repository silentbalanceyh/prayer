package com.prayer.model.type;

import java.lang.reflect.Type;
import java.util.Arrays;

import com.prayer.constant.Resources;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.kernel.Value;

/**
 * 字节数组
 *
 * @author Lang
 * @see
 */
public class BinaryType implements Value<byte[]> {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** **/
    private byte[] value = new byte[1024];

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public BinaryType() {
        this(new byte[1024]);
    }

    /** **/
    public BinaryType(final byte[] value) {
        this.value = Arrays.copyOf(value, value.length);
    }

    /** **/
    public BinaryType(final int length) {
        this.value = new byte[length];
    }

    /** **/
    public BinaryType(final String value) {
        this.value = value.getBytes();
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public byte[] getValue() {
        return Arrays.copyOf(this.value, this.value.length);
    }

    /** **/
    @Override
    public void setValue(final byte[] value) {
        this.value = Arrays.copyOf(value, value.length);
    }

    /** **/
    @Override
    public Type getType() {
        return this.value.getClass();
    }

    /** **/
    @Override
    public DataType getDataType() {
        return DataType.BINARY;
    }
    /** **/
    @Override
    public String literal(){
        return new String(this.value,Resources.SYS_ENCODING);
    }

    // ~ Methods =============================================
    /** **/
    public void setValue(final String value) {
        this.value = value.getBytes();
    }

    // ~ Private Methods =====================================
    // ~ hashCode,equals,toString ============================
    /** **/
    @Override
    public String toString() {
        return "BinaryType [value=" + Arrays.toString(value) + "]";
    }

    /** **/
    @Override
    public int hashCode() {
        final int prime = Constants.HASH_BASE; 
        int result = 1;
        result = prime * result + Arrays.hashCode(value);
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
        final BinaryType other = (BinaryType) obj;
        if (!Arrays.equals(value, other.value)) { // NOPMD
            return false; 
        }
        return true;
    }
}
