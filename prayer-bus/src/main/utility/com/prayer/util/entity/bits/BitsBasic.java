package com.prayer.util.entity.bits;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.facade.constant.Constants;

/**
 * 基础类型转换，字节顺序：Big-Endian大端运算
 * 
 * @author Lang
 *
 */
public final class BitsBasic {
    // ~ Static Fields =======================================
    /** Length **/
    private static ConcurrentMap<Class<?>, Integer> LEN_MAP = new ConcurrentHashMap<>();

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    /** 字节长度信息 **/
    static {
        LEN_MAP.put(boolean.class, 1);
        LEN_MAP.put(Boolean.class, 1);
        LEN_MAP.put(short.class, 2);
        LEN_MAP.put(Short.class, 2);
        LEN_MAP.put(char.class, 2);
        LEN_MAP.put(Character.class, 2);
        LEN_MAP.put(int.class, 4);
        LEN_MAP.put(Integer.class, 4);
        LEN_MAP.put(float.class, 4);
        LEN_MAP.put(Float.class, 4);
        LEN_MAP.put(long.class, 8);
        LEN_MAP.put(Long.class, 8);
        LEN_MAP.put(double.class, 8);
        LEN_MAP.put(Double.class, 8);
    }

    // ~ Static Methods ======================================
    /**
     * 获取基础类型长度，影响字节偏移量
     * 
     * @param data
     * @return
     */
    public static <T> int getLength(final T data) {
        int len = 0;
        if (null != data) {
            final Class<?> type = data.getClass();
            len = LEN_MAP.get(type);
        }
        return len;
    }

    /**
     * 将基础类型转换成字节
     * 
     * @param data
     * @return
     */
    public static <T> byte[] toBytes(final T data) {
        byte[] bytes = new byte[] {};
        if (null != data) {
            final Class<?> type = data.getClass();
            final int length = LEN_MAP.get(type);
            bytes = new byte[length];
            if (short.class == type || Short.class == type) {
                // Short 2
                final short value = (short) data;
                putShort(bytes, Constants.POS, value);
            } else if (int.class == type || Integer.class == type) {
                // Integer 4
                final int value = (int) data;
                putInt(bytes, Constants.POS, value);
            } else if (long.class == type || Long.class == type) {
                // Long 8
                final long value = (long) data;
                putLong(bytes, Constants.POS, value);
            } else if (char.class == type || Character.class == type) {
                // Char 2
                final char value = (char) data;
                putChar(bytes, Constants.POS, value);
            } else if (float.class == type || Float.class == type) {
                // Float 4
                final float value = (float) data;
                putFloat(bytes, Constants.POS, value);
            } else if (double.class == type || Double.class == type) {
                // Double 8
                final double value = (double) data;
                putDouble(bytes, Constants.POS, value);
            } else if (boolean.class == type || Boolean.class == type) {
                // Boolean 1
                final boolean value = (boolean) data;
                putBoolean(bytes, Constants.POS, value);
            } else {
                // Recovery to Zero
                bytes = new byte[] {};
            }
        }
        return bytes;
    }

    /**
     * 
     * @param type
     * @param value
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromObject(final Class<?> type, final Object value) {
        T ret = null;
        if (null != value) {
            // List, Enum需要单独处理
            if (short.class == type || Short.class == type) {
                // Short 2
                ret = (T) Short.valueOf(value.toString());
            } else if (int.class == type || Integer.class == type) {
                // Integer 4
                ret = (T) Integer.valueOf(value.toString());
            } else if (long.class == type || Long.class == type) {
                // Long 8
                ret = (T) Long.valueOf(value.toString());
            } else if (char.class == type || Character.class == type) {
                // Char 2
                ret = (T) Character.valueOf(value.toString().charAt(Constants.IDX));
            } else if (float.class == type || Float.class == type) {
                // Float 4
                ret = (T) Float.valueOf(value.toString());
            } else if (double.class == type || Double.class == type) {
                // Double 8
                ret = (T) Double.valueOf(value.toString());
            } else if (boolean.class == type || Boolean.class == type) {
                // Boolean 1
                ret = (T) Boolean.valueOf(value.toString());
            }
        }
        return ret;
    }

    /**
     * 
     * @param bytes
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromBytes(final Class<?> type, final byte[] bytes) {
        T ret = null;
        if (null != bytes && 0 < bytes.length) {
            // List, Enum需要单独处理
            if (short.class == type || Short.class == type) {
                // Short 2
                final Short value = getShort(bytes, Constants.POS);
                ret = (T) value;
            } else if (int.class == type || Integer.class == type) {
                // Integer 4
                final Integer value = getInt(bytes, Constants.POS);
                ret = (T) value;
            } else if (long.class == type || Long.class == type) {
                // Long 8
                final Long value = getLong(bytes, Constants.POS);
                ret = (T) value;
            } else if (char.class == type || Character.class == type) {
                // Char 2
                final Character value = getChar(bytes, Constants.POS);
                ret = (T) value;
            } else if (float.class == type || Float.class == type) {
                // Float 4
                final Float value = getFloat(bytes, Constants.POS);
                ret = (T) value;
            } else if (double.class == type || Double.class == type) {
                // Double 8
                final Double value = getDouble(bytes, Constants.POS);
                ret = (T) value;
            } else if (boolean.class == type || Boolean.class == type) {
                // Boolean 1
                final Boolean value = getBoolean(bytes, Constants.POS);
                ret = (T) value;
            }
        }
        return ret;
    }

    /** From Bytes: Boolean **/
    public static boolean getBoolean(final byte[] bytes, final int off) {
        return bytes[off] != 0;
    }

