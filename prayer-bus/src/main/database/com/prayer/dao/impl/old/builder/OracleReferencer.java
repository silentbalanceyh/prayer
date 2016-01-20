package com.prayer.dao.impl.old.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.prayer.facade.kernel.Referencer;
import com.prayer.facade.pool.JdbcConnection;
import com.prayer.model.crucial.schema.FKReferencer;
import com.prayer.util.jdbc.SqlDDL;

import net.sf.oval.constraint.InstanceOf;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * SQL Server中处理Referencer的方法
 * 
 * @author Lang
 *
 */
@Guarded
public final class OracleReferencer implements Referencer {
    // ~ Static Fields =======================================
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
    public OracleReferencer(@NotNull final JdbcConnection context) {
        this.context = context;
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================

    /**
     * 获取所有的引用集合
     */
    @Override
    public List<FKReferencer> getReferences(@NotNull @NotEmpty @NotBlank final String table,
            @NotNull @NotEmpty @NotBlank final String column) {
        final String sql = MsSqlHelper.getSqlReferences(table, column);
        final List<ConcurrentMap<String, String>> records = this.context.select(sql,
                new String[] { "CONSTRAINT_NAME", "TABLE_NAME", "COLUMN_NAME" });
        final List<FKReferencer> retRefs = new ArrayList<>();
        for (final ConcurrentMap<String, String> item : records) {
            final FKReferencer ref = this.extractReference(item, table, column);
            retRefs.add(ref);
        }
        return retRefs;
    }

    /**
     * 根据获取的Referencer列表获取删除其他表的FK约束的SQL语句
     */
    @Override
    public List<String> prepDropSql(@NotNull final List<FKReferencer> refs) {
        final List<String> dropSqls = new ArrayList<>();
        for (final FKReferencer ref : refs) {
            final String dropSql = SqlDDL.dropCSSql(ref.getFromTable(), ref.getName());
            dropSqls.add(dropSql);
        }
        return dropSqls;
    }

    /**
     * 根据获取的Referencer列表获取添加（修复）其他表的FK约束的SQL语句
     */
    @Override
    public List<String> prepRecoverySql(@NotNull final List<FKReferencer> refs) {
        final List<String> recoverySqls = new ArrayList<>();
        for (final FKReferencer ref : refs) {
            final List<String> columns = new ArrayList<>();
            columns.add(ref.getFromColumn());
            final String fkLine = SqlDDL.newFKSql(ref.getName(), columns, ref.getToTable(), ref.getToColumn());
            final String lineSql = SqlDDL.addColSql(ref.getFromTable(), fkLine);
            recoverySqls.add(lineSql);
        }
        return recoverySqls;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private FKReferencer extractReference(final ConcurrentMap<String, String> record, final String table,
            final String column) {
        final FKReferencer refs = new FKReferencer();
        refs.setName(record.get("CONSTRAINT_NAME"));
        refs.setToTable(table);
        refs.setToColumn(column);
        refs.setFromTable(record.get("TABLE_NAME"));
        refs.setFromColumn(record.get("COLUMN_NAME"));
        return refs;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
