package com.prayer.facade.dao;

import static com.prayer.util.reflection.Instance.singleton;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.prayer.constant.Accessors;
import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.facade.model.crucial.Value;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.model.type.DataType;

/**
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.ENG_PUBLIC)
public interface JdbcTransducer {
    /**
     * 
     * @param stmt
     * @param idx
     * @param value
     * @throws SQLException
     */
    @VertexApi(Api.TOOL)
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
    @VertexApi(Api.TOOL)
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
