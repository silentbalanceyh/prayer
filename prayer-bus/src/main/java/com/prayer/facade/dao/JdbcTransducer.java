package com.prayer.facade.dao;

import static com.prayer.util.Instance.singleton;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.prayer.exception.AbstractDatabaseException;
import com.prayer.facade.kernel.Value;
import com.prayer.model.type.DataType;
import com.prayer.util.cv.Accessors;

/**
 * 
 * @author Lang
 *
 */
public interface JdbcTransducer {
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
     * @throws AbstractDatabaseException 
     */
    Value<?> getValue(ResultSet retSet, DataType type, String column) throws SQLException, AbstractDatabaseException;
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
        public static JdbcTransducer get() {
            return singleton(Accessors.transducer());
        }
    }
}
