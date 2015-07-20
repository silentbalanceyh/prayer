package com.prayer.db.conn.tools;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.prayer.constant.Constants;
import com.prayer.kernel.Value;
import com.prayer.model.type.DataType;
import com.prayer.model.type.IntType;
import com.prayer.model.type.LongType;
import com.prayer.util.StringKit;

import net.sf.oval.constraint.MinLength;
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
public final class Output {
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	/**
	 * 直接获取自增长字段的值
	 * 
	 * @return
	 */
	public static PreparedStatementCallback<Value<?>> extractIncrement(final DataType retType) {
		return new PreparedStatementCallback<Value<?>>() {
			/** **/
			@Override
			public Value<?> doInPreparedStatement(final PreparedStatement stmt)
					throws SQLException, DataAccessException {
				final int rows = stmt.executeUpdate();
				Value<?> retValue = null;
				if (Constants.ZERO <= rows) {
					try (final ResultSet ret = stmt.getGeneratedKeys()) {
						if (ret.next()) {
							if (DataType.INT == retType) {
								retValue = new IntType(ret.getInt(1));
							} else if (DataType.LONG == retType) { // NOPMD
								retValue = new LongType(ret.getLong(1));
							}
						}
					}
				}
				return retValue;
			}

		};
	}

	/**
	 * 结果集获取
	 * 
	 * @param columns
	 * @return
	 */
	public static ResultSetExtractor<List<ConcurrentMap<String, String>>> extractDataList(
			@MinLength(1) final String... columns) {
		return new ResultSetExtractor<List<ConcurrentMap<String, String>>>() {
			/** **/
			@Override
			public List<ConcurrentMap<String, String>> extractData(final ResultSet retSet)
					throws SQLException, DataAccessException {
				final List<ConcurrentMap<String, String>> retList = new ArrayList<>();
				while (retSet.next()) {
					final ConcurrentMap<String, String> record = new ConcurrentHashMap<>();
					for (final String column : columns) {
						String value = retSet.getString(column);
						if (StringKit.isNil(value)) {
							value = "";
						}
						record.put(column, value);
					}
					retList.add(record);
				}
				return retList;
			}
		};
	}

	/**
	 * 提取单列数据集
	 * 
	 * @param column
	 * @return
	 */
	public static ResultSetExtractor<List<String>> extractColumnList(@NotNull @NotEmpty @NotBlank final String column) {
		return new ResultSetExtractor<List<String>>() {
			/** **/
			@Override
			public List<String> extractData(final ResultSet retSet) throws SQLException, DataAccessException {
				final List<String> retList = new ArrayList<>();
				while (retSet.next()) {
					String value = retSet.getString(column);
					if (StringKit.isNil(value)) {
						value = "";
					}
					retList.add(value);
				}
				return retList;
			}
		};
	}

	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	private Output() {
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
