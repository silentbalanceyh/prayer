package com.prayer.util.dao; // NOPMD

import static com.prayer.util.debug.Log.debug;
import static com.prayer.util.debug.Log.peError;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.prayer.base.exception.AbstractDatabaseException;
import com.prayer.constant.Constants;
import com.prayer.constant.log.DebugKey;
import com.prayer.facade.dao.JdbcTransducer.T;
import com.prayer.facade.kernel.Value;
import com.prayer.model.type.DataType;
import com.prayer.model.type.IntType;
import com.prayer.model.type.LongType;
import com.prayer.util.StringKit;

import net.sf.oval.constraint.InstanceOfAny;
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
public final class Output { // NOPMD
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
                debug(LOGGER, DebugKey.INFO_JDBC_AROWS, rows);
                return rows;
            }
        };
    }

    /**
     * 直接获取自增长字段的值
     * 
     * @return
     */
    public static PreparedStatementCallback<Value<?>> extractIncrement(final boolean isRetKey,
            @InstanceOfAny(DataType.class) final DataType retType) {
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
                        try {
                            final Value<?> value = T.get().getValue(retSet, columnTypes.get(column), column);
                            record.put(column, value);
                        } catch (AbstractDatabaseException ex) {
                            peError(LOGGER, ex);
                        }
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
                        value = Constants.EMPTY_STR;
                    }
                    retList.add(value);
                }
                return retList;
            }
        };
    }

    /**
     * 提取多列数据集
     * 
     * @param column
     * @return
     */
    public static ResultSetExtractor<List<ConcurrentMap<String, String>>> extractColumnList(
            @NotNull @MinSize(2) final String[] columns) {
        return new ResultSetExtractor<List<ConcurrentMap<String, String>>>() {
            /** **/
            @Override
            public List<ConcurrentMap<String, String>> extractData(final ResultSet retSet)
                    throws SQLException, DataAccessException {
                final List<ConcurrentMap<String, String>> retList = new ArrayList<>();
                while (retSet.next()) {
                    final ConcurrentMap<String, String> row = new ConcurrentHashMap<>();
                    for (final String column : columns) {
                        String value = retSet.getString(column);
                        if (StringKit.isNil(value)) {
                            value = Constants.EMPTY_STR;
                        }
                        row.put(column, value);
                    }
                    retList.add(row);
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
