package com.prayer.dao.record.impl;

import static com.prayer.util.Instance.reservoir;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.dao.record.RecordDao;
import com.prayer.db.conn.JdbcContext;
import com.prayer.db.conn.impl.JdbcConnImpl;
import com.prayer.kernel.Record;
import com.prayer.kernel.Value;

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
abstract class AbstractDaoImpl implements RecordDao {
	// ~ Static Fields =======================================
	/** JDBC的Context的延迟池化技术 **/
	private static final ConcurrentMap<String, JdbcContext> JDBC_POOLS = new ConcurrentHashMap<>();

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	/**
	 * 
	 * @return
	 */
	@NotNull
	protected JdbcContext getContext(@NotNull @NotEmpty @NotBlank final String identifier) {
		JdbcContext context = JDBC_POOLS.get(identifier);
		if (null == context) {
			context = reservoir(JDBC_POOLS, identifier, JdbcConnImpl.class);
		}
		return context;
	}

	/**
	 * 生成Insert语句
	 * 
	 * @param schema
	 * @param filters
	 * @return
	 */
	@NotNull
	protected String prepInsertSQL(@NotNull final Record record, @NotNull @MinLength(0) final String... filters) {
		final Set<String> columns = new HashSet<>();	// 不可直接引用record.columns()，否则会修改掉Columns
		for(final String injectCol: record.columns()){
			columns.add(injectCol);
		}
		columns.removeAll(Arrays.asList(filters));
		return SqlDmlStatement.prepInsertSQL(record.table(), columns);
	}

	/**
	 * 生成Insert语句的参数表
	 * 
	 * @param record
	 * @param filters
	 * @return
	 */
	@NotNull
	protected List<Value<?>> prepInsertParam(@NotNull final Record record,
			@NotNull @MinLength(0) final String... filters) {
		final Set<String> columns = new HashSet<>();	// 不可直接引用record.columns()，否则会修改掉Columns
		for(final String injectCol: record.columns()){
			columns.add(injectCol);
		}
		columns.removeAll(Arrays.asList(filters));
		final List<Value<?>> retParam = new ArrayList<>();
		for (final String column : columns) {
			retParam.add(record.column(column));
		}
		return retParam;
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
