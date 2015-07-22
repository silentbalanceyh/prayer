package com.prayer.dao.record.impl;

import static com.prayer.util.Calculator.diff;
import static com.prayer.util.Error.info;
import static com.prayer.util.Instance.instance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.AbstractDatabaseException;
import com.prayer.kernel.Expression;
import com.prayer.kernel.Record;
import com.prayer.kernel.Value;
import com.prayer.kernel.model.GenericRecord;
import com.prayer.kernel.query.Restrictions;
import com.prayer.model.h2.FieldModel;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
final class SqlHelper {
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(SqlHelper.class);

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	/**
	 * 获取主键WHERE子句
	 * 
	 * @param columns
	 *            传入一个TreeSet
	 * @return
	 */
	public static Expression getAndExpr(@NotNull final Set<String> columns) {
		Expression ret = null;
		// 记得使用TreeSet
		for (final String column : columns) {
			if (ret == null) {
				// 单个条件
				ret = Restrictions.eq(column);
			} else {
				// 多个条件组合
				try {
					ret = Restrictions.and(ret, Restrictions.eq(column));
				} catch (AbstractDatabaseException ex) {
					info(LOGGER, ex.getErrorMessage());
				}
			}
		}
		return ret;
	}
	/**
	 * 将查询的结果集List<ConcurrentMap<String,String>>转换成List<Record>
	 * 
	 * @param record
	 * @param result
	 */
	public static List<Record> extractData(@NotNull final Record record,
			@NotNull final List<ConcurrentMap<String, String>> resultData) {
		final String identifier = record.identifier();
		// 从List中抽取记录
		final List<Record> retList = new ArrayList<>();
		for (final ConcurrentMap<String, String> item : resultData) {
			// 从Map中抽取字段
			final Record ret = instance(GenericRecord.class.getName(), identifier);
			for (final String column : item.keySet()) {
				final String field = record.schema().getColumn(column).getName();
				try {
					ret.set(field, item.get(column));
				} catch (AbstractDatabaseException ex) {
					info(LOGGER, ex.getErrorMessage());
				}
			}
			retList.add(ret);
		}
		return retList;
	}
	/**
	 * 生成Insert语句
	 * 
	 * @param schema
	 * @param filters
	 * @return
	 */
	public static String prepInsertSQL(final Record record, final String... filters) {
		final Collection<String> columns = diff(record.columns(), Arrays.asList(filters));
		return SqlDmlStatement.prepInsertSQL(record.table(), columns);
	}

	/**
	 * 生成Insert语句的参数表
	 * 
	 * @param record
	 * @param filters
	 * @return
	 */
	public static List<Value<?>> prepParam(final Record record, final String... filters) {
		final Collection<String> columns = diff(record.columns(), Arrays.asList(filters));
		final List<Value<?>> retParam = new ArrayList<>();
		for (final String column : columns) {
			retParam.add(record.column(column));
		}
		return retParam;
	}

	/**
	 * 获取主键参数表
	 * 
	 * @param record
	 * @return
	 */
	public static ConcurrentMap<String, Value<?>> prepPKWhere(final Record record) {
		final List<FieldModel> pkFields = record.schema().getPrimaryKeys();
		final ConcurrentMap<String, Value<?>> retMap = new ConcurrentHashMap<>();
		for (final FieldModel field : pkFields) {
			retMap.put(field.getColumnName(), record.column(field.getColumnName()));
		}
		return retMap;
	}
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	private SqlHelper() {
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
