package com.prayer.db.conn.impl;

import static com.prayer.constant.Accessors.pool;
import static com.prayer.util.Error.debug;
import static com.prayer.util.Instance.singleton;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcConnImpl.class);
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
        debug(LOGGER, "[DB] (int execute(String,List<Value<?>>)) SQL : " + sql);
        int ret = Constants.RC_FAILURE;
        if (null == params) {
            jdbc.execute(sql);
            // 直接执行只要不抛异常，不计算行数，返回0
            ret = Constants.RC_SUCCESS;
        } else {
            ret = jdbc.execute(Input.prepStmt(sql, params), Output.extractExecuteResult());
            // 在不抛异常的情况下，如果ret > 0则表示执行成功，返回影响行数，否则返回false
            ret = Constants.ZERO < ret ? ret : Constants.RC_FAILURE;
        }
        return ret;
    }

    /** **/
    @Override
    @Pre(expr = PRE_CONDITION, lang = Constants.LANG_GROOVY)
    public Long count(@NotNull @NotBlank @NotEmpty final String sql) {
        final JdbcTemplate jdbc = this.dbPool.getJdbc();
        debug(LOGGER, "[DB] (Long count(String)) SQL : " + sql);
        return jdbc.queryForObject(sql, Long.class);
    }

    /** **/
    @Override
    @Pre(expr = PRE_CONDITION, lang = Constants.LANG_GROOVY)
    public List<ConcurrentMap<String, Value<?>>> select(@NotNull @NotBlank @NotEmpty final String sql,
            final List<Value<?>> params, @MinSize(1) final ConcurrentMap<String, DataType> columnMap,
            @MinSize(0) final String... columns) {
        final JdbcTemplate jdbc = this.dbPool.getJdbc();
        debug(LOGGER, "[DB] (List<ConcurrentMap<String,String>> select(String,List<Value<?>>,String...)) SQL : " + sql);
        if (null == params) {
            return jdbc.query(sql, Output.extractDataList(columnMap, columns));
        } else {
            return jdbc.query(Input.prepStmt(sql, params), Output.extractDataList(columnMap, columns));
        }
    }

    /** **/
    @Override
    @Pre(expr = PRE_CONDITION, lang = Constants.LANG_GROOVY)
    public List<String> select(@NotNull @NotBlank @NotEmpty final String sql,
            @NotNull @NotBlank @NotEmpty final String column) {
        debug(LOGGER, "[DB] (List<String> select(String,String)) SQL : " + sql);
        final JdbcTemplate jdbc = this.dbPool.getJdbc();
        return jdbc.query(sql, Output.extractColumnList(column));
    }

    /** **/
    @Override
    @Pre(expr = PRE_CONDITION, lang = Constants.LANG_GROOVY)
    public Value<?> insert(@NotNull @NotBlank @NotEmpty final String sql,
            @NotNull @MinSize(1) final List<Value<?>> values, final boolean isRetKey, final DataType retType) {
        final JdbcTemplate jdbc = this.dbPool.getJdbc();
        debug(LOGGER, "[DB] (Value<?> insert(String,List<Value<?>>,boolean,DataType)) SQL : " + sql);
        return jdbc.execute(Input.prepStmt(sql, values, isRetKey), Output.extractIncrement(isRetKey, retType));
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
