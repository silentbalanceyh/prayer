package com.prayer.dao.impl.builder; // NOPMD

import static com.prayer.util.Log.debug;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.dao.AbstractBuilder;
import com.prayer.exception.database.NullableAddException;
import com.prayer.exception.database.NullableAlterException;
import com.prayer.model.h2.schema.FieldModel;
import com.prayer.model.h2.schema.KeyModel;
import com.prayer.model.h2.schema.MetaModel;
import com.prayer.model.kernel.GenericSchema;
import com.prayer.util.StringKit;
import com.prayer.util.cv.Constants;
import com.prayer.util.cv.SqlSegment;
import com.prayer.util.cv.SystemEnum.KeyCategory;
import com.prayer.util.cv.SystemEnum.MetaPolicy;
import com.prayer.util.cv.SystemEnum.StatusFlag;
import com.prayer.util.cv.log.DebugKey;
import com.prayer.util.dao.SqlDdlStatement;

import net.sf.oval.constraint.InstanceOfAny;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;
import net.sf.oval.guard.PreValidateThis;

/**
 * SQL Server的元数据生成器
 * 
 * @author Lang
 *
 */
@Guarded
public class MsSqlBuilder extends AbstractBuilder implements SqlSegment { // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(MsSqlBuilder.class);
    /** 针对整个系统的表统计管理 **/
    private static final ConcurrentMap<String, Boolean> TB_COUNT_MAP = new ConcurrentHashMap<>();

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public MsSqlBuilder(@NotNull @InstanceOfAny(GenericSchema.class) final GenericSchema schema) {
        super(schema);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    protected String[] lengthTypes() {
        return Arrays.copyOf(MsSqlHelper.LENGTH_TYPES, MsSqlHelper.LENGTH_TYPES.length);
    }

    /**
     * 
     */
    @Override
    protected String[] precisionTypes() {
        return Arrays.copyOf(MsSqlHelper.PRECISION_TYPES, MsSqlHelper.PRECISION_TYPES.length);
    }

    /**
     * 
     * @param column
     * @return
     */
    @Override
    protected Long nullRows(@NotNull @NotBlank @NotEmpty final String column) {
        return this.getContext().count(MsSqlHelper.getSqlNull(this.getTable(), column));
    }

    /**
     * 创建新的数据表
     */
    @Override
    @PreValidateThis
    public boolean createTable() {
        final boolean exist = this.existTable();
        if (exist) {
            return false;
        } else {
            final String sql = genCreateSql();
            debug(LOGGER, DebugKey.INFO_SQL_STMT, sql);
            final int respCode = this.getContext().execute(sql, null);
            // EXIST：新表创建成功过后添加缓存
            final boolean ret = Constants.RC_SUCCESS == respCode;
            if (ret) {
                TB_COUNT_MAP.put(this.getTable(), Boolean.TRUE);
            }
            return ret;
        }
    }

    /**
     * 检查数据表是否存在
     */
    @Override
    @PreValidateThis
    public boolean existTable() {
        // 缓存统计结果
        Boolean exist = TB_COUNT_MAP.get(this.getTable());
        // 理论上这个Hash表中只可能有TRUE的值，没有FALSE
        if (null == exist || !exist) {
            final String sql = MsSqlHelper.getSqlTableExist(this.getTable());
            final Long counter = this.getContext().count(sql);
            exist = 0 < counter;
            // EXIST：直接判断添加缓存
            if (exist) {
                TB_COUNT_MAP.put(this.getTable(), Boolean.TRUE);
            }
        }
        return exist;
    }

    /**
     * 传入参数schema是最新从H2中拿到的元数据信息
     */
    @Override
    @PreValidateThis
    public boolean syncTable(@NotNull final GenericSchema schema) {
        final boolean exist = this.existTable();
        if (exist) {
            final String sql = this.genUpdateSql(schema);
            debug(LOGGER, DebugKey.INFO_SQL_STMT, sql);
            final int respCode = this.getContext().execute(sql, null);
            return Constants.RC_SUCCESS == respCode;
        } else {
            return false;
        }
    }

    /** **/
    @Override
    @PreValidateThis
    public boolean purgeTable() {
        final boolean exist = this.existTable();
        if (exist) {
            final String sql = MessageFormat.format(TB_DROP, this.getTable());
            debug(LOGGER, DebugKey.INFO_SQL_STMT, sql);
            final int respCode = this.getContext().execute(sql, null);
            // EXIST：删除成功过后移除缓存
            final boolean ret = Constants.RC_SUCCESS == respCode;
            if (ret && TB_COUNT_MAP.containsKey(this.getTable())) {
                TB_COUNT_MAP.remove(this.getTable());
            }
            return Constants.RC_SUCCESS == respCode;
        } else {
            return false;
        }
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private String genUpdateSql(final GenericSchema schema) {
        // 1.清除SQL行
        this.getSqlLines().clear();
        // 没有表存在的时候：Fix Issue: Invalid object name 'XXXXX'.
        Long rows = 0L;
        if (this.existTable()) {
            rows = this.getRows();
        }
        // 2.清除约束
        {
            final Collection<String> csSet = this.getCSNames();
            for (final String name : csSet) {
                addSqlLine(this.genDropConstraints(name));
            }
        }
        final ConcurrentMap<StatusFlag, Collection<String>> statusMap = this.getStatusMap(schema);
        // 3.删除列操作
        {
            final Collection<String> columns = statusMap.get(StatusFlag.DELETE);
            for (final String column : columns) {
                addSqlLine(this.genDropColumns(column));
            }
        }
        // 4.添加列操作
        {
            this.genAddColumnLines(statusMap, schema, rows);
        }
        // 5.修改列操作
        {
            this.genAlterColumnLines(statusMap, schema, rows);
        }
        // 6.添加约束
        {
            final Collection<KeyModel> keys = schema.getKeys().values();
            for (final KeyModel key : keys) {
                if (KeyCategory.ForeignKey == key.getCategory()) {
                    final String column = key.getColumns().get(Constants.ZERO);
                    final FieldModel field = schema.getColumn(column);
                    addSqlLine(this.genAddCsLine(key, field));
                } else {
                    addSqlLine(this.genAddCsLine(key, null));
                }
            }
        }
        return StringKit.join(this.getSqlLines(), SEMICOLON, true);
    }

    private void genAlterColumnLines(final ConcurrentMap<StatusFlag, Collection<String>> statusMap,
            final GenericSchema schema, final long rows) {
        final Collection<String> columns = statusMap.get(StatusFlag.UPDATE);
        for (final String column : columns) {
            final FieldModel field = schema.getColumn(column);
            if (Constants.ZERO == rows) {
                addSqlLine(this.genAlterColumns(field));
            } else if (Constants.ZERO < rows) {
                final Long nullRows = this.nullRows(column);
                if (Constants.ZERO == nullRows) {
                    addSqlLine(this.genAlterColumns(field));
                } else {
                    if (field.isNullable()) {
                        addSqlLine(this.genAlterColumns(field));
                    } else {
                        this.setError(new NullableAlterException(getClass(), field.getColumnName(), // NOPMD
                                this.getTable()));
                        break;
                    }
                }
            }
        }
    }

    private void genAddColumnLines(final ConcurrentMap<StatusFlag, Collection<String>> statusMap,
            final GenericSchema schema, final long rows) {
        final Collection<String> columns = statusMap.get(StatusFlag.ADD);
        for (final String column : columns) {
            final FieldModel field = schema.getColumn(column);
            if (Constants.ZERO == rows) {
                addSqlLine(this.genAddColumns(field));
            } else {
                if (field.isNullable()) {
                    addSqlLine(this.genAddColumns(field));
                } else {
                    this.setError(new NullableAddException(getClass(), field.getColumnName(), this.getTable())); // NOPMD
                    break;
                }
            }
        }
    }

    private ConcurrentMap<StatusFlag, Collection<String>> getStatusMap(final GenericSchema schema) {
        // 获取新列集合
        final Collection<String> newColumns = schema.getColumns();
        // 获取旧列集合
        final String sql = MsSqlHelper.getSqlColumns(this.getTable());
        final Collection<String> oldColumns = this.getContext().select(sql, MsSqlHelper.COL_TB_COLUMN);
        return this.getColumnStatus(oldColumns, newColumns);
    }

    private Collection<String> getCSNames() {
        final String sql = MsSqlHelper.getSqlConstraints(this.getTable());
        return this.getContext().select(sql, MsSqlHelper.COL_TB_CONSTRAINT);
    }

    private String genCreateSql() {
        // 1.主键定义行
        this.getSqlLines().clear();
        {
            this.genPrimaryKeyLines();
        }
        // 2.字段定义行
        {
            final Collection<FieldModel> fields = this.getSchema().getFields().values();
            for (final FieldModel field : fields) {
                if (!field.isPrimaryKey()) {
                    addSqlLine(this.genColumnLine(field));
                    // this.getSqlLines().add(this.genColumnLine(field));
                }
            }
        }
        // 3.添加Unique/Primary Key约束
        {
            final Collection<KeyModel> keys = this.getSchema().getKeys().values();
            for (final KeyModel key : keys) {
                // INCREMENT已经在前边生成过主键行了，不需要重新生成
                addSqlLine(this.genKeyLine(key));
                // this.getSqlLines().add(this.genKeyLine(key));
            }
        }
        // 4.添加Foreign Key约束
        {
            addSqlLine(this.genForeignKey());
        }
        // 5.生成最终SQL语句
        return MessageFormat.format(TB_CREATE, this.getTable(), StringKit.join(this.getSqlLines(), COMMA));
    }

    private void genPrimaryKeyLines() {
        final MetaPolicy policy = this.getSchema().getMeta().getPolicy();
        if (MetaPolicy.INCREMENT == policy) {
            addSqlLine(this.genIdentityLine(this.getSchema().getMeta()));
            // this.getSqlLines().add(this.genIdentityLine(getSchema().getMeta()));
        } else if (MetaPolicy.COLLECTION == policy) {
            final List<FieldModel> pkFields = this.getSchema().getPrimaryKeys();
            for (final FieldModel field : pkFields) {
                addSqlLine(this.genColumnLine(field));
                // this.getSqlLines().add(this.genColumnLine(field));
            }
        } else {
            final FieldModel field = this.getSchema().getPrimaryKeys().get(Constants.ZERO);
            addSqlLine(this.genColumnLine(field));
            // this.getSqlLines().add(this.genColumnLine(field));
        }
    }

    private String genIdentityLine(final MetaModel meta) {
        final StringBuilder pkSql = new StringBuilder();
        final FieldModel field = this.getSchema().getPrimaryKeys().get(Constants.ZERO);
        // 1.1.主键字段和数据类型
        final String columnType = SqlDdlStatement.DB_TYPES.get(field.getColumnType());

        // 2.字段名、数据类型，SQL Server独有：NAME INT PRIMARY KEY IDENTITY
        pkSql.append(field.getColumnName()).append(SPACE).append(columnType).append(SPACE).append(MsSqlHelper.IDENTITY)
                .append(BRACKET_SL).append(meta.getSeqInit()).append(COMMA).append(meta.getSeqStep())
                .append(BRACKET_SR);
        return pkSql.toString();
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
