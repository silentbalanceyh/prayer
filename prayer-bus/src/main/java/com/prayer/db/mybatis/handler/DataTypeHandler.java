package com.prayer.db.mybatis.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.prayer.meta.DataType;

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
		DataType retValue = null;
		if (!retSet.wasNull()) {
			retValue = DataType.fromString(repValue);
		}
		return retValue;
	}

	/** **/
	@Override
	public DataType getNullableResult(final ResultSet retSet, final int columnIndex) throws SQLException {
		final String repValue = retSet.getString(columnIndex);
		DataType retValue = null;
		if (!retSet.wasNull()) {
			retValue = DataType.fromString(repValue);
		}
		return retValue;
	}

	/** **/
	@Override
	public DataType getNullableResult(final CallableStatement callStmt, final int columnIndex) throws SQLException {
		final String repValue = callStmt.getString(columnIndex);
		DataType retValue = null;
		if (!callStmt.wasNull()) {
			retValue = DataType.fromString(repValue);
		}
		return retValue;
	}
	// ~ Get/Set =============================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ hashCode,equals,toString ============================
}
