package com.prayer.model.treater;

import com.prayer.constant.Constants;
import com.prayer.constant.Resources;
import com.prayer.facade.entity.Treater;
import com.prayer.util.fun.BeanGet;
import com.prayer.util.fun.BeanSet;
import com.prayer.util.string.StringKit;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public class StringTreater implements Treater<String> {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** 将数据写入到JsonObject **/
    @Override
    public void writeField(final JsonObject json, final String key, final BeanGet<String> fun) {
        // 1.调用函数引用的get方法
        final String data = fun.get();
        // 2.如果为null引用或者空字符串则不填充
        if (StringKit.isNonNil(data)) {
            json.put(key, data);
        }
    }

    /** 将数据写入到Buffer **/
    @Override
    public void writeField(final Buffer buffer, final BeanGet<String> fun) {
        // 1.调用函数引用的get方法
        final String data = fun.get();
        // 2.初始化byte[]
        byte[] bytesData = new byte[] {};
        // 3.根据data判断
        if (StringKit.isNonNil(data)) {
            // 4.不为空的情况
            bytesData = data.getBytes(Resources.SYS_ENCODING);
        }
        // 5.先在Buffer中添加String的数组长度length
        buffer.appendInt(bytesData.length);
        // 6.添加字节数组
        buffer.appendBytes(bytesData);
    }

    /** 将数据从Json对象中读取出来 **/
    @Override
    public void readField(final JsonObject json, final String key, final BeanSet<String> fun) {
        // 1.调用Json的Get方法
        final Object value = json.getValue(key);
        // 2.如果值不为null，并且为String.class类型
        if (null != value && String.class == value.getClass()) {
            // 3.1.设置String
            final String data = json.getString(key);
            fun.set(data);
        } else {
            // 3.2.设置null
            fun.set(null);
        }
    }

    /** 从Buffer中读取 **/
    @Override
    public int readField(final Buffer buffer, int pos, final BeanSet<String> fun) {
        // 1.获取字节数组长度
        final int length = buffer.getInt(pos);
        // 2.跳过length长度部分
        pos += 4;
        // 3.从Buffer中读取bytesData
        final byte[] bytesData = buffer.getBytes(pos, pos + length);
        // 4.构造读取新的String
        final String value = new String(bytesData, Resources.SYS_ENCODING);
        // 5.刷新position
        pos += length;
        if (Constants.ZERO < length) {
            // 6.1.调用set函数
            fun.set(value);
        } else {
            // 6.2.长度为0，序列化成null
            fun.set(null);
        }
        // 7.最终返回Buffer中的position
        return pos;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
