package com.prayer.util.entity.bits;

import static com.prayer.util.Converter.fromStr;

import java.nio.charset.Charset;

import com.prayer.facade.resource.Point;
import com.prayer.resource.InceptBus;
import com.prayer.util.string.StringKit;

/**
 * 枚举的Buffer处理工具类
 * 
 * @author Lang
 *
 */
public final class BitsEnum {
    // ~ Static Fields =======================================
    /** **/
    private static final Charset ENCODING = Charset
            .forName(InceptBus.build(Point.System.class).getString(Point.System.ENCODING));

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 将Enum从一个bytes中读出来
     * 
     * @param bytes
     * @return
     */
    public static <T extends Enum<T>> T fromBytes(final Class<T> type, final byte[] bytes) {
        T ret = null;
        if (null != bytes && 0 < bytes.length) {
            final String value = new String(bytes, ENCODING);
            if (StringKit.isNonNil(value)) {
                ret = fromStr(type, value);
            }
        }
        return ret;
    }

    /**
     * 将一个枚举值转成一个bytes
     * 
     * @param data
     * @return
     */
    public static <T> byte[] toBytes(final T data) {
        byte[] ret = new byte[] {};
        if (null != data && data.getClass() == Enum.class) {
            ret = data.toString().getBytes(ENCODING);
        }
        return ret;
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private BitsEnum() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