    /** From Bytes: Char **/
    public static char getChar(final byte[] bytes, final int off) {
        return (char) (((bytes[off + 1] & 0xFF)) + ((bytes[off]) << 8));
    }

    /** From Bytes: Short **/
    public static short getShort(final byte[] bytes, final int off) {
        return (short) (((bytes[off + 1] & 0xFF)) + ((bytes[off]) << 8));
    }

    /** From Bytes: Int **/
    public static int getInt(final byte[] bytes, final int off) {
        return ((bytes[off + 3] & 0xFF)) + ((bytes[off + 2] & 0xFF) << 8) + ((bytes[off + 1] & 0xFF) << 16)
                + ((bytes[off]) << 24);
    }

    /** From Bytes: Float **/
    public static float getFloat(final byte[] bytes, final int off) {
        int i = getInt(bytes, off);
        return Float.intBitsToFloat(i);
    }

    /** From Bytes: Long **/
    public static long getLong(final byte[] bytes, final int off) {
        return ((bytes[off + 7] & 0xFFL)) + ((bytes[off + 6] & 0xFFL) << 8) + ((bytes[off + 5] & 0xFFL) << 16)
                + ((bytes[off + 4] & 0xFFL) << 24) + ((bytes[off + 3] & 0xFFL) << 32) + ((bytes[off + 2] & 0xFFL) << 40)
                + ((bytes[off + 1] & 0xFFL) << 48) + (((long) bytes[off]) << 56);
    }

    /** From Bytes: Double **/
    public static double getDouble(final byte[] bytes, final int off) {
        long j = getLong(bytes, off);
        return Double.longBitsToDouble(j);
    }

    /** To Bytes: Boolean **/
    public static void putBoolean(final byte[] bytes, final int off, final boolean val) {
        bytes[off] = (byte) (val ? 1 : 0);
    }

    /** To Bytes: Char **/
    public static void putChar(final byte[] bytes, final int off, final char val) {
        bytes[off + 1] = (byte) (val);
        bytes[off] = (byte) (val >>> 8);
    }

    /** To Bytes: Short **/
    public static void putShort(final byte[] bytes, final int off, final short val) {
        bytes[off + 1] = (byte) (val);
        bytes[off] = (byte) (val >>> 8);
    }

    /** To Bytes: Int **/
    public static void putInt(final byte[] bytes, final int off, final int val) {
        bytes[off + 3] = (byte) (val);
        bytes[off + 2] = (byte) (val >>> 8);
        bytes[off + 1] = (byte) (val >>> 16);
        bytes[off] = (byte) (val >>> 24);
    }

    /** To Bytes: Float **/
    public static void putFloat(final byte[] bytes, final int off, final float val) {
        int i = Float.floatToIntBits(val);
        putInt(bytes, off, i);
    }

    /** To Bytes: Long **/
    public static void putLong(final byte[] bytes, final int off, final long val) {
        bytes[off + 7] = (byte) (val);
        bytes[off + 6] = (byte) (val >>> 8);
        bytes[off + 5] = (byte) (val >>> 16);
        bytes[off + 4] = (byte) (val >>> 24);
        bytes[off + 3] = (byte) (val >>> 32);
        bytes[off + 2] = (byte) (val >>> 40);
        bytes[off + 1] = (byte) (val >>> 48);
        bytes[off] = (byte) (val >>> 56);
    }

    /** To Bytes: Double **/
    public static void putDouble(final byte[] bytes, final int off, final double val) {
        long j = Double.doubleToLongBits(val);
        putLong(bytes, off, j);
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private BitsBasic() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
