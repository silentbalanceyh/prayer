package com.prayer.fantasm.dao.database;

import static com.prayer.util.reflection.Instance.singleton;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.Resources;
import com.prayer.database.pool.impl.jdbc.JdbcConnImpl;
import com.prayer.facade.dao.DatabaseDao;
import com.prayer.facade.pool.JdbcConnection;
import com.prayer.model.business.Metadata;
import com.prayer.util.digraph.Edges;
import com.prayer.util.jdbc.DatabaseKit;

import net.sf.oval.constraint.InstanceOf;
import net.sf.oval.constraint.NotNull;

/**
 * Metadata元数据信息
 * 
 * @author Lang
 *
 */
public abstract class AbstractDatabaseDao implements DatabaseDao {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 数据库连接 **/
    @NotNull
    @InstanceOf(JdbcConnection.class)
    private transient final JdbcConnection connection; // NOPMD
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================

    public AbstractDatabaseDao() {
        this.connection = singleton(JdbcConnImpl.class);
    }

    // ~ Abstract Methods ====================================
    /** 读取Relation的SQL语句 **/
    public abstract String buildRelSql();

    /** From的列名 **/
    public abstract String fromColumn();

    /** To的列名 **/
    public abstract String toColumn();

    // ~ Override Methods ====================================
    /**
     * 通过Discovery分析出来的数据库的文件名，其他所有特殊数据库文件名由此而来
     */
    @Override
    public String getFile() {
        final Metadata metadata = this.connection.getMetadata(Resources.DB_CATEGORY);
        String retFile = DatabaseKit.getDatabaseVersion(metadata.getProductName(), metadata.getProductVersion());
        return Resources.DB_CATEGORY + retFile;
    }

    /**
     * 从数据库中读取列名
     */
    @Override
    public Edges getRelations() {
        /** 1.读取SQL语句 **/
        final String sql = this.buildRelSql();
        /** 2.从数据库中读取最终信息 **/
        final List<ConcurrentMap<String, String>> records = this.connection.select(sql,
                new String[] { this.fromColumn(), this.toColumn() });
        /** 3.构造关系信息 **/
        final Edges edges = new Edges();
        for (final ConcurrentMap<String, String> item : records) {
            final String from = item.get(this.fromColumn());
            final String to = item.get(this.toColumn());
            if (!from.equals(to)) {
                edges.addEdge(from, to);
            }
        }
        return edges;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
