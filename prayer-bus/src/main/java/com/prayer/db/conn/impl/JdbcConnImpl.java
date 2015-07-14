package com.prayer.db.conn.impl;

import static com.prayer.constant.Accessors.pool;
import static com.prayer.util.Error.info;
import static com.prayer.util.Instance.singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.prayer.constant.Constants;
import com.prayer.db.conn.JdbcContext;
import com.prayer.db.pool.AbstractDbPool;

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
	private static final Logger LOGGER = LoggerFactory.getLogger(JdbcConnImpl.class);
	/** **/
	private static final String PRE_CONDITION = "_this.jdbc != null";
	// ~ Instance Fields =====================================
	/** **/
	@NotNull
	private transient final JdbcTemplate jdbc;
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	@PostValidateThis
	public JdbcConnImpl(){
		final AbstractDbPool dbPool = singleton(pool());
		this.jdbc = dbPool.getJdbc();
	}
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	@Pre(expr = PRE_CONDITION, lang = Constants.LANG_GROOVY)
	public int execute(@NotNull final String sql) {
		info(LOGGER, "execute(sql) : " + sql);
		this.jdbc.execute(sql);
		return Constants.RC_SUCCESS;
	}
	/** **/
	@Override
	@Pre(expr = PRE_CONDITION, lang = Constants.LANG_GROOVY)
	public Long count(@NotNull final String sql){
		info(LOGGER, "count(sql) : " + sql);
		return this.jdbc.queryForObject(sql, Long.class);
	}
	@Override
	public int update(String sql, Object[] values, int... types) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(String sql, Object... params) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <T> T select(String sql, RowMapper<T> rowMapper, Object... params) {
		// TODO Auto-generated method stub
		return null;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
