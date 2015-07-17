package com.prayer.db.conn.tools;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

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
