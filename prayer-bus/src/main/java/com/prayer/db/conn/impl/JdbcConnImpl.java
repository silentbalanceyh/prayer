package com.prayer.db.conn.impl;

import static com.prayer.constant.Accessors.pool;
import static com.prayer.util.Instance.singleton;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.springframework.jdbc.core.JdbcTemplate;

import com.prayer.constant.Constants;
import com.prayer.db.conn.JdbcContext;
import com.prayer.db.conn.tools.Input;
import com.prayer.db.conn.tools.Output;
import com.prayer.db.pool.AbstractDbPool;
import com.prayer.kernel.Value;
import com.prayer.model.type.DataType;

import net.sf.oval.constraint.MinSize;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;
import net.sf.oval.guard.Pre;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class JdbcConnImpl implements JdbcContext {
	// ~ Static Fields =======================================
	/** **/
	private static final String PRE_CONDITION = "_this.dbPool != null";
	// ~ Instance Fields =====================================
	/** **/
	private transient final AbstractDbPool dbPool;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	@PostValidateThis
	public JdbcConnImpl() {
		synchronized (getClass()) {
			this.dbPool = singleton(pool());
		}
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	@Pre(expr = PRE_CONDITION, lang = Constants.LANG_GROOVY)
	public int execute(@NotNull @NotBlank @NotEmpty final String sql, final List<Value<?>> params) {
		final JdbcTemplate jdbc = this.dbPool.getJdbc();
		if (null == params) {
			jdbc.execute(sql);
		} else {
			jdbc.execute(Input.prepStmt(sql, params), null);
		}
		return Constants.RC_SUCCESS;
	}

	/** **/
	@Override
	@Pre(expr = PRE_CONDITION, lang = Constants.LANG_GROOVY)
	public Long count(@NotNull @NotBlank @NotEmpty final String sql) {
		final JdbcTemplate jdbc = this.dbPool.getJdbc();
		return jdbc.queryForObject(sql, Long.class);
	}

	/** **/
	@Override
	@Pre(expr = PRE_CONDITION, lang = Constants.LANG_GROOVY)
	public List<ConcurrentMap<String, String>> select(@NotNull @NotBlank @NotEmpty final String sql,
			final List<Value<?>> params, @MinSize(1) final String... columns) {
		final JdbcTemplate jdbc = this.dbPool.getJdbc();
		if (null == params) {
			return jdbc.query(sql, Output.extractDataList(columns));
		} else {
			return jdbc.query(Input.prepStmt(sql, params), Output.extractDataList(columns));
		}
	}

	/** **/
	@Override
	@Pre(expr = PRE_CONDITION, lang = Constants.LANG_GROOVY)
	public List<String> select(@NotNull @NotBlank @NotEmpty final String sql,
			@NotNull @NotBlank @NotEmpty final String column) {
		final JdbcTemplate jdbc = this.dbPool.getJdbc();
		return jdbc.query(sql, Output.extractColumnList(column));
	}

	/** **/
	@Override
	@Pre(expr = PRE_CONDITION, lang = Constants.LANG_GROOVY)
	public Value<?> insert(@NotNull @NotBlank @NotEmpty final String sql,
			@NotNull @MinSize(1) final List<Value<?>> values, final boolean isRetKey, final DataType retType) {
		final JdbcTemplate jdbc = this.dbPool.getJdbc();
		return jdbc.execute(Input.prepStmt(sql, values, isRetKey), Output.extractIncrement(isRetKey, retType));
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
