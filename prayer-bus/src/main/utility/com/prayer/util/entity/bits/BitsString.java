package com.prayer.util.entity.bits;

import com.prayer.resource.Resources;

/**
 * 
 * @author Lang
 *
 */
public final class BitsString {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 
     * @param type
     * @return
     */
    public static int getLength(final String data) {
        int len = 0;
        if (null != data) {
            len = data.toString().getBytes(Resources.ENCODING).length;
        }
        return len;
    }

    /**
     * 将一个类String转换成byte
     * 
     * @param data
     * @return
     */
    public static byte[] toBytes(final String data) {
        byte[] bytes = new byte[] {};
        if (null != data) {
            bytes = data.getBytes(Resources.ENCODING);
        }
        return bytes;
    }

    /**
     * 从一个byte的数组中反转
     * 
     * @param type
     * @param bytes
     * @return
     */
    public static String fromBytes(final byte[] bytes) {
        String ret = null;
        if (null != bytes) {
            ret = new String(bytes, Resources.ENCODING);
        }
        return ret;
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private BitsString() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
