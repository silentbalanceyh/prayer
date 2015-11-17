package com.prayer.kernel.model;

import static com.prayer.util.Instance.singleton;

import com.prayer.exception.AbstractDatabaseException;
import com.prayer.kernel.Value;
import com.prayer.model.type.DataType;

/**
 * 内部转换器
 * 
 * @author Lang
 *
 */
interface Transducer {
    /**
     * 
     * @param type
     * @param value
     * @return
     */
    Value<?> getValue(DataType type, String value) throws AbstractDatabaseException;

    /**
     * 内部类
     * 
     * @author Lang
     *
     */
    class V { // NOPMD
        /**
         * 
         * @return
         */
        public static Transducer get(){
            return singleton(ValueTransducer.class);
        }
    }
}
