package com.prayer.fantasm.builder;

import static com.prayer.util.Calculator.diff;
import static com.prayer.util.Calculator.intersect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.SystemEnum.StatusFlag;
import com.prayer.exception.database.NullableAddException;
import com.prayer.exception.database.NullableAlterException;
import com.prayer.exception.database.UniqueAddException;
import com.prayer.exception.database.UniqueAlterException;
import com.prayer.facade.builder.Refresher;
import com.prayer.facade.builder.line.FieldSaber;
import com.prayer.facade.builder.line.KeySaber;
import com.prayer.facade.builder.reflector.Reflector;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.constant.Symbol;
import com.prayer.facade.model.crucial.Referencer;
import com.prayer.facade.pool.JdbcConnection;
import com.prayer.facade.schema.Schema;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.model.crucial.schema.FKReferencer;
import com.prayer.model.meta.database.PEField;
import com.prayer.model.meta.database.PEKey;
import com.prayer.sql.util.SqlDDLBuilder;
import com.prayer.util.string.StringKit;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 抽象层的Refresher
 * 
 * @author Lang
 *
 */
@Guarded
public abstract class AbstractRefresher implements Refresher {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 数据库连接 **/
    private transient final JdbcConnection connection; // NOPMD
    /** References **/
    private transient final List<FKReferencer> refs = new ArrayList<>();
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================

    /** **/
    public AbstractRefresher(@NotNull final JdbcConnection connection) {
        this.connection = connection;
    }

    // ~ Abstract Methods ====================================
    /** 反向构造器 **/
    public abstract Reflector reflector();

    /** 构造系统需要使用的Reference的获取器 **/
    public abstract Referencer referencer();

    /** 构造字段操作器 **/
    public abstract FieldSaber getFieldSaber();

    /** 构造约束操作器 **/
    public abstract KeySaber getKeySaber();

    // ~ Override Methods ====================================
    /** **/
    @Override
    public String buildAlterSQL(@NotNull final Schema schema) throws AbstractDatabaseException {
        // 不需要，这个语句只有表存在的时候会执行：Fix Issue：Invalid object name 'XXXXX'
        final List<String> lines = new ArrayList<>();
        this.refs.clear();
        /** 1.添加引用删除语句：References **/
        lines.addAll(this.getRefsDropSql(schema));
        /** 2.添加约束删除语句：Constraints **/
        lines.addAll(this.getCsDropSql(schema));
        /** 3.获取列状态 **/
        final ConcurrentMap<StatusFlag, Collection<String>> statusMap = this.getColumnStatus(schema);
        /** 4.DELETE COLUMN：生成删除列的语句 **/
        lines.addAll(this.getColDropSql(schema, statusMap.get(StatusFlag.DELETE)));
        /** 5.ADD COLUMN：生成添加列的语句 **/
        lines.addAll(this.getColAddSql(schema, statusMap.get(StatusFlag.ADD)));
        /** 6.ALTER COLUMN：生成更新列的语句 **/
        lines.addAll(this.getColAlterSql(schema, statusMap.get(StatusFlag.UPDATE)));
        /** 7.约束添加：Constraints **/
        lines.addAll(this.getCsAddSql(schema));
        /** 8.生成Reference的Recovery **/
        lines.addAll(this.getRefsRecoverySql(schema));
        /** 9.Final **/
        return StringKit.join(lines, Symbol.SEMICOLON, true);
    }

    // ~ Methods =============================================
    /**
     * 读取JDBC数据库连接
     * 
     * @return
     */
    protected JdbcConnection connection() {
        return this.connection;
    }

    // ~ Private Methods =====================================

    private long rows(final String table) {
        final SqlDDLBuilder builder = SqlDDLBuilder.create();
        final String sql = builder.buildRowsSQL(table);
        return this.connection().count(sql);
    }

    private long nullRows(final String table, final String column) {
        final SqlDDLBuilder builder = SqlDDLBuilder.create();
        final String sql = builder.buildNullSQL(table, column);
        return this.connection().count(sql);
    }

    private long uniqueRows(final String table, final String column) {
        final SqlDDLBuilder builder = SqlDDLBuilder.create();
        final String sql = builder.buildUniqueSQL(table, column);
        return this.connection().count(sql);
    }

    private ConcurrentMap<String, PEField> extractForeignKey(final Schema schema) {
        final List<PEField> fields = schema.getForeignField();
        final ConcurrentMap<String, PEField> map = new ConcurrentHashMap<>();
        for (final PEField field : fields) {
            map.put(field.getColumnName(), field);
        }
        return map;
    }

    private ConcurrentMap<StatusFlag, Collection<String>> getColumnStatus(final Schema schema) {
        /** 1.读取原始系统中的列信息（数据库中和Schema的H2中） **/
        final Collection<String> oldCols = this.reflector().getColumns(schema.getTable());
        final Collection<String> newCols = schema.getColumns();

        final ConcurrentMap<StatusFlag, Collection<String>> statusMap = new ConcurrentHashMap<>();
        /** 2.1. ADD：新添加字段 **/
        statusMap.put(StatusFlag.ADD, diff(newCols, oldCols));
        /** 2.2. DELETE：删除字段 **/
        statusMap.put(StatusFlag.DELETE, diff(oldCols, newCols));
        /** 2.3. UPDATE：更新字段 **/
        statusMap.put(StatusFlag.UPDATE, intersect(oldCols, newCols));
        /** 3.字段状态最终结果 **/
        return statusMap;
    }

