package com.prayer.base.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.Constants;
import com.prayer.dao.impl.builder.SqlDDLBuilder;
import com.prayer.facade.dao.builder.line.KeySaber;
import com.prayer.facade.kernel.Referencer;
import com.prayer.facade.pool.JdbcConnection;
import com.prayer.model.crucial.schema.FKReferencer;

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
public abstract class AbstractReferencer implements Referencer {
    // ~ Static Fields =======================================
    /** 约束名KEY **/
    protected static final String CONSTRAINT = "_CONSTRAINT";
    /** 表名KEY **/
    protected static final String TABLE = "_TABLE";
    /** 列名COLUMN **/
    protected static final String COLUMN = "_COLUMN";
    // ~ Instance Fields =====================================
    /** 数据库连接 **/
    @NotNull
    @InstanceOf(JdbcConnection.class)
    private transient final JdbcConnection connection; // NOPMD
    /** DDL的处理器 **/
    private transient final SqlDDLBuilder builder; // NOPMD
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================

    /**
     * 引用中使用的连接，直接从Builder中传入
     * 
     * @param connection
     */
    @PostValidateThis
    public AbstractReferencer(@NotNull final JdbcConnection connection) {
        this.connection = connection;
        this.builder = SqlDDLBuilder.create();
    }

    // ~ Abstract Methods ====================================
    /** **/
    public abstract String buildRefSQL(String table, String column);

    /** **/
    public abstract KeySaber getKeySaber();

    /** **/
    public abstract ConcurrentMap<String, String> getMetadata();

    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 根据获取到的Referencer列表获取删除其他表的FK约束的SQL语句
     */
    @Override
    public List<String> prepDropSql(@NotNull final List<FKReferencer> refs) {
        final List<String> dropSqls = new ArrayList<>();
        for (final FKReferencer ref : refs) {
            final String sql = this.builder.buildDropConstraint(ref.getFromTable(), ref.getName());
            dropSqls.add(sql);
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
            final String lineSql = this.builder.buildAddConstraint(ref.getFromTable(),
                    this.getKeySaber().buildLine(ref));
            recoverySqls.add(lineSql);
        }
        return recoverySqls;
    }

    /**
     * 获取引用集合
     */
    @Override
    public List<FKReferencer> getReferences(@NotNull @NotEmpty @NotBlank final String table,
            @NotNull @NotEmpty @NotBlank final String column) {
        // 1.构建引用语句，子类实现
        final String sql = this.buildRefSQL(table, column);
        // 2.从数据库中读取最终信息
        final ConcurrentMap<String, String> metadata = this.getMetadata();
        final List<ConcurrentMap<String, String>> records = this.connection.select(sql,
                metadata.values().toArray(Constants.T_STR_ARR));
        // 3.从数据库中解析最终结果
        final List<FKReferencer> refs = new ArrayList<>();
        for (final ConcurrentMap<String, String> item : records) {
            final FKReferencer ref = new FKReferencer();
            ref.setName(item.get(metadata.get(CONSTRAINT)));
            ref.setToTable(table);
            ref.setToColumn(column);
            ref.setFromTable(item.get(metadata.get(TABLE)));
            ref.setFromColumn(item.get(metadata.get(COLUMN)));
            refs.add(ref);
        }
        return refs;
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
