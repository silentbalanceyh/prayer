package com.prayer.util.jdbc;

import static com.prayer.util.Calculator.diff;
import static com.prayer.util.debug.Log.debug;
import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.instance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.log.DebugKey;
import com.prayer.facade.kernel.Expression;
import com.prayer.facade.kernel.Value;
import com.prayer.facade.record.Record;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.model.query.Restrictions;

import net.sf.oval.constraint.InstanceOf;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class QueryHelper {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(QueryHelper.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    /**
     * 获取主键WHERE子句
     * 
     * @param columns
     *            传入一个TreeSet
     * @return
     */
    @InstanceOf(Expression.class)
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
                    peError(LOGGER, ex);
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
    @NotNull
    public static List<Record> extractData(@NotNull @InstanceOf(Record.class) final Record record,
            @NotNull final List<ConcurrentMap<String, Value<?>>> resultData) {
        final String identifier = record.identifier();
        // 从List中抽取记录
        final List<Record> retList = new ArrayList<>();
        for (final ConcurrentMap<String, Value<?>> item : resultData) {
            // 从Map中抽取字段
            final Record ret = instance(record.getClass().getName(), identifier);
            for (final String column : item.keySet()) {
                try {
                    final String field = record.toField(column);
                    debug(LOGGER, DebugKey.INFO_R_EXTRACT, field, item.get(column));
                    ret.set(field, item.get(column));
                } catch (AbstractDatabaseException ex) {
                    peError(LOGGER, ex);
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
    @NotNull
    public static String prepInsertSQL(@InstanceOf(Record.class) final Record record, final String... filters) {
        final Collection<String> columns = diff(record.columns(), Arrays.asList(filters));
        return SqlDML.prepInsertSQL(record.table(), columns);
    }

    /**
     * 生成Insert语句的参数表
     * 
     * @param record
     * @param filters
     * @return
     */
    @NotNull
    public static List<Value<?>> prepParam(@InstanceOf(Record.class) final Record record, final String... filters)
            throws AbstractDatabaseException {
        final Collection<String> columns = diff(record.columns(), Arrays.asList(filters));
        final List<Value<?>> retParam = new ArrayList<>();
        for (final String column : columns) {
            retParam.add(record.column(column));
        }
        return retParam;
    }

    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private QueryHelper() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