    private List<String> getColAlterSql(final Schema schema, final Collection<String> columns)
            throws AbstractDatabaseException {
        /** 1.遍历需要生成的列信息 **/
        final SqlDDLBuilder builder = SqlDDLBuilder.create();
        final String table = schema.getTable();
        final long rows = this.rows(table);
        /** 2.执行每个字段的更新 **/
        final List<String> sqlLines = new ArrayList<>();
        for (final String column : columns) {
            final PEField field = schema.getColumn(column);
            if (Constants.ZERO < rows) {
                // NOT NULL在有数据的情况下判断不可变更
                final Long nullRows = this.nullRows(table, column);
                if (Constants.ZERO < nullRows && !field.isNullable()) {
                    throw new NullableAlterException(getClass(), column, table);
                }
                // UNIQUE在有数据信息时本身出现了Duplicated
                final Long uniqueRows = this.uniqueRows(table, column);
                if (Constants.ZERO < uniqueRows && field.isUnique()) {
                    throw new UniqueAlterException(getClass(), column, table);
                }
            }
            /** 3.更新语句 **/
            final String sql = builder.buildAlterColumn(table, this.getFieldSaber().buildLine(field));
            sqlLines.add(sql);
        }
        return sqlLines;
    }

    private List<String> getColAddSql(final Schema schema, final Collection<String> columns)
            throws AbstractDatabaseException {
        /** 1.遍历需要生成的列信息 **/
        final SqlDDLBuilder builder = SqlDDLBuilder.create();
        final String table = schema.getTable();
        final long rows = this.rows(table);
        /** 2.执行每一个Add语句的添加 **/
        final List<String> sqlLines = new ArrayList<>();
        for (final String column : columns) {
            final PEField field = schema.getColumn(column);
            if (Constants.ZERO != rows) {
                // NOT NULL有数据不可行
                if (!field.isNullable()) {
                    throw new NullableAddException(getClass(), column, table);
                }
                // UNIQUE不可针对超过1行数据
                if (Constants.ONE < rows && field.isUnique()) {
                    throw new UniqueAddException(getClass(), column, table);
                }
            }
            /** 3.添加语句 **/
            final String sql = builder.buildAddColumn(table, this.getFieldSaber().buildLine(field));
            sqlLines.add(sql);
        }
        return sqlLines;
    }

    /**
     * 生成删除列的语句
     * 
     * @param schema
     * @param columns
     * @return
     */
    private List<String> getColDropSql(final Schema schema, final Collection<String> columns) {
        /** 1.遍历需要生成的列信息 **/
        final SqlDDLBuilder builder = SqlDDLBuilder.create();
        final String table = schema.getTable();

        final List<String> sqlLines = new ArrayList<>();
        for (final String column : columns) {
            sqlLines.add(builder.buildDropColumn(table, column));
        }
        return sqlLines;
    }

    /**
     * 
     * @param schema
     * @return
     */
    private List<String> getCsAddSql(final Schema schema) {
        /** 1.遍历需要生成的列信息 **/
        final SqlDDLBuilder builder = SqlDDLBuilder.create();
        final String table = schema.getTable();

        final List<String> sqlLines = new ArrayList<>();
        /** 2.主键约束 **/
        {
            for (final PEKey key : schema.keys()) {
                // buildLine(PEKey)仅针对PK和UK
                final String keyPart = this.getKeySaber().buildLine(key);
                if (StringKit.isNonNil(keyPart)) {
                    final String sql = builder.buildAddConstraint(table, keyPart);
                    sqlLines.add(sql);
                }
            }
        }
        /** 3.外键约束 **/
        {
            final ConcurrentMap<String, PEField> foreigns = this.extractForeignKey(schema);
            for (final PEKey key : schema.getForeignKey()) {
                final String keyPart = this.getKeySaber().buildLine(key, foreigns);
                if (StringKit.isNonNil(keyPart)) {
                    final String sql = builder.buildAddConstraint(table, keyPart);
                    sqlLines.add(sql);
                }
            }
        }
        return sqlLines;
    }

    /**
     * 从系统中读取所有约束，生成约束的删除语句
     * 
     * @param schema
     * @return
     */
    private List<String> getCsDropSql(final Schema schema) {
        /** 1.获取当前表中所有的约束信息 **/
        final String table = schema.getTable();
        final List<String> contraints = this.reflector().getConstraints(table);
        /** 2.得到SQL语句 **/
        final List<String> sqlLines = new ArrayList<>();
        final SqlDDLBuilder builder = SqlDDLBuilder.create();
        for (final String name : contraints) {
            sqlLines.add(builder.buildDropConstraint(table, name));
        }
        return sqlLines;
    }

    /**
     * 从系统中读取和当前Schema相关联的约束信息，在操作时需要解除约束才可执行
     * 
     * @param schema
     * @return
     */
    private List<String> getRefsDropSql(final Schema schema) {
        /** 1.获取反向约束 **/
        final String table = schema.getTable();
        {
            final Set<String> columns = schema.getColumns();
            for (final String column : columns) {
                /** 2.加载约束信息 **/
                refs.addAll(this.referencer().getReferences(table, column));
            }
        }
        /** 3.生成Reference的Drop语句 **/
        final List<String> sqlLines = new ArrayList<>();
        {
            if (!refs.isEmpty()) {
                sqlLines.addAll(this.referencer().prepDropSql(refs));
            }
        }
        return sqlLines;
    }

    /**
     * 恢复引用
     * 
     * @param schema
     * @return
     */
    private List<String> getRefsRecoverySql(final Schema schema) {
        final List<String> sqlLines = new ArrayList<>();
        if (!refs.isEmpty()) {
            sqlLines.addAll(this.referencer().prepRecoverySql(refs));
        }
        return sqlLines;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
