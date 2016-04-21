package com.prayer.plugin.db.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.prayer.facade.constant.Constants;
import com.prayer.util.io.JacksonKit;

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
        return getNull(!retSet.wasNull(), retValue);
    }

    /** **/
    @Override
    public List<String> getNullableResult(final ResultSet retSet, final int columnIndex) throws SQLException {
        final String retValue = retSet.getString(columnIndex);
        return getNull(!retSet.wasNull(), retValue);
    }

    /** **/
    @Override
    public List<String> getNullableResult(final CallableStatement callStmt, final int columnIndex) throws SQLException {
        final String retValue = callStmt.getString(columnIndex);
        return getNull(!callStmt.wasNull(), retValue);
    }

    /** **/
    @Override
    public void setNonNullParameter(final PreparedStatement pstmt, final int colIndex, final List<String> parameter,
            final JdbcType jdbcType) throws SQLException {
        final String paramStr = JacksonKit.toStr(parameter);
        pstmt.setString(colIndex, null == paramStr ? Constants.EMPTY_JARR : paramStr);
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private List<String> getNull(final boolean ret, final String value) {
        List<String> retList = new ArrayList<>();
        if (ret) {
            retList = JacksonKit.fromStr(retList.getClass(), value);
        }
        return retList;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
