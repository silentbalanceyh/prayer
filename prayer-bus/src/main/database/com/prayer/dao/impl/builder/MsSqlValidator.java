package com.prayer.dao.impl.builder;

import static com.prayer.util.debug.Log.debug;
import static com.prayer.util.reflection.Instance.reservoir;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.constant.MemoryPool;
import com.prayer.constant.Resources;
import com.prayer.constant.SqlSegment;
import com.prayer.constant.Symbol;
import com.prayer.exception.schema.BKeyConstraintInvalidException;
import com.prayer.exception.schema.BTColumnNotExistingException;
import com.prayer.exception.schema.BTColumnTypeInvalidException;
import com.prayer.exception.schema.BTableNotExistingException;
import com.prayer.facade.dao.JdbcConnection;
import com.prayer.facade.schema.DataValidator;
import com.prayer.pool.impl.jdbc.RecordConnImpl;
import com.prayer.util.string.StringKit;

import net.sf.oval.constraint.InstanceOf;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class MsSqlValidator implements DataValidator {

    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(MsSqlValidator.class);
    // ~ Instance Fields =====================================
    /** 数据库连接 **/
    @NotNull
    @InstanceOf(JdbcConnection.class)
    private transient final JdbcConnection context; // NOPMD
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================

    /**
     * 
     */
    @PostValidateThis
    public MsSqlValidator() {
        this.context = reservoir(MemoryPool.POOL_JDBC, Resources.DB_CATEGORY, RecordConnImpl.class);
    }

    /**
     * 检查表是否存在的情况
     */
    @Override
    public AbstractSchemaException verifyTable(@NotNull @NotEmpty @NotBlank final String table) {
        final String sql = MsSqlHelper.getSqlTableExist(table);
        final Long counter = this.context.count(sql);
        AbstractSchemaException error = null;
        if (counter <= 0) {
            error = new BTableNotExistingException(getClass(), table);
        }
        return error;
    }

    /**
     * 检查字段是否存在的情况
     */
    @Override
    public AbstractSchemaException verifyColumn(@NotNull @NotEmpty @NotBlank final String table,
            @NotNull @NotEmpty @NotBlank final String column) {
        final String sql = MsSqlHelper.getSqlColumnExist(table, column);
        final Long counter = this.context.count(sql);
        AbstractSchemaException error = null;
        if (counter <= 0) {
            error = new BTColumnNotExistingException(getClass(), table, column);
        }
        return error;
    }

    /**
     * 验证Target字段是否Unique或者Primary Key <code>Sub Table必须满足</code>
     * <code>Foreign Key关联的Table也必须满足</code>
     */
    @Override
    public AbstractSchemaException verifyConstraint(@NotNull @NotEmpty @NotBlank final String table,
            @NotNull @NotEmpty @NotBlank final String column) {
        final String sql = MsSqlHelper.getSqlUKPKConstraint(table, column);
        final Long counter = this.context.count(sql);
        AbstractSchemaException error = null;
        if (counter <= 0) {
            error = new BKeyConstraintInvalidException(getClass(), table, column);
        }
        return error;
    }

    /**
     * 验证Target字段类型是否和当前类型一致
     */
    @Override
    public AbstractSchemaException verifyColumnType(@NotNull @NotEmpty @NotBlank final String table,
            @NotNull @NotEmpty @NotBlank final String column, @NotNull @NotEmpty @NotBlank final String expectedType) {
        // 处理参数
        String type = "";
        if (0 < expectedType.indexOf('(')) {
            type = expectedType.split("(")[0];
            if (StringKit.isNonNil(type)) {
                type = type.toLowerCase(Locale.getDefault());
            }
        } else {
            type = expectedType;
        }
        final String sql = MsSqlHelper.getSqlColumnType(table, column, type);
        final Long counter = this.context.count(sql);
        AbstractSchemaException error = null;
        if (counter <= 0) {
            error = new BTColumnTypeInvalidException(getClass(), table, column, type);
        }
        return error;
    }

    /**
     * 
     */
    @Override
    public void purgeTestData() {
        final String sql = MsSqlHelper.getSqlTestingTables();
        List<String> tables = new ArrayList<>();
        do {
            // 1.从系统中读取表信息
            tables = this.context.select(sql, "TABLE_NAME");
            // 2.删除读取的表信息，递归操作，第一次删不掉的话直接第二次删除
            for (final String table : tables) {
                // 表存在的时候就删除
                if (null == this.verifyTable(table)) {
                    final String purgeSql = MessageFormat.format(SqlSegment.TB_DROP, table) + Symbol.SEMICOLON;
                    this.context.executeBatch(purgeSql);
                    debug(LOGGER, "[T] Table = " + table + " has been purged successfully !");
                }
            }
        } while (!tables.isEmpty());
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
