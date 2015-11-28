package com.prayer.kernel.model;

import static com.prayer.util.Instance.singleton;

import com.prayer.exception.AbstractDatabaseException;
import com.prayer.kernel.i.Value;
import com.prayer.model.type.DataType;

import io.vertx.core.json.JsonObject;

/**
 * 内部转换器
 * 
 * @author Lang
 *
 */
public interface Transducer {
    /**
     * 
     * @param type
     * @param value
     * @return
     */
    Value<?> getValue(DataType type, String value) throws AbstractDatabaseException;
    /**
     * 
     * @param data
     * @param type
     * @param field
     * @return
     */
    Value<?> getValue(JsonObject data, DataType type, String field) throws AbstractDatabaseException;
    /**
     * 内部类
     * 
     * @author Lang
     *
     */
    public class V { // NOPMD
        /**
         * 
         * @return
         */
        public static Transducer get(){
            return singleton(ValueTransducer.class);
        }
    }
}
