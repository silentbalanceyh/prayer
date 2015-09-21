package com.prayer.schema.db.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.prayer.util.JsonKit;

/**
 * 
 * @author Lang
 *
 */
@SuppressWarnings("unchecked")
public class ArrayHandler extends BaseTypeHandler<List<String>> { // NOPMD

    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public List<String> getNullableResult(final ResultSet retSet, final String columnName) throws SQLException {
        final String retValue = retSet.getString(columnName);
        List<String> retList = new ArrayList<>();
        if (!retSet.wasNull()) {
            retList = JsonKit.fromStr(retList.getClass(), retValue);
        }
        return retList;
    }

    /** **/
    @Override
    public List<String> getNullableResult(final ResultSet retSet, final int columnIndex) throws SQLException {
        final String retValue = retSet.getString(columnIndex);
        List<String> retList = new ArrayList<>();
        if (!retSet.wasNull()) {
            retList = JsonKit.fromStr(retList.getClass(), retValue);
        }
        return retList;
    }

    /** **/
    @Override
    public List<String> getNullableResult(final CallableStatement callStmt, final int columnIndex) throws SQLException {
        final String retValue = callStmt.getString(columnIndex);
        List<String> retList = new ArrayList<>();
        if (!callStmt.wasNull()) {
            retList = JsonKit.fromStr(retList.getClass(), retValue);
        }
        return retList;
    }

    /** **/
    @Override
    public void setNonNullParameter(final PreparedStatement pstmt, final int colIndex, final List<String> parameter,
            final JdbcType jdbcType) throws SQLException {
        final String paramStr = JsonKit.toStr(parameter);
        pstmt.setString(colIndex, null == paramStr ? "[]" : paramStr);
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
