package com.prayer.db.conn.tools;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;

import com.prayer.kernel.Value;
import com.prayer.model.type.BinaryType;
import com.prayer.model.type.BooleanType;
import com.prayer.model.type.DateType;
import com.prayer.model.type.DecimalType;
import com.prayer.model.type.IntType;
import com.prayer.model.type.LongType;
import com.prayer.model.type.StringType;

import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.MinSize;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class Input {
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	/**
	 * Insert语句的参数设置，不可不传参
	 * @param sql
	 * @param values
	 * @param isRetKey
	 * @return
	 */
	public static PreparedStatementCreator prepStmt(@NotNull @NotBlank @NotEmpty final String sql,
			@NotNull @MinSize(1) final List<Value<?>> values, final boolean isRetKey) {
		return new PreparedStatementCreator() {
			/** **/
			@Override
			public PreparedStatement createPreparedStatement(final Connection con) throws SQLException {
				PreparedStatement stmt = null;
				if (isRetKey) {
					stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				} else {
					stmt = con.prepareStatement(sql);
				}
				final int size = values.size();
				for (int idx = 1; idx <= size; idx++) {
					// 以数据库的Index为主，数据库从1开始索引，数组本身从0开始索引
					setParameters(stmt, idx, values.get(idx - 1));
				}
				return stmt;
			}
		};
	}

	/**
	 * Select语句的参数设置，可不传参，则表示Select所有
	 * @param sql
	 * @param values
	 * @return
	 */
	public static PreparedStatementCreator prepStmt(@NotNull @NotBlank @NotEmpty final String sql,
			@NotNull @MinSize(0) final List<Value<?>> values) {
		return new PreparedStatementCreator() {
			/** **/
			@Override
			public PreparedStatement createPreparedStatement(final Connection con) throws SQLException {
				final PreparedStatement stmt = con.prepareStatement(sql);
				final int size = values.size();
				for (int idx = 1; idx <= size; idx++) {
					setParameters(stmt, idx, values.get(idx - 1));
				}
				return stmt;
			};
		};
	}

	/**
	 * 
	 * @param stmt
	 * @param idx
	 * @param value
	 * @throws SQLException
	 */
	public static void setParameters(@NotNull final PreparedStatement stmt, @Min(0) final int idx,
			@NotNull final Value<?> value) throws SQLException {
		switch (value.getDataType()) {
		case INT: {
			stmt.setInt(idx, ((IntType) value).getValue());
		}
			break;
		case LONG: {
			stmt.setLong(idx, ((LongType) value).getValue());
		}
			break;
		case BOOLEAN: {
			stmt.setBoolean(idx, ((BooleanType) value).getValue());
		}
			break;
		case DECIMAL: {
			stmt.setBigDecimal(idx, ((DecimalType) value).getValue());
		}
			break;
		case DATE: {
			final Date datetime = ((DateType) value).getValue();
			stmt.setDate(idx, new java.sql.Date(datetime.getTime()));
		}
			break;
		case BINARY: {
			final InputStream stream = new ByteArrayInputStream(((BinaryType) value).getValue());
			stmt.setBinaryStream(idx, stream);
		}
			break;
		default: // Default for XML, STRING, SCRIPT, JSON
		{
			stmt.setString(idx, ((StringType) value).getValue());
		}
			break;
		}
	}
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	private Input(){}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
