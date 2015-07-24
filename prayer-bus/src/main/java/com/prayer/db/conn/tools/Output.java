package com.prayer.db.conn.tools;	// NOPMD

import static com.prayer.util.Error.debug;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.prayer.constant.Constants;
import com.prayer.kernel.Value;
import com.prayer.model.type.BinaryType;
import com.prayer.model.type.BooleanType;
import com.prayer.model.type.DataType;
import com.prayer.model.type.DateType;
import com.prayer.model.type.DecimalType;
import com.prayer.model.type.IntType;
import com.prayer.model.type.JsonType;
import com.prayer.model.type.LongType;
import com.prayer.model.type.ScriptType;
import com.prayer.model.type.StringType;
import com.prayer.model.type.XmlType;
import com.prayer.util.StringKit;

import net.sf.oval.constraint.MinLength;
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
public final class Output {		// NOPMD
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(Output.class);

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	/**
	 * 执行用的Callback信，判断执行是否成功，返回影响的行数
	 * 
	 * @return
	 */
	public static PreparedStatementCallback<Integer> extractExecuteResult() {
		return new PreparedStatementCallback<Integer>() {
			/** **/
			@Override
			public Integer doInPreparedStatement(final PreparedStatement stmt)
					throws SQLException, DataAccessException {
				final int rows = stmt.executeUpdate();
				debug(LOGGER, "[Output] ==> Rows : " + rows);
				return rows;
			}
		};
	}

	/**
	 * 直接获取自增长字段的值
	 * 
	 * @return
	 */
	public static PreparedStatementCallback<Value<?>> extractIncrement(final boolean isRetKey, final DataType retType) {
		return new PreparedStatementCallback<Value<?>>() {
			/** **/
			@Override
			public Value<?> doInPreparedStatement(final PreparedStatement stmt)
					throws SQLException, DataAccessException {
				final int rows = stmt.executeUpdate();
				Value<?> retValue = null;
				if (Constants.ZERO <= rows && isRetKey) {
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
	public static ResultSetExtractor<List<ConcurrentMap<String, Value<?>>>> extractDataList(
			@MinSize(1) final ConcurrentMap<String, DataType> columnTypes, @MinLength(1) final String... columns) {
		return new ResultSetExtractor<List<ConcurrentMap<String, Value<?>>>>() {
			/** **/
			@Override
			public List<ConcurrentMap<String, Value<?>>> extractData(final ResultSet retSet)
					throws SQLException, DataAccessException {
				final List<ConcurrentMap<String, Value<?>>> retList = new ArrayList<>();
				while (retSet.next()) {
					final ConcurrentMap<String, Value<?>> record = new ConcurrentHashMap<>();
					for (final String column : columns) {
						record.put(column, getValue(retSet, columnTypes.get(column), column));
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

	/**
	 * 
	 * @param type
	 * @param column
	 * @return
	 */
	public static Value<?> getValue(@NotNull final ResultSet retSet, @NotNull final DataType type,	// NOPMD
			@NotNull @NotEmpty @NotBlank final String column) throws SQLException {
		Value<?> ret = null;
		switch (type) {
		case INT: {
			final int value = retSet.getInt(column);
			ret = new IntType(value);
		}
			break;
		case LONG: {
			final long value = retSet.getLong(column);
			ret = new LongType(value);
		}
			break;
		case BOOLEAN: {
			final boolean value = retSet.getBoolean(column);
			ret = new BooleanType(value);
		}
			break;
		case DECIMAL: {
			final BigDecimal value = retSet.getBigDecimal(column);
			ret = new DecimalType(value);
		}
			break;
		case DATE: {
			// 是不是SQL Server特有
			final java.sql.Timestamp value = retSet.getTimestamp(column);
			ret = new DateType(new Date(value.getTime()));
		}
			break;
		case BINARY: {
			final byte[] value = retSet.getBytes(column);
			ret = new BinaryType(value);
		}
			break;
		case XML: {
			final String value = retSet.getString(column);
			ret = new XmlType(value);
		}
			break;
		case JSON: {
			final String value = retSet.getString(column);
			ret = new JsonType(value);
		}
			break;
		case SCRIPT: {
			final String value = retSet.getString(column);
			ret = new ScriptType(value);
		}
			break;
		default: {
			final String value = retSet.getString(column);
			ret = new StringType(value);
		}
			break;
		}
		return ret;
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
