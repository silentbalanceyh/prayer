package com.prayer.db.conn.tools;

import static com.prayer.util.Instance.singleton;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.prayer.constant.Accessors;
import com.prayer.exception.AbstractMetadataException;
import com.prayer.kernel.Value;
import com.prayer.model.type.DataType;

/**
 * 
 * @author Lang
 *
 */
interface Transducer {
    /**
     * 
     * @param stmt
     * @param idx
     * @param value
     * @throws SQLException
     */
    void injectArgs(PreparedStatement stmt, int idx, Value<?> value) throws SQLException;

    /**
     * 
     * @param retSet
     * @param type
     * @param column
     * @return
     * @throws SQLException
     * @throws AbstractMetadataException 
     */
    Value<?> getValue(ResultSet retSet, DataType type, String column) throws SQLException, AbstractMetadataException;
    /**
     * 内部类
     * @author Lang
     *
     */
    class T {    // NOPMD
        /**
         * 获取类型转换器
         * 
         * @return
         */
        public static Transducer get() {
            return singleton(Accessors.transducer());
        }
    }
}
