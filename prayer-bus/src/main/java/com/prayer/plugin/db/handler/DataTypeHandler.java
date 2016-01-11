package com.prayer.plugin.db.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.prayer.model.type.DataType;

/**
 * 为ENUM枚举类型定制的TypeHandler
 *
 * @author Lang
 * @see
 */
public class DataTypeHandler extends BaseTypeHandler<DataType> { // NOPMD
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void setNonNullParameter(final PreparedStatement pstmt, final int colIndex, final DataType parameter,
            final JdbcType jdbcType) throws SQLException {
        pstmt.setString(colIndex, parameter.toString());
    }

    /** **/
    @Override
    public DataType getNullableResult(final ResultSet retSet, final String columnName) throws SQLException {
        final String repValue = retSet.getString(columnName);
        return getNull(!retSet.wasNull(), repValue);
    }

    /** **/
    @Override
    public DataType getNullableResult(final ResultSet retSet, final int columnIndex) throws SQLException {
        final String repValue = retSet.getString(columnIndex);
        return getNull(!retSet.wasNull(), repValue);
    }

    /** **/
    @Override
    public DataType getNullableResult(final CallableStatement callStmt, final int columnIndex) throws SQLException {
        final String repValue = callStmt.getString(columnIndex);
        return getNull(!callStmt.wasNull(), repValue);
    }
    // ~ Get/Set =============================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private DataType getNull(final boolean ret, final String value) {
        DataType retType = null;
        if (ret) {
            retType = DataType.fromString(value);
        }
        return retType;
    }
    // ~ hashCode,equals,toString ============================
}
